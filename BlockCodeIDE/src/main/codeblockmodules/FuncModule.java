package main.codeblockmodules;

public class FuncModule extends CodeBlockModule {
	String[] variable;
	public FuncModule(String cb, String i, String imp, String[] fn) {
		super(cb, i, imp);
		variable = fn;
	}

	public String[] getFunctionNames() {
		// TODO Auto-generated method stub
		return variable;
	}

}
