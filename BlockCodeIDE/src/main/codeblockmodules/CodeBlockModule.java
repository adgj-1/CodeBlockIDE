package main.codeblockmodules;

public class CodeBlockModule {

	String classBlock;
	String initiator;
	String imports;
	public CodeBlockModule(String cb, String i, String imp) {
		classBlock = cb;
		initiator = i;
		imports = imp;
	}
	
	public String getClassBlock() {
		return classBlock;
	}
	
	public String getInitiator() {
		return initiator;
	}
	
	public String getImports() {
		return imports;
	}
	
}
