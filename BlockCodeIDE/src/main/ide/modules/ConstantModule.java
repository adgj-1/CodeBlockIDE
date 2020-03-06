package main.ide.modules;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.TextField;

import main.Main;
import main.Variable;

public class ConstantModule extends VariableModule {
//!!!
	TextField textbox;
	boolean tbRemoved;
	public ConstantModule(Variable var) {
		super(var);
		textbox = new TextField();
		Main.c.add(textbox);
		tbRemoved = false;
	}
	
	@Override
	public void draw(Graphics g) {
		metrics = g.getFontMetrics(font);
		int strWidth = metrics.stringWidth(name);
		int tempWidth = DEFAULT_WIDTH;
		if (strWidth > (tempWidth - 45)) {
			tempWidth = 45 + strWidth;
		}
		width = tempWidth;
		if (v.getType().equals("boolean")) {
			g.setColor(Color.BLUE);
		} else {
			g.setColor(Color.ORANGE);
		}
		g.fillRoundRect(x, y, width, getHeight(), getHeight()/2, getHeight()/2);
		g.setColor(Color.BLACK);
		g.setFont(font);
		
		g.drawString(name, x + 5, y + 15 + metrics.getHeight() / 4);
		if (tbRemoved) {
			Main.c.add(textbox);
			textbox.setVisible(true);
			tbRemoved = false;
		}
		textbox.setBounds(x + width - 35, y + 5, 30, 20);
	}
	
	public void update() {
		super.update();
		
		
		if (!textbox.getText().equals(name)) {
			name = textbox.getText();
			parent.updateContent();
		}
	}
	
	public void initRemoval() {
		Main.c.remove(textbox);
		tbRemoved = true;
	}
	@Override
	public VariableModule duplicate() {
		return new ConstantModule(v);
	}
	
	@Override
	public String getCode() {
		return name;
	}

}
