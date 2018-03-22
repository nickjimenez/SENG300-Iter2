//To be used in tandem with counter class
package counter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

 
// Takes files within the directory path (variable listed as folder)
// converts all found .java files into charArray 
public class Main {

	// Driver for program
	public static void main(String[] args) throws IOException {
				
		File folder = new File("D:\\eclipse-workspace\\Counter");			// Replace pathName with actual path i.e D:\\eclipse-workspace\\File Reader
		File[] listOfFiles = folder.listFiles();
				
		// Iterate through all java files in the folder
		for (File javaFiles : listOfFiles) {
			if (javaFiles.isFile() && javaFiles.getName().endsWith(".java")) {		
				Counter.parse(ReadFileToCharArray(javaFiles.getName())); 		//do conversion for all .java files
				// char[] returnedCharArray = ReadFileToCharArray(javaFiles.getName());  	// the return of the method can be stored for use later with ASTParser
				// returnedCharArray holds the char[] resulting from ReadFileToCharArray method
				
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
