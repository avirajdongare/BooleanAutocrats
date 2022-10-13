import pandas as pd
import time
import configparser
print("\t 1)Enter 1 for Read and write the text file")
print("\t 2)Enter 2 for Read and write the Excel file")
print("\t 3)Enter 3 for Read and write the XML file")
a = input("Enter a Number: ")
if a == '1':
    start = time.time()
    config = configparser.ConfigParser()
    config.read('path3.properties')

    read_file = config.get("ReadSection", "100Mbtxt")
    file = open(read_file, "r")
    data = file.read()

    write_file = config.get("WriteSection", "1sttxt")
    with open(write_file, "a") as file:
        file.write(data)
    end = time.time()
    print(end - start)
    print("--- %s seconds ---" % (end - start))
    print('Done')

elif a == '2':
    start = time.time()
    config = configparser.ConfigParser()
    config.read('path3.properties')

    read_file = config.get("ReadSection", "samplecsv")
    df = pd.read_csv(read_file)

    write_file = config.get("WriteSection", "csv")
    newdf = df.to_csv(write_file)
    end = time.time()
    print("--- %s seconds ---" % (end - start))
    print("Done")
elif a == 3:
    start = time.time()
    config = configparser.ConfigParser()
    config.read('path3.properties')

    read_file = config.get("ReadSection", "read")
    file = open(read_file, "r")
    data = file.read()
    file.close()

    write_file = config.get("WriteSection", "write")
    with open(write_file, "a") as file:
        file.write(data)
    end = time.time()
    print("---%s seconds---" % (end-start))
    print('Done')

else:
    print("You choose the wrong option!")
    print("Please try again!!!")
