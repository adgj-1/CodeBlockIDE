package main;

import java.util.ArrayList;
import java.util.List;

public class MethodHandler {
	public static final String DEFAULT_METHOD = "";
	public static String methods = DEFAULT_METHOD;
	public static List<Method> methodList = new ArrayList<Method>();
	public static void loadMethods() {
		for (String fv : CodeModuleManager.functionVariables) {
			methodList.add(new Method(fv, ""));
		}
		updateMethods();
	}
	
	public static void updateMethods() {
		methods = DEFAULT_METHOD;
		for (Method m : methodList) {
			methods = methods.concat(m.getMethod());
		}
	}
	
	public static void setMethodContent(String methodName, String content) {
		for (Method m : methodList) {
			
			if (m.getName().equals(methodName)) {
				m.setContent(content);
				updateMethods();
			}
		}
	}
}
