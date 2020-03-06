package main.codeblockmodules;

public class CallerModule extends CodeBlockModule {

	String[] functionNames;
	public CallerModule(String cb, String i, String imp, String[] fn) {
		super(cb, i, imp);
		functionNames = fn;
	}

	public String[] getFunctionNames() {
		return functionNames;
	}
}
