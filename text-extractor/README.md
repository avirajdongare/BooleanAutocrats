# Text Extractor using OpenCV and Tesseract

## Table of Contents
- [Introduction](#introduction)

## Introduction
This repository contains automation script to extract text from images.

## Getting Started
1. Create conda/pyenv environment using Python 3.70 (recommended and tested).
2. Install tesseract library using the following command:
    ```sh
    sudo apt install tesseract-ocr
    ```
3. Run `pip install -r requirements.txt` to install required dependencies.
4. Download model using:
    ```sh
    wget -q https://raw.githubusercontent.com/oyyd/frozen_east_text_detection.pb/master/frozen_east_text_detection.pb
    ```
5. Run `python main.py --help` to identify the correct command line arguments.
6. Enjoy!
