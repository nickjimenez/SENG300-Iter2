// Currently reads jar files and stores class files, java files, and directory names in separate arraylists
// Creates a JarReader object for every jar file
// TODO: Convert/parse class files
// TODO: Implement reading java files from Main class

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.jar.*;

   public class JarReader{
	   public static JarFile jarFile;
	   public static ArrayList<String> classFileNames;
	   public static ArrayList<String> javaFileNames;
	   public static ArrayList<String> dirNames;
	   
	   public JarReader(String fileName) throws IOException{
		   this.jarFile = new JarFile(fileName);
		   this.classFileNames = new ArrayList<String>();
	   }

     
	   public static void getJarFiles() throws IOException{
		   Enumeration<JarEntry> entry = jarFile.entries();
		   
		   while(entry.hasMoreElements()){
			   String temp = entry.nextElement().getName();
			   System.out.println(temp);
			   if(temp.contains(".java")){
				   javaFileNames.add(temp);				   
			   }if(temp.contains(".class")){
				   classFileNames.add(temp);
			   }else{
				   dirNames.add(temp);
			   }
		   }
		   jarFile.close();
	   }
	   
//	   public static void process(String fileName) throws IOException {
//		   JarEntry entry = jarFile.getJarEntry(fileName);
//		   InputStream input = jarFile.getInputStream(entry);
//		   InputStreamReader isr = new InputStreamReader(input);
//		   BufferedReader reader = new BufferedReader(isr);
//		   String line;
//		   while ((line = reader.readLine()) != null) {
//			   System.out.println(line);
//		   }
//		   reader.close();
//     }
	   
//	   public static void main(String[] args) throws IOException{
//	   JarReader test = new JarReader("Check.jar");
//	   test.getJarFiles();
//	   test.process(fileNames.get(0));
//   }
}
           
         