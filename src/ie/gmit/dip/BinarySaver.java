package ie.gmit.dip;

import java.net.*;
import java.util.Date;
import java.util.Scanner;
import java.io.*;

public class BinarySaver {

  public static void main (String args[]) {
	  String log ="";
	  if(args.length>0){// if the program is called with parameters, each parameter is considered as a URL.
		  for (int i = 0; i < args.length; i++) {
	
	      try {
	    	  log = "";
	        URL root = new URL(args[i]);
	         log = saveBinaryFile(root);
	         saveLog(log);
	      }
	      catch (MalformedURLException e) {
	        System.err.println(args[i] + " is not URL I understand.");
	        log = log + "Download not complete: "+args[i] + " is not a valid URL.\n";
	         saveLog(log);
	      }
		catch (IOException e) {
	        System.err.println(e);
	        log = log + "Download not complete due to IOException.\n";
	         saveLog(log);
	      }
	    } // end for
	  }//end if
	  else{// if the user did not enter any parameter -- in this case the program download only 1 file that the user gives in the following line
		  Scanner s = new Scanner(System.in);
		  String url = "";
		  System.out.println("Provide a valid URL for download: ");
		  url = s.nextLine();
		  try {
		        URL root = new URL(url);
		         log = saveBinaryFile(root);
		         saveLog(log);
		      }
		      catch (MalformedURLException e) {
		        System.err.println(url + " is not URL I understand.");
		        log = log + "Download not complete: "+url + " is not a valid URL.\n";
		         saveLog(log);
		      } catch (IOException e) {
				// TODO Auto-generated catch block
			        System.err.println("Unable to save the file : "+url);
			        log = log + "Download not complete due to IOException.\n";
			         saveLog(log);
		      }
	  }// end else
	  // display to the user a msg to say that the download is now complete!
	  System.out.println("End of the program, please check your local folder for files and check perf.log for logs.");
  } // end main


  private static void saveLog(String log) {

	  Date dt = new Date(System.currentTimeMillis());
	  log = "\n\nNew download activity started at : "+ dt +"\n"+ log;
	  Writer output;
	  try {
		  output = new BufferedWriter(new FileWriter("perf.log",true)); //open the log file in append mode
		  output.append(log);
		  output.close();
		} catch (IOException e) {
			System.out.println("Unable to log the download activity!");
		}  

}


public static String saveBinaryFile(URL u) throws IOException {
	  String log = "";
	  boolean download = true;
	  boolean display = false;

    URLConnection uc = u.openConnection();
    String contentType = uc.getContentType();
    int contentLength = uc.getContentLength();
    String filename = u.getFile();
    filename = filename.substring(filename.lastIndexOf('/') + 1);
    
    
    log = log+ "URL = " + u.toString() + "\n"; // logging the url
    log = log+ "Filename = " + filename + "\n"; // logging the filename
    log = log+ "Content Type = " + uc.getContentType() + "\n"; // logging the content type
    log = log+ "File Size = " + uc.getContentLength() + " bytes\n"; // logging the file size

    
    if (contentLength > (10 * 1024 * 1024)){ // check the size of the file; if longer than 10 Mo, then ask the user if he wiches to proceed
    	Scanner s = new Scanner(System.in);
    	String answer ="";
    	System.out.println(filename+" has a size longer than  10 MB, do yoy want to proceed?");
    	
    	while (!(answer.toUpperCase().equals("YES") || answer.toUpperCase().equals("NO"))){
	    	System.out.print("Please type yes or no: ");
	    	answer = s.next();
	    	if (answer.equals("no") ){// if the user decides not to proceed then download becomes false.
	    		download = false;
	    	    log = log+ "Download = no \n"; // logging the decision of the user regarding the download

	    	}
	    	
    	}
    	
    }
    
    if (download){
    if (contentType.contains("html")) {// check the type of the file, if it is an html, we ask the user if he wants to display it on the screen rather than download it
    	Scanner s = new Scanner(System.in);
    	String answer ="";
    	
    	System.out.println(filename +" is an html file! Do you wish to download display on screen this file?");
    	
    	while (!(answer.toUpperCase().equals("D") || answer.toUpperCase().equals("S"))){
	    	System.out.print("Please type D for download and S for disaply on screen: ");
	    	answer = s.next();
	    	
	    	if (answer.equals("S") ){
	    		download = false; // if the user decides to display then  download becomes false.
	    		display = true; // and display becomes true
	    	    log = log+ "Download = no \n"; // logging the decision of the user regarding the download
	    	    log = log+ "Display on Screen = yes \n"; // logging the decision of the user regarding the display on screen
	    	}
    	}
    }
    }
    if (download || display){
    long start = System.currentTimeMillis(); // current system time to log the download time

	InputStream raw = uc.getInputStream();
    InputStream in  = new BufferedInputStream(raw);
    byte[] data = new byte[1000000];
    int bytesRead = 0;
    int offset = 0;
    while (offset < contentLength) {
       bytesRead = in.read(data, offset, data.length-offset);
       if (bytesRead == -1) break;
       offset += bytesRead;
    }
    in.close();

    if (offset != contentLength) {
      throw new IOException("Only read " + offset
       + " bytes; Expected " + contentLength + " bytes");
    }

    if (display){  
    	System.out.write(data); 
    }
    if (download){
    FileOutputStream fout = new FileOutputStream(filename);
    fout.write(data);
    fout.flush();
    fout.close();
    log = log+ "Download = yes \n"; // logging the decision of the user regarding the download
    log = log+ "Display on Screen = no \n"; // logging the decision of the user regarding the display on screen

    }
    long end = System.currentTimeMillis();
    long duration = end - start;
    log = log+ "Download Time =  " + duration +" ms\n"; // logging the decision of the user regarding the download
  }
    return log;
  }

} // end BinarySaver
