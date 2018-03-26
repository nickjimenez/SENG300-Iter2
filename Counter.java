//package counter;
//Main method is used to test

//Current version only prints out the outputs into console  

//import java.io.IOException;

//import javax.annotation.processing.SupportedAnnotationTypes;

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
//import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
//import org.eclipse.jdt.core.dom.MarkerAnnotation;
//import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class Counter{
	
	public static int declarationsFound = 0;
	public static int referencesFound = 0;
	public static int x = 0;
	public static int y = 0;
	public static boolean found = false;
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
			// Doesn't work as intended
			// Checks for annotation type declarations 
			public boolean visit(AnnotationTypeDeclaration node) {
				ITypeBinding  binding = node.getName().resolveTypeBinding();
				String annotType = binding.getQualifiedName();
				System.out.println("Annotations: " + annotType);
				return true;
			}
			// Checks for Classes/interface declarations
			public boolean visit(TypeDeclaration node) {
				SimpleName name = node.getName();
				System.out.println("Classes/interface: " + name);
				return true;
			}
			
			// With added import delcaration
			public boolean visit(ImportDeclaration node) {
				
				String importName = node.getName().toString();
				System.out.println("Import: " + importName);
				
				return true;
			}
			
		
			
			// Currently prints out the type and respective variable names
			public boolean visit(VariableDeclarationFragment node) {
				found = false;
				IVariableBinding binding = node.resolveBinding();
				String types = binding.getType().getName();
				//System.out.println(types.equals("int"));
				if (types.equals("int") == true) {										//Change "int" to whatever value the user input
					System.out.println(node);
					String anode = node.toString();
					String arr[] = anode.split("=", 2);
					String first = arr[0];
					//System.out.println(first);
					//System.out.println("vdf");
					declarationsFound++;
					x = 1;
					//found = true;
					if ((node.getRoot().toString().contains(first))) {
						referencesFound++;
					}
					//System.out.println(found);
				}
//				String anode = node.toString();
//				String arr[] = anode.split("=", 2);
//				String first = arr[0];
//				if ((x == 1) && (node.getRoot().toString().contains(first))) {
//					System.out.println("line 99" + first);
//					referencesFound++;
//				}
				
				String name = binding.getName();
					
				//System.out.println(declarationsFound);
				System.out.printf("Variables: %s Name: %s\n",types, name);
				//System.out.println(name.getClass().getSimpleName());
				//System.out.println("int" + ". Declarations found: " + declarationsFound + "; references found: ");
				return false;
			}
			
//			public boolean visit(SimpleName node) {
//				String name = node.getFullyQualifiedName();
//				//System.out.println(node);
//				System.out.println(name + "||" + node.getRoot().toString());
//				
//				if ((x == 1) && (node.getRoot().toString().contains(name))) {
//					System.out.println("found");
//					//x = 0;
//					if (found == false) {
//						x = 0;
//					}
//					if (found == true) {
//						referencesFound++;
//						//System.out.println(referencesFound);
//						//System.out.println("okay");
//					}
//				}
//				
//				//System.out.println(refs);
////				if (x == 1) {
////					refs.add(name);
////					x = 0;
////				}
////				for (int i = 0; i < refs.size(); i++) {
////					if (name.equals(refs.get(i))) {
////						referencesFound++;
////					}
////				}
//				//System.out.println("Name:" + name + " Ref found: " + referencesFound);
//				return true;
//			}
			
		});
	}
	
	public void printOutput() {
		System.out.println("int" + ". Declarations found: " + declarationsFound + "; references found: " + referencesFound);
	}


}