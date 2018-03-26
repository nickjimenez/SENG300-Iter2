/**
 *  @author Daniel Nwaroh, Blandon Tang, Kevin Lee, Kevin Huynh, Nickole Jiminez, Denis Shevchenko
 *  Iteration 2
 */
//package counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.jar.*;
import java.util.Enumeration;
import java.io.*;

// Driver for program that relies on a static call to counter class
// Counter class creates instances of ASTParser for visiting ASTNodes
public class Main {

	public static String pathname;
	public static String destdir = pathname + "\\newUnzip";														// New destination of the files we extracted for jar file
	public static String javaTypeName;
	
	// Driver for program
	public static void main(String[] args) throws IOException {
		setArgs(args);
		fileWalk(pathname);
		File directory = new File(destdir);
		delete(directory);
		Counter.printOutput();
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