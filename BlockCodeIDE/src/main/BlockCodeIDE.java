package main;

import java.util.ArrayList;
import java.util.List;

import main.ide.CodeBlockEditor;
import main.ide.ComponentButton;
import main.ide.ModuleToolbar;
import main.ide.UIComponent;

public class BlockCodeIDE {
	public static List<UIComponent> componentList = new ArrayList<UIComponent>();
	public static void init() {
		componentList.add(new ComponentButton("Run", 10, 10, 60, 40, new Thread() {public void run(){Main.testRun();}}));
		componentList.add(new CodeBlockEditor(10,60,new ModuleToolbar()));
	}
	
	public static void start() {
		
		while (true) {
			update();
			Main.c.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void update() {
		((Console) Main.console).updateStream();
		for (int i = 0; i < componentList.size(); i++) {
			componentList.get(i).update();
		}
	}
}
