package main;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main {

	public static JFrame j = new JFrame("Block Code IDE");
	public static JPanel c = new Canvas();
	public static JPanel console = new Console();
	public static MouseListener ml = new MouseListener_();
	public static MouseMotionListener mml = new MouseMotionListener_();
	public static void main(String[] args) {
		j.setLayout(new BorderLayout());
		j.add(c, BorderLayout.CENTER);
		j.add(console, BorderLayout.LINE_END);
		c.addMouseListener(ml);
		c.addMouseMotionListener(mml);
		c.setLayout(null);
		j.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//j.setSize(800, 800);
		
		j.setVisible(true);
		CodeModuleManager.init();
		MethodHandler.loadMethods();
		VariableHandler.loadVariables();
		BlockCodeIDE.init();
		BlockCodeIDE.start();
		//testing stuff
//		MethodHandler.setMethodContent("click", "System.out.println(\"mouse clicked\");" +
//		"j.setSize(" + VariableHandler.variableList.get(0).name + "," + VariableHandler.variableList.get(1).name + ");");
//		testRun();
		
	}
	
	public static void testRun() {
		//ExecutionManager.gamecode = "System.out.println((2 * 2) + 5);";
		((Console) console).clear();
		try {
			ExecutionManager.createTempAndRun();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
