

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
 
public class Counter{
	// Used to count declarations
	// Key: String of the type
	// Value: Integer count of the number of declaration of the type
	static HashMap declare = new 	HashMap<String, Integer>();
	static HashMap reference = new 	HashMap<String, Integer>();
	public static void parse(char[] str) {
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
			// Adds package to the declaration counter
			public boolean visit(PackageDeclaration node) {
				String packageName = node.getName().toString();
				if(declare.containsKey(packageName)){
					int value = (int) declare.get(packageName);
					value++;
					declare.put(packageName, value);
				}else{
					int value = 1;
					declare.put(packageName, value);
				}	
				return true;
			}
			
			// Adds imports to the declaration counter
			public boolean visit(ImportDeclaration node) {
				String importName = node.getName().toString();
				if(declare.containsKey(importName)){
					int value = (int) declare.get(importName);
					value++;
					declare.put(importName, value);
				}else{
					int value = 1;
					declare.put(importName, value);
				}	
				return true;
			}
			
			// Adds methods and its parameters declaration
			public boolean visit(MethodDeclaration node){
				String methName = node.getName().toString();
				IMethodBinding binding = node.resolveBinding();
				String test = binding.getReturnType().toString();
				System.out.println(test);
				if(reference.containsKey(test)){
					System.out.println(test);
				}
				
				// add the method name to the declaration counter
				if(declare.containsKey(methName)){
					int value = (int) declare.get(methName);
					value++;
					declare.put(methName, value);
				}else{
					int value = 1;
					declare.put(methName, value);
					reference.put(methName, 0);
				}	

				// checks every parameter
				for(Object param : node.parameters()){
					// varDecalare = Variable type + variable name
					VariableDeclaration varDeclare = (VariableDeclaration) param;
					// varType = only Variable type
					String varType = varDeclare.getStructuralProperty(SingleVariableDeclaration.TYPE_PROPERTY).toString();
					// add parameters to the declaration counter
					if(declare.containsKey(varType)){
						int value = (int) declare.get(varType);
						value++;
						declare.put(varType, value);
					}else{
						int value = 1;
						declare.put(varType, value);
					}	
				}
				return true;
			}

			// Checks for Classes/interface declarations
			public boolean visit(TypeDeclaration node) {
				ITypeBinding binding = node.resolveBinding();
				String key = binding.getName().toString();
				// adds to the declaration counter
				if(declare.containsKey(key)){
					int value = (int) declare.get(key);
					value++;
					declare.put(key, value);
				}else{
					int value = 1;
					declare.put(key, value);
				}	
				
				return true;
			}

			// Checks every variable declared and increases the declare hash map counter by one according to their respective types
			// Doesn't add primitives to declare hash map
			// Also covers fields
			public boolean visit(VariableDeclarationFragment node) {
				// used to check variables for primitive types
				// primitives are never declared, only referenced from java.lang.(insert primitive type)
				//String primitives [] = { "boolean", "byte", "char", "short", "int", "long", "float", "double" };
				String primitives = "boolean byte char short int long float double";
				IVariableBinding binding = node.resolveBinding();
				String key = binding.getType().getName();
				
				// as long as the variable isn't a primitive type, increase the counter of the declare hash map by 1
				if(!(primitives.contains(key))){
					// adds to the declaration counter
					if(declare.containsKey(key)){
						int value = (int) declare.get(key);
						value++;
						declare.put(key, value);
					}else{
						int value = 1;
						declare.put(key, value);
					}					
				}
				return false;
			}			
		});
	}
 
	// for debugging purposes
	// prints every key their respective count in the declare hashmap
	public static void printDeclare(){
		Set keys = declare.keySet();
		Iterator iter = keys.iterator();
		while(iter.hasNext()){
			String key = (String) iter.next();
			int value = (int) declare.get(key);
			System.out.println(key + ". Declarations found: " + value); // used for debugging
		}
		
	}
	public static void printReference(){
		Set keys = reference.keySet();
		Iterator iter = keys.iterator();
		while(iter.hasNext()){
			String key = (String) iter.next();
			int value = (int) reference.get(key);
			System.out.println(key + ". reference found: " + value); // used for debugging
		}
		
	}
}
