package main;

import java.util.ArrayList;
import java.util.List;

import main.parameters.BoolParam;
import main.parameters.NaturalParam;
import main.parameters.Parameter;
import main.variables.Natural;

public class VariableHandler {
	
	public static List<Variable> variableList = new ArrayList<Variable>();
	public static List<Method> functionList = new ArrayList<Method>();
	public static void loadVariables() {
		for (String v : CodeModuleManager.variables) {
			variableList.add(generateVariable(v));
		}
		
		for (String v : CodeModuleManager.functions) {
			functionList.add(generateFunction(v));
		}
		
		variableList.add(generateVariable("boolean true"));
		variableList.add(generateVariable("boolean false"));
		variableList.add(generateVariable("pred + int int int"));
		variableList.add(generateVariable("pred - int int int"));
		variableList.add(generateVariable("pred == int int boolean"));
		variableList.add(generateVariable("pred && boolean boolean boolean"));
		variableList.add(generateVariable("pred || boolean boolean boolean"));
		variableList.add(generateVariable("pred > int int boolean"));
		variableList.add(generateVariable("pred < int int boolean"));
	}
	
	
	public static Variable generateVariable(String variableName) {
		Variable res;
		String[] cleanedVarset = variableName.split(" ");
		switch (cleanedVarset[0]) {
		case "int": {
			res = new Natural(cleanedVarset[1]);
			break;
		}
		case "float": {
			res = new Natural(cleanedVarset[1]);
			break;
		}
		case "double": {
			res = new Natural(cleanedVarset[1]);
			break;
		}
		case "boolean": {
			res = new main.variables.Boolean(cleanedVarset[1]);
			break;
		}
		case "pred": {
			res = new main.Predicate(cleanedVarset[1], chooseParam(cleanedVarset[2]), chooseParam(cleanedVarset[3]), cleanedVarset[4]);
			break;
		}
		default: {
			res = new Variable(cleanedVarset[1]);
			break;
		}
		}
		return res;
	}
	
	public static Parameter chooseParam(String type) {
		Parameter res;
		switch (type) {
		case "int": {
			res = new NaturalParam(type);
			break;
		}
		case "float": {
			res = new NaturalParam(type);
			break;
		}
		case "double": {
			res = new NaturalParam(type);
			break;
		}
		
		case "boolean": {
			res = new BoolParam(type);
			break;
		}
		
		default: {
			res = new Parameter(type);
			break;
		}
		}
		return res;
		}
	
	public static Method generateFunction(String functionName) {
		return new Method(functionName, "");
	}
}
