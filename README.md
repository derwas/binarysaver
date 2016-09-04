I. Description

This project allows to download a file given as an argument.
The argument is a URL, the file is downloaded then a logging function for keeping logs of the download operation.

II. File list
-----------------
BinarySaver.java        File that contains the only class of the program.
pref.log				File that contains an example of logs
README.md				File that contains the instructions for this program (this file)

III. Design
--------------
A. Methods

1. main method: 
This method checks if the program is launched with parameters.
If there is any argument, it is considered as a valid URL and tries to save the corresponding file and updates the log file.
If there are no arguments, the program asks the user to enter a valid url of a file to download.
Upon completion of the download, the program displays a completion message.

2. saveBinaryFile method:
This method has been modified from the orignal version to be compliant with the instructions of the project:
 - Checks the size of the file, if its more than 10 MB, it asks the user if he wishes to proceed.
 - Checks the type of the file, if it is an html file then the user is decided if he whishes to download it of simply display it on the screen.
This method log every detail of the download activity and it returns the log as a string. 

3. saveLog method:
This method takes as input a String that contains a log.
It saves this string at the end of the perf.log file.
Every new log entry is separated by two empty lines.

B. Log file Structure:

The log file keeps records of all download activities, this means it does not erase the download log after a new execution.
Every new download activity is logged at the end of the file.

The structure of logging one activity is as follows:

New download activity started at : Sun Jan 17 13:36:17 GMT 2016
URL = http://www.gmit.ie/sites/default/files/public/styles/landing_page_image/public/default_images/banner-galwaycampus_8.jpg
Filename = banner-galwaycampus_8.jpg
Content Type = image/jpeg
File Size = 35051 bytes
Download = yes 
Display on Screen = no 
Download Time =  31 ms

In case of an invalid URL, the logging entry is as follows:

New download activity started at : Sun Jan 17 13:40:26 GMT 2016
Download not complete: sdgf is not a valid URL.

