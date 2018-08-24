/*
 * Author: James Jacobs
 * Version: 1
 * Date: 24/08/2018
 * Name: ImageDuplicate Remover
 * Description: This program will iterate through images in a directory and display the filepath of the duplicates
 * Requirements: text file called 'hashes.txt'
 * 
 */


import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.imageio.ImageIO;

public class IDR {
	
	////////////////////////////////////////////////////////////////////////////////////////
	// some of the following code is from: https://stackoverflow.com/questions/4917326/how-to-iterate-over-the-files-of-a-certain-directory-in-java
	
	public static void main(String[] args) throws IOException  {

		//This code will iterate through all the files in the given directory, all files must be images.
		String filePath = "C:\\Users\\jacob_000\\Pictures\\Camera Roll";
		File dir = new File(filePath);
		  File[] directoryListing = dir.listFiles();
		  if (directoryListing != null) {
		    for (File child : directoryListing) {
		      try {
				blah(child);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		    }
		  } else {
		    
			  System.out.println("Doesn't work");
		  }
		  
		  System.out.println("Hashing complete Homie");
		  
		  compare();
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////
	//https://sites.google.com/site/matthewjoneswebsite/java/md5-hash-of-an-image
	//Returns the hash as a hex string
	
	static String returnHex(byte[] inBytes) throws Exception {
        String hexString = null;
        for (int i=0; i < inBytes.length; i++) {
            hexString +=
            Integer.toString( ( inBytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
	    return hexString;
	  }

	public static void blah(File address) throws NoSuchAlgorithmException, Exception
	{
		//takes each files and hashes it then passes it to the hasher method
		File input = address;
        
        BufferedImage buffImg = ImageIO.read(input);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(buffImg, "png", outputStream);
        byte[] data = outputStream.toByteArray();

        //System.out.println(address);
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);
        byte[] hash = md.digest();
        //System.out.println(returnHex(hash));
        hasher(address, returnHex(hash));
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	
	public static void hasher(File file, String address) throws IOException
	{
		//this method writes the hashes to a given text file
		String filePath = "C:\\Users\\jacob_000\\Desktop\\hashes.txt";
	    String str = file.toString();
	    BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true));
	    writer.append(str);
	    writer.newLine();
	    writer.append(address);
	    writer.newLine();
	         
	    writer.close();
	}
	
	public static void compare() throws IOException
	{
		//this method compares all the hashes and displays the ones which are duplicates
		String filePath = "C:\\Users\\jacob_000\\Desktop\\hashes.txt";
		List<String> list = Files.readAllLines(new File(filePath).toPath(), Charset.defaultCharset() );
		
		//System.out.println("\n" + list);
		
		System.out.println("\n\ncomparison...\n\n");
		
		
		for (int i = 0; i < list.size(); i++) {
	        String itemI = list.get(i);
	        for (int j = 0; j < list.size(); j++) {
	            String itemJ = list.get(j);
	            if (i != j && i % 2 != 0 && j % 2 != 0) {
	                if (itemI.compareTo(itemJ) == 0) {
	                    System.out.println("Element " + i + " and " + j + " are pairs.");
	                	System.out.println(list.get(i-1));
	                	System.out.println(list.get(i));
	                	System.out.println(list.get(j-1));
	                	System.out.println(list.get(j));
	                    System.out.println("\n");
	                }
	            }
	        }
	    }
				 
	}

}
