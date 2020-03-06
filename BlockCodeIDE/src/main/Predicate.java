package main;

import main.parameters.Parameter;

public class Predicate extends Variable {

	String name;
	Parameter a1;
	Parameter a2;
	String type;
	public Predicate(String name, Parameter arg1, Parameter arg2, String type) {
		super (name);
		this.name = name;
		this.a1 = arg1;
		this.a2 = arg2;
		this.type = type;
	}
	
	public String getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public String getTypeA() {
		return a1.getType();
	}
	
	public String getTypeB() {
		return a2.getType();
	}
}
