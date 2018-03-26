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
	
	// Tests for a qualified name in declaration Visitors but no type. 
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
	// Tests for declarations and references
	@Test
	public void testValidDeclaration0() throws IOException {
		String path = BASEDIR + "TestFiles";
		String[] args = {path, "String"};
		//String type = "String"
		Main.main(args);	
		assertEquals(3, Counter.getDec());
		assertEquals(3, Counter.getRef());
	}
	// Tests for declarations and references
	@Test
	public void testValidDeclaration1() throws IOException {
		String path = BASEDIR + "TestFiles";
		String[] args = {path, "Foo"};
		//String type = "Foo"
		Main.main(args);	
		assertEquals(3, Counter.getDec());
		assertEquals(3, Counter.getRef());
	}
	
	// empty Jar file
	@Test
	public void testEmptyJar() throws IOException {
		String path = BASEDIR + "TestFiles/EmptyFolder.jar";
		String[] args = {path, "int"};
		Driver.main(args);
		assertEquals(0, Counter.getDec());
	}
	
	// No references or declarations jar file
	@Test
	public void testAbsentType() throws IOException {
		String path = {BASEDIR + "TestFiles/InnerFolder01.jar"};
		String[] args = {path, "int"};
		Driver.main(args);
		assertEquals(0, Counter.getDec());
	}
	
	// has declarations and references
	@Test
	public void testPresentType() throws IOException {
		String path = {BASEDIR + "TestFiles/InnerFolder01.jar"};
		String[] args = {path, "String"};
		Driver.main(args);
		assertNotNull(Counter.getDec());
		assertNotNull(Counter.getRef());
	}
}








