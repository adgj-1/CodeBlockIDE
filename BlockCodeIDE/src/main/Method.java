package main;

import main.parameters.BoolParam;
import main.parameters.NaturalParam;
import main.parameters.Parameter;

public class Method {

	String identifier;
	String content;
	public Method(String identifier, String content) {
		this.identifier = identifier;
		this.content = content;
	}
	
	public String getMethod() {
		String res = identifier + "{\r\n" + content + "}\r\n";
		return res;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getName() {
		String cut = identifier.substring(0,identifier.indexOf("("));
		return cut.substring(cut.lastIndexOf(" ") + 1);
	}
	
	public Parameter[] getParameters() {
		String cut = identifier.substring(identifier.indexOf("(") + 1,identifier.indexOf(")"));
		if (cut.equals("")) {
			return new Parameter[] {};
		}
		String[] vStr = cut.split(",");
		Parameter[] res = new Parameter[vStr.length];
		for (int i = 0; i < vStr.length; i++) {
			String[] varset = vStr[i].split(" ");
			String[] cleanedVarset= removeAllEmpty(varset);
			switch (cleanedVarset[0]) {
			case "int": {
				res[i] = new NaturalParam(cleanedVarset[1]);
				break;
			}
			case "float": {
				res[i] = new NaturalParam(cleanedVarset[1]);
				break;
			}
			case "double": {
				res[i] = new NaturalParam(cleanedVarset[1]);
				break;
			}
			
			case "boolean": {
				res[i] = new BoolParam(cleanedVarset[1]);
				break;
			}
			
			default: {
				res[i] = new Parameter(cleanedVarset[1]);
				break;
			}
			}
		}
		return res;
	}
	
	private static String[] removeAllEmpty(String[] seq) {
		int spacecount = 0;
		for (int i = 0; i < seq.length; i++) {
			if (seq[i].equals("")) {
				spacecount++;
			}
		}
		String[] res = new String[seq.length - spacecount];
		int index = 0;
		for (int i = 0; i < seq.length; i++) {
			if (!seq[i].equals("")) {
				res[index] = seq[i];
				index++;
			}
		}
		return res;
	}

	public String getContent() {
		// TODO Auto-generated method stub
		return content;
	}

	public String getFunctionalMethod(String params) {
		String res = getName() + "(" + params + ")" + "{\r\n" + content + "}\r\n";
		return res;
	}
	
}
