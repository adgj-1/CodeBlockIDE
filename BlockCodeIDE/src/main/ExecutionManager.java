package main;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class ExecutionManager {
public static final String className = "TempCode";
public static String gamecode = "";
static Path temp = Paths.get(System.getProperty("java.io.tmpdir"), className);

static String testCode;
static boolean logOutput = true;
public static void createTempAndRun() throws IOException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
	Files.createDirectories(temp);
	Path javaSourceFile = Paths.get(temp.normalize().toAbsolutePath().toString(), className + ".java");
	String code = CodeModuleManager.imports +   "public class " + className + " {" +
												"public static JFrame j = new JFrame(\"Custom Program\");\n" +
												"public static void main(String[] args) {\n" +
												"       System.out.println(\"Running Compiled Program...\"); \n" +
												"       " + CodeModuleManager.initiator + "\r\n" +
												"       j.setSize(400, 400);\n" + 
												"       j.setVisible(true);" +
												gamecode +
												"\n    }\r\n" +
												MethodHandler.methods +
												"}" +
												CodeModuleManager.classes;
	Files.write(javaSourceFile, code.getBytes());
	testCode = code;
	compile(javaSourceFile);
	runClass();
}

@SuppressWarnings("rawtypes")
public static void compile(Path javaSourceFile) throws IOException {
	//Definition of file to compile
	File[] files1 = {javaSourceFile.toFile()};
	
	JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
	
	StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
	
	//Create a compilation unit (files)
	Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(files1));
	
	//Feedback object to get errors
	DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
	
	//Compilation unit can be created and called only once
	JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits);
	
	task.call();
	
	for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
		System.out.format("Error on line %d in %s%n", diagnostic.getLineNumber(), diagnostic.getSource());
		System.out.println(diagnostic.getMessage(null));
		
	}
	if (logOutput) {
		if (diagnostics.getDiagnostics().size() == 0) {
			System.out.println("Program Successfully Compiled...");
		}
		int index = 1;
		String prev = "0: ";
		String rest = testCode;
		while (true) {
			int lineIndex = rest.indexOf("\n");
			if (lineIndex != -1) {
				prev = prev + rest.substring(0,lineIndex) + "\n" + index + ": ";
				rest = rest.substring(lineIndex).replaceFirst("\n", "");
				index++; 
			} else {
				break;
			}
		}
		System.out.print("============================\r\n" +
				prev +
				"\r============================\r\n");
	}
	fileManager.close();
}

@SuppressWarnings({ "unchecked", "rawtypes", "resource" })
public static void runClass() throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException {
	ClassLoader classLoader = ExecutionManager.class.getClassLoader();
	URLClassLoader urlClassLoader = new URLClassLoader(new URL[] {temp.toUri().toURL()}, classLoader);
	
	Class tempclass = urlClassLoader.loadClass(className);
	Method method = tempclass.getMethod("main", String[].class);
	method.invoke(null, new Object[1]);
}
}
