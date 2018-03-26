import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Before;
import org.junit.Test;

/**
* JUnit Test class
*/

public class Test{

	// Edit to reflect your base address.
	public static String BASEDIR = "src/";
	
	// Checks if the main method correctly identifies and rejects a
	// nonexistent invalid directory
	@Test(expected = NullPointerException.class)
	public void testInvalidDirectory() throws IOException {
		String[] args = { "NoneExistentDir"};
		Main.main(args);
	} 
	
	// Checks if the main method correctly accepts a proper directory
	@Test
	public void testValidDirectory() throws IOException {
		String[] args = {BASEDIR};
		Main.main(args);
	}

	// Tests the reference Visitors to correctly ignore type names that are
	// unqualified
	@Test
	public void testUnqualifiedReference() throws IOException {
		//Counter count = new Counter();
		String[] args = {BASEDIR};
		Main.main(args);
		assertEquals(0, Counter.getDec());
	}
	
	// Tests for a qualified name in declaration Visitors. 
	@Test
	public void testValidDeclaration() throws IOException {
		String path = BASEDIR;
		String[] args = {path};
		Main.main(args);	
		assertEquals(0, Counter.getDec());
	}
	
	// Tests the declaration Visitors for an incorrect, unqualified name
	@Test
	public void testInvalidDeclaration() throws IOException {
		String[] args = {BASEDIR};
		Main.main(args);
		assertEquals(0, Counter.getDec());
	}
	
	// Tests that the file converter ignores none .java files
	@Test
	public void testNotJavaFile() {
		String directory = BASEDIR + "TestFiles/TestTxt.txt"; 
		
	}
	
}








