package main.ide;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Scrollbar;
import java.util.ArrayList;
import java.util.List;

import main.Main;
import main.Method;
import main.MethodHandler;
import main.MouseListener_;
import main.MouseMotionListener_;
import main.Predicate;
import main.Variable;
import main.VariableHandler;
import main.ide.modules.ConstantFunctionModule;
import main.ide.modules.ConstantModule;
import main.ide.modules.FunctionModule;
import main.ide.modules.MethodModule;
import main.ide.modules.Module;
import main.ide.modules.PredicateModule;
import main.ide.modules.VariableModule;
import main.variables.Natural;

public class ModuleToolbar extends UIComponent {
	int width;
	int height;
	int scrollPos = 0;
	public List<Module> modules = new ArrayList<Module>();
	CodeBlockEditor parent;
	private boolean canClick;
	Scrollbar sb;
	public ModuleToolbar() {
		for (Method m : MethodHandler.methodList) {
			modules.add(new MethodModule(m));
		}
		
		modules.add(new ConstantFunctionModule(new Method("Print(int num)", ""), "System.out.println(arg0)"));
		
		for (Method v : VariableHandler.functionList) {
			modules.add(new FunctionModule(v));
		}
		
		for (Variable v : VariableHandler.variableList) {
			if (v instanceof Predicate) {
				modules.add(new PredicateModule((Predicate) v));
			} else {
				modules.add(new VariableModule(v));
			}
		}
		
		
		modules.add(new ConstantModule(new main.variables.Boolean("")));
		modules.add(new ConstantModule(new Natural("")));
		width = 200;
		height = 800;
		
		int posY = y - scrollPos + 20;
		for (int i = 0; i < modules.size(); i++) {
			posY += modules.get(i).getHeight() + 10;
		}
		
		sb = new Scrollbar();
		sb.setBounds(x, y, 15, height);
		if (posY > height) {
			sb.setValues(0, 10, 0, (posY - height) * 2);
		}
		Main.c.add(sb);
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, width, height);
		int posY = y - scrollPos + 20;
		for (int i = 0; i < modules.size(); i++) {
			if (posY + modules.get(i).getHeight() - (y + 10) < height && posY > y) {
				modules.get(i).move(x + 10, posY);
				posY += modules.get(i).getHeight() + 10;
				modules.get(i).draw(g);
			} else {
				modules.get(i).move(x + 10, Main.c.getHeight() + 1);
				modules.get(i).initRemoval();
				posY += modules.get(i).getHeight() + 10;
			}
		}
		
		sb.setBounds(x + width, y, 15, height);
		scrollPos = sb.getValue();
	}

	@Override
	public void update() {
		
		if (canClick) {
			if (MouseListener_.mDown) {
				int mX = MouseMotionListener_.mX;
				int mY = MouseMotionListener_.mY;
				for (int i = 0; i < modules.size(); i++) {
					int x = modules.get(i).getX();
					int y = modules.get(i).getY();
					int w = modules.get(i).getW();
					int h = modules.get(i).getH();
					if (mX > x && mX < x + w && mY > y && mY < y + h) {
						Module m = modules.get(i).duplicate();
						m.setParent(parent);
						m.setEditor(parent);
						parent.inHand = m;
					}
				}
				canClick = false;
			}
		} else {
			if (!MouseListener_.mDown) {
				canClick = true;
			}
		}
		
	}

}
