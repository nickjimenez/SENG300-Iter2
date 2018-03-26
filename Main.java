/**
 * @author	Blandon Tang
 * @Date	Last Edited March 22, 2018
 * Seng 300 Iteration 2 of group project
 * Includes feature to recursively search through directory for .java files
 * TODO: Requires implementation of JAR file searching
 */

/**
 * @author Daniel Nwaroh
 * @Date Last Edited March 23
 * SENG 300 Iteration 2 Group Project
 * Includes feature to recursively search through directory for .java files
 * Now searches JAR files and reads the .java files in them
 * In test jar file: Point.java and Average.java
 * How it works: To read through the jar file, i just extract all the files to new folder which is inside the directory being searched
 * 				 From there it parses as usual
 * Problem: The issue is that if the program is ran back to back with the same .jar file it will read the same file more than once
 * Possible Fix: After everything is done, we delete that unzipped version of the .jar file
 * @Date LAst Edited March 24
 * It now deletes the unzipped folder that gets created, it goes through each file in that directory and deletes each one before deleting the directory itself
 * TODO: Count the declarations and references and implement command line which shouldn't take too long
 * TODO: Document and clean up the code itself lol
 * TODO: If we have time, trim down the length of the code, i feel like i have some redundant lines of code in there that can be removed 
 */
//package counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.jar.*;
import java.util.zip.ZipFile;
import java.util.Enumeration;
import java.io.*;

 
// Driver for program that relies on a static call to counter class
// Counter class creates instances of ASTParser for visiting ASTNodes
public class Main {

	//public static String pathname = "C:\\Users\\Daniel Nwaroh\\Desktop\\300test"; 					// Change pathname to folder path for testing
	public static String pathname;
	public static String destdir = pathname + "\\newUnzip";														// New destination of the files we extracted for jar file
	public static String javaTypeName;
	
	// Driver for program
	public static void main(String[] args) throws IOException {
		setArgs(args);
		fileWalk(pathname);
		File directory = new File(destdir);
		delete(directory);
		System.out.println("Declarations: " + Counter.declarations);
		System.out.println("References: " + Counter.references);
	}
	
	// A Recursive function that looks inside a folder and creates a parser for every .java file found
	public static void fileWalk(String pathname) throws IOException {
				
		File folder = new File(pathname);
		
		if (folder.isDirectory()) {
			File[] listOfFiles = folder.listFiles();
			
			// Iterate through all java files in the folder
			for (File javaFiles : listOfFiles) {
				if (javaFiles.isDirectory()) {
					fileWalk(javaFiles.getAbsolutePath());		// recursive call for subfolders and their subfolders
				} 
			
				else {
					if (javaFiles.isFile() && javaFiles.getName().endsWith(".java")) {		
						Counter.parse(ReadFileToCharArray(javaFiles.getAbsolutePath())); 		//do conversion for all .java files
					}
					else if (javaFiles.isFile() && javaFiles.getName().endsWith(".jar")) {
						String fileFound = javaFiles.getName();	
						String newFolder = extractJAR(pathname + "\\" + fileFound);
						fileWalk(newFolder);																	
					}
				}
			}
		}
	}

	// Responsible for taking a java file and converting it to a string then to a charArray
	public static char[] ReadFileToCharArray(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
 
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
		reader.close();
		char[] retCharArray = fileData.toString().toCharArray();
		return retCharArray;				
	}
	
	//extracts the jar file into a directory within the current directory we are searching
	public static String extractJAR(String toBeExtracted) throws java.io.IOException {
		JarFile jarfile = new JarFile(new File(toBeExtracted));
		Enumeration<JarEntry> enu = jarfile.entries();						
	    while(enu.hasMoreElements())
	    {
	    	java.util.jar.JarEntry je = enu.nextElement();

	        File fl = new File(destdir, je.getName());
	        if(!fl.exists()) {
	            fl.getParentFile().mkdirs();
	            fl = new File(destdir, je.getName());
	        }
	        if(je.isDirectory()) {
	            continue;
	        }
	        InputStream is = jarfile.getInputStream(je);
	        FileOutputStream fo = new FileOutputStream(fl);
	        while(is.available()>0) {
	            fo.write(is.read());
	        }
	        fo.close();
	        is.close();
	    }
	    return destdir;
	  }
	
	public static void delete(File file) throws IOException{
		if(file.isDirectory()){
			if(file.list().length==0) {
				file.delete();
			} 
			else {
				//list all the directory contents
				String files[] = file.list();
				for (String temp : files) {
					//construct the file structure
					File fileDelete = new File(file, temp);
					//recursive delete
					delete(fileDelete);
				}
				//check the directory again, if empty then delete it
				if(file.list().length==0){
					file.delete();
				}
		    }
		}
		else {
			//if file, then delete it
			file.delete();
		}
	}
	
	public static void setArgs(String[] args) {
		try {
    		pathname = args[0];
    		javaTypeName = args[1];
    	} catch (ArrayIndexOutOfBoundsException e) {
    		System.out.println("Not enough arguments, please provide the pathname and the Java type.");
    	}
	}
	
}