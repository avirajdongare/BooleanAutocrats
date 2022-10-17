import cv2
import click
import pytesseract

import numpy as np
from decode_predictions import decode_predictions

from imutils.object_detection import non_max_suppression


IMAGE_HEIGHT = 128
IMAGE_WIDTH = 128
IMAGE_PADDING = 0.10


@click.command()
@click.option(
    "--image-path", help="image path with text to extract from.", required=True
)
@click.option("--min-confidence", default=0.75, help="minimum confidence out of 1.0")
def process(image_path, min_confidence):
    resized_image = extract_image(image_path)

    # Extract boxes containing text
    (scores, geometry) = east_model(resized_image)
    (rects, confidences) = decode_predictions(scores, geometry, IMAGE_PADDING)
    boxes = non_max_suppression(np.array(rects), probs=confidences)

    # Get image output with text
    get_image_roi(image_path, boxes)


def extract_image(image_path):
    """
    Extract image from file
    """
    image_obj = cv2.imread(image_path)

    # Resize for optimization purposes
    image_resized = cv2.resize(image_obj, (IMAGE_WIDTH, IMAGE_HEIGHT))

    return image_resized


def east_model(image_obj):
    """
    To find out which region of the image containing text
    """
    layers = ["feature_fusion/Conv_7/Sigmoid", "feature_fusion/concat_3"]

    net = cv2.dnn.readNet("frozen_east_text_detection.pb")
    blob = cv2.dnn.blobFromImage(
        image_obj,
        1.0,
        (IMAGE_WIDTH, IMAGE_HEIGHT),
        (123.68, 116.78, 103.94),
        swapRB=True,
        crop=False,
    )
    net.setInput(blob)
    return net.forward(layers)


def get_image_roi(image_path, boxes):
    """
    Get image region of interest
    """
    image_obj = cv2.imread(image_path)
    (ori_height, ori_width) = image_obj.shape[:2]

    width_ratio = ori_width / IMAGE_WIDTH
    height_ratio = ori_height / IMAGE_HEIGHT

    results = []
    for (start_x, start_y, end_x, end_y) in boxes:
        start_x = int(start_x * width_ratio)
        start_y = int(start_y * height_ratio)
        end_x   = int(end_x * width_ratio)
        end_y   = int(end_y * height_ratio)
        
        d_x     = int((end_x - start_x) * IMAGE_PADDING)
        d_y     = int((end_y - start_y) * IMAGE_PADDING)

        start_x = max(0, start_x - d_x)
        start_y = max(0, start_y - d_y)
        end_x   = min(ori_width, end_x + (d_x * 2))
        end_y   = min(ori_height, end_y + (d_y * 2))

        roi = image_obj[start_y:end_y, start_x:end_x]
        
        config = ("-l eng --oem 1 --psm 7")
        text = pytesseract.image_to_string(roi, config=config)

        results.append(((start_x, start_y, end_x, end_y), text))
    

    results = sorted(results, key=lambda r:r[0][1])
    for ((start_x, start_y, end_x, end_y), text) in results:
        print(f"Extracted text: {text}")
        
        text = "".join([c if ord(c) < IMAGE_HEIGHT else "" for c in text]).strip()
        output = image_obj.copy()
        cv2.rectangle(output, (start_x, start_y), (end_x, end_y),
            (0, 0, 255), 2)
        cv2.putText(output, text, (start_x, start_y - 20),
            cv2.FONT_HERSHEY_SIMPLEX, 1.2, (0, 0, 255), 3)

        cv2.imshow("Text Detection", output)
        cv2.waitKey(0)


if __name__ == "__main__":
    process()
