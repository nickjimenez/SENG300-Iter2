//To be used in tandem with counter class
package counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

 
// Takes files within the directory path (variable listed as folder)
// converts all found .java files into charArray 
public class Main {

	static String pathname = "D:\\University\\SENG 300\\Testing"; 						//"D:\\eclipse-workspace\\Counter"
	
	// Driver for program
	public static void main(String[] args) throws IOException {
	
		fileWalk(pathname);
	}
	
	
	public static void fileWalk(String pathname) throws IOException {
				
		File folder = new File(pathname);			// Replace pathName with actual path i.e D:\\eclipse-workspace\\File Reader
		File[] listOfFiles = folder.listFiles();
				
		// Iterate through all java files in the folder
		
		// Nothing in folder exit
		if (listOfFiles == null)
				return;
		
		for (File javaFiles : listOfFiles) {
			if (javaFiles.isDirectory()) {
				fileWalk(javaFiles.getAbsolutePath());
			}
			
			else {
				if (javaFiles.isFile() && javaFiles.getName().endsWith(".java")) {		
					//Counter.parse(ReadFileToCharArray(javaFiles.getName())); 		//do conversion for all .java files
					String fileFound = javaFiles.getName();
					//char[] returnedCharArray = ReadFileToCharArray(javaFiles.getName());  	// the return of the method can be stored for use later with ASTParser
					System.out.println(fileFound);									// For testing purposes
					// returnedCharArray holds the char[] resulting from ReadFileToCharArray method
				}
			}	
		}
		
		// else if (//this is a pseudo switch for jar) in the event that the program needs to handle both jar and recursive file walking
		
		
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
