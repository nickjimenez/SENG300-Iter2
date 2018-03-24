//package counter;
//Main method is used to test

//Current version only prints out the outputs into console  

//import java.io.IOException;

//import javax.annotation.processing.SupportedAnnotationTypes;

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
				IVariableBinding binding = node.resolveBinding();
				String types = binding.getType().getName();
				String name = binding.getName();
					
				
				System.out.printf("Variables: %s Name: %s\n",types, name);
				return false;
			}			
		});
	}


}