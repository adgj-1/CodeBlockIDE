package main;

public class Variable {

	String name;
	String displayName;
	String className;
	public Variable(String name) {
		this.name = name;
		if (name.indexOf(".") != -1) {
			displayName = name.split("\\.")[1];
			className = name.split("\\.")[0];
		} else {
			displayName = name;
			className = "";
		}
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public String getClassName() {
		return className;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return "";
	}
	
	public void setName(String name) {
		this.name = name;
		if (name.indexOf(".") != -1) {
			displayName = name.split("\\.")[1];
			className = name.split("\\.")[0];
		} else {
			displayName = name;
			className = "";
		}
	}
}
