/**
 * @author	Blandon Tang
 * @Date	Last Edited March 22, 2018
 * Seng 300 Iteration 2 of group project
 * Includes feature to recursively search through directory for .java files
 * TODO: Requires implementation of JAR file searching
 */
package counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

 
// Driver for program that relies on a static call to counter class
// Counter class creates instances of ASTParser for visiting ASTNodes
public class Main {

	static String pathname = "D:\\University\\SENG 300\\Testing"; 		// Change pathname to folder path for testing
	
	// Driver for program
	public static void main(String[] args) throws IOException {
	
		fileWalk(pathname);
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
					
						//String fileFound = javaFiles.getName();									// For testing purposes
						//System.out.println("\n" + fileFound);									// For testing purposes
						System.out.println();					//Line break - Optional for final product
						Counter.parse(ReadFileToCharArray(javaFiles.getAbsolutePath())); 		//do conversion for all .java files
					}
				}
			}
		}
		
		// ******
		// else if here for JAR file type - A pseudo switch for looking at the JAR type
		// ******
		// This is where the work for JAR file types should go
		// since a JAR file is essentially a zip file, I assume we need to open it up (with some variable type that accepts zip (jar) files)
		// Then we could make hash map or array to fileld with all individual files from JAR and then we just call our static parse method
		// (The parse method comes from the Counter file)
		// ******
	
	}
		
	
	
	
	// Responsible for taking a java file and converting it to a string then to a charArray
	public static char[] ReadFileToCharArray(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
 
		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			//System.out.println(numRead);
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}
 
		reader.close();
		
		char[] retCharArray = fileData.toString().toCharArray();
		//System.out.println(retCharArray); 				       //Print test - uncomment out to print what is written in .java files
 
		return  retCharArray;	
			
	}
	
	
}
