//package counter;
/**
 *  @author Daniel Nwaroh, Blandon Tang, Kevin Lee, Kevin Huynh, Nickole Jiminez, Denis Shevchenko
 *  Iteration 2
 */

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class Counter{
	
	public static int declarationsFound = 0;
	public static int referencesFound = 0;
	public static ArrayList<String> refs = new ArrayList<String>();
	
	public static void parse(char[] str) {
		@SuppressWarnings("deprecation")
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setResolveBindings(true);
		parser.setSource(str);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setBindingsRecovery(true);
		
		// setEnvironment and setUnitName is required to run resolveBindings
		parser.setEnvironment(null, null, null, true);
		parser.setUnitName("");
		 

		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		cu.accept(new ASTVisitor() { 
			// Checks for annotation type declarations 
			public boolean visit(AnnotationTypeDeclaration node) {
				ITypeBinding  binding = node.getName().resolveTypeBinding();
				//String annotType = binding.getQualifiedName();
				//System.out.println("Annotations: " + annotType);
				return true;
			}
			// Checks for Classes/interface declarations
			public boolean visit(TypeDeclaration node) {
				//SimpleName name = node.getName();
				//System.out.println("Classes/interface: " + name);
				return true;
			}
			
			// With added import delcaration
			public boolean visit(ImportDeclaration node) {
				return true;
			}
			// Currently prints out the type and respective variable names
			public boolean visit(VariableDeclarationFragment node) {
				IVariableBinding binding = node.resolveBinding();
				String types = binding.getType().getName();
				if (types.equals(Main.javaTypeName) == true) {										//Change "int" to whatever value the user input
					String anode = node.toString();
					String arr[] = anode.split("=", 2);
					String first = arr[0];
					declarationsFound++;
					if ((node.getRoot().toString().contains(first))) {
						referencesFound++;
					}
				}

				return false;
			}		
		});
	}
	
	public static void printOutput() {
		System.out.println(Main.javaTypeName + ". Declarations found: " + declarationsFound + "; references found: " + referencesFound);
	}


}