/**
 * @author	Blandon Tang
 * @Date	Last Edited March 25, 2018
 * Seng 300 Iteration 2 of group project
 * The counter portion of the project. Utilizes a hash map to count declarations and references
 * 
 */

package counter;

import java.util.HashMap;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.PrimitiveType;


// This class is responsible for counting the declarations and references as well as the visitation
// AST nodes in order to find number of declarations and references
// Declarations and References stored in hash maps where the key (string type) is the variable type and
// value is the number found
public class Counter{

	static HashMap<String, Integer> declarations = new HashMap<String, Integer>();
	static HashMap<String, Integer> references = new HashMap<String, Integer>();
	
	public static void parse(char[] charArray){
		
		@SuppressWarnings("deprecation")
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		
		// Initialization of Parser
		parser.setSource(charArray);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setCompilerOptions(null);
		parser.setBindingsRecovery(true);
		parser.setResolveBindings(true);
		parser.setUnitName("");
				
		parser.setEnvironment(null, null, null, true);
			
		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
 		
		
		cu.accept(new ASTVisitor() {
			
			// Count number of normal Annotation types
			public boolean visit (NormalAnnotation node) {
				String normAnnot = node.resolveTypeBinding().getQualifiedName();
				
				if (references.containsKey(normAnnot)) 
					references.put(normAnnot, references.get(normAnnot)+1);	
				
				else 
					references.put(normAnnot, 1);
				
				return true;
			}
			
			// Count number of marker annotation types 
			public boolean visit (MarkerAnnotation node) {
				String markerAnnot = node.resolveTypeBinding().getQualifiedName();
				
				if (references.containsKey(markerAnnot)) 
					references.put(markerAnnot, references.get(markerAnnot)+1);	
					
				else 
					references.put(markerAnnot, 1);
				
				return true;
			}
			
			// Count number of annotation declarations
			public boolean visit(AnnotationTypeDeclaration node) {			
				String annotDec = node.resolveBinding().getQualifiedName();
				
				if (declarations.containsKey(annotDec)) 
					declarations.put(annotDec, declarations.get(annotDec)+1);	
								
				else 
					declarations.put(annotDec, 1);
				
				return true;
			}
			
			// Count number of type declarations
			public boolean visit(TypeDeclaration node) {
				String typeDec = node.resolveBinding().getQualifiedName();
				
				if (declarations.containsKey(typeDec)) 
					declarations.put(typeDec, declarations.get(typeDec)+1);	
					
				else 
					declarations.put(typeDec, 1);
				
				return true;
			}
			
			// Count number of enumeration declarations
			public boolean visit(EnumDeclaration node) {												
				String enumDec = node.resolveBinding().getQualifiedName();
				
				if (declarations.containsKey(enumDec)) 
					declarations.put(enumDec, declarations.get(enumDec)+1);	
								
				else 
					declarations.put(enumDec, 1);
				
				return true;
			}
		
			// count number of simple type references
			public boolean visit(SimpleType node) { 
				String simType = node.resolveBinding().getQualifiedName();
				
				if (references.containsKey(simType)) 
					references.put(simType, references.get(simType)+1);	
				
				else 
					references.put(simType, 1);
				
				return true;
			}
			
			// Count number of primitive types
			public boolean visit(PrimitiveType node) {	
				String primType = node.resolveBinding().getQualifiedName();
				
				if (references.containsKey(primType)) 
					references.put(primType, references.get(primType)+1);	
					
				else 
					references.put(primType, 1);
				
				return true;
			}
			

		});
		
	}
 

}
