package main.codeblockmodules;

public class StatusModule extends CodeBlockModule {
	
	String[] variables;
	
	public StatusModule(String cb, String i, String imp, String[] v) {
		super(cb,i,imp);
		variables = v;
	}
	
	public String[] getVariableNames() {
		return variables;
	}
}
