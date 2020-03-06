package main.ide.modules;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import main.Variable;

public class VariableModule extends Module {
	public static final int DEFAULT_WIDTH = 80;
	Variable v;
	Font font;
	FontMetrics metrics;
	String name;
	public VariableModule(Variable var) {
		width = 80;
		height = 30;
		v = var;
		font = new Font("Calibri", Font.BOLD, 20);
		
		name = v.getDisplayName();
	}
	
	public void draw(Graphics g) {
		metrics = g.getFontMetrics(font);
		if (v.getType().equals("boolean")) {
			g.setColor(Color.BLUE);
		} else {
			g.setColor(Color.ORANGE);
		}
		g.fillRoundRect(x, y, width, getHeight(), getHeight()/2, getHeight()/2);
		g.setColor(Color.BLACK);
		g.setFont(font);
		
		g.drawString(name, x + 40 - metrics.stringWidth(name)/2, y + 15 + metrics.getHeight() / 4);
	}
	
	public Module duplicate() {
		return new VariableModule(v);
	}

	public String getCode() {
		return v.getName();
		
	}
	
	public String getType() {
		return v.getType();
	}
}
