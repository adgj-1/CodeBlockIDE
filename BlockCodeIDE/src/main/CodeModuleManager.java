package main;

import java.util.ArrayList;
import java.util.List;

import main.codeblockmodules.CallerModule;
import main.codeblockmodules.CodeBlockModule;
import main.codeblockmodules.FuncModule;
import main.codeblockmodules.StatusModule;

public class CodeModuleManager {

	public static String imports = "import javax.swing.JFrame;\n";
	public static String initiator = "\n";
	public static String classes = "\n";
	public static List<String> functionVariables = new ArrayList<String>();
	public static List<String> variables = new ArrayList<String>();
	public static List<CodeBlockModule> moduleList = new ArrayList<CodeBlockModule> ();
	public static List<String> functions = new ArrayList<String>();
	public static void init() {
		CodeBlockModule mml = new StatusModule("class MouseMotionListener_ implements MouseMotionListener {\r\n" + 
													"\r\n" + 
													"public static int mouseX;\r\n" +
													"public static int mouseY;\r\n" +
													"	@Override\r\n" + 
													"	public void mouseDragged(MouseEvent e) {\r\n" + 
													"		// TODO Auto-generated method stub\r\n" + 
													"\r\n" + 
													"	}\r\n" + 
													"\r\n" + 
													"	@Override\r\n" + 
													"	public void mouseMoved(MouseEvent e) {\r\n" + 
													"		mouseX ="+ "e" +".getX();\r\n" + 
													"		mouseY ="+ "e" +".getY();\r\n" + 
													"\r\n" + 
													"	}\r\n" + 
													"\r\n" + 
													"}", 
													"j.addMouseMotionListener(new MouseMotionListener_());",
													"import java.awt.event.*;",
													new String[] {"int MouseMotionListener_.mouseX",
																	"int MouseMotionListener_.mouseY"});
		moduleList.add(mml);
		
		CodeBlockModule ml = new CallerModule("class MouseListener_ implements MouseListener {\r\n" + 
													"\r\n" + 
													"	@Override\r\n" + 
													"	public void mouseClicked(MouseEvent e) {\r\n" + 
													"		TempCode.click();\r\n" + 
													"\r\n" + 
													"	}\r\n" + 
													"\r\n" + 
													"	@Override\r\n" + 
													"	public void mousePressed(MouseEvent e) {\r\n" + 
													"		TempCode.press();\r\n" + 
													"\r\n" + 
													"	}\r\n" + 
													"\r\n" + 
													"	@Override\r\n" + 
													"	public void mouseReleased(MouseEvent e) {\r\n" + 
													"		TempCode.release();\r\n" + 
													"\r\n" + 
													"	}\r\n" + 
													"\r\n" + 
													"	@Override\r\n" + 
													"	public void mouseEntered(MouseEvent e) {\r\n" + 
													"		// TODO Auto-generated method stub\r\n" + 
													"\r\n" + 
													"	}\r\n" + 
													"\r\n" + 
													"	@Override\r\n" + 
													"	public void mouseExited(MouseEvent e) {\r\n" + 
													"		// TODO Auto-generated method stub\r\n" + 
													"\r\n" + 
													"	}\r\n" + 
													"\r\n" + 
													"}",
													"j.addMouseListener(new MouseListener_());",
													"import java.awt.event.*;",
													new String[] {"public static void click()",
															"public static void press()",
															"public static void release()"});
		moduleList.add(ml);
		CodeBlockModule commonFunctions = new FuncModule("","","",new String[] {"if(boolean status)"});
		moduleList.add(commonFunctions);
		loadModules();
	}
	
	public static void loadModules() {
		for (CodeBlockModule cbm : moduleList) {
			if (imports.indexOf(cbm.getImports()) == -1) {
				imports = imports.concat(cbm.getImports() + "\n");
			}
			initiator = initiator.concat(cbm.getInitiator() + "\n");
			classes = classes.concat(cbm.getClassBlock() + "\n");
			if (cbm instanceof CallerModule) {
				for (String fn : ((CallerModule)cbm).getFunctionNames()) {
					functionVariables.add(fn);
				}
			} else if (cbm instanceof StatusModule) {
				for (String v : ((StatusModule)cbm).getVariableNames()) {
					variables.add(v);
				}
			} else if (cbm instanceof FuncModule) {
				for (String v : ((FuncModule)cbm).getFunctionNames()) {
					functions.add(v);
				}
			}
		}
	}
}
