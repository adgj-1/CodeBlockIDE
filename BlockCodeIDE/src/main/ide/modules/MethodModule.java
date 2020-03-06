package main.ide.modules;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import main.Method;
import main.MethodHandler;

public class MethodModule extends Module implements Container {
	Method m;
	Font font;
	FontMetrics metrics;
	String name;
	int internalHeight = 20;
	
	Containable internalList;
	
	public MethodModule(Method fn) {
		m = fn;
		font = new Font("Calibri", Font.BOLD, 20);
		name = fn.getName();
		width = 100;
		
	}
	
	public void draw(Graphics g) {
		metrics = g.getFontMetrics(font);
		g.setColor(Color.YELLOW);
		g.fillRoundRect(x, y, 100, 30, 10, 10);
		g.fillRoundRect(x, y, 10, 40 + internalHeight, 10, 10);
		//g.fillRoundRect(x, y + internalHeight + 30, 100, 10, 5, 5);
		g.setColor(Color.BLACK);
		g.setFont(font);
		
		g.drawString(name, x + 40 - metrics.stringWidth(name)/2, y + 15 + metrics.getHeight() / 4);
		height = internalHeight + 40;
		
		if (internalList != null) {
			((Module)internalList).move(x + 10, y + 30);
			internalHeight = ((Module)internalList).getHeight();
			height = internalHeight + 30;
			((Module)internalList).draw(g);
		} else {
			internalHeight = 20;
			height = internalHeight + 30;
		}
		
	}
	
	public Module duplicate() {
		return new MethodModule(m);
	}

	@Override
	public void input(Containable c, int slot) {
		internalList = c;
		((Module)c).setParent(this);
		
	}

	@Override
	public boolean remove(Containable c, int slot) {
		if (internalList.equals(c)) {
			internalList = null;
			return true;
		}
		return false;
	}

	@Override
	public int getObjectSlot(Containable c) {
		if (internalList != null && internalList.equals(c)) {
			return 0;
		}
		return 1;
	}
	
	@Override
	public boolean dropIn(Module m) {
		if (internalList != null) {
			boolean done = ((Module)internalList).dropIn(m);
			if (done) {
				updateContent();
				return true;
			}
		}
		
		if (!(m instanceof FunctionModule)) {
			return false;
		}
		
		if (m.x > x && m.x < x + width && m.y > y + 30 && m.y < y + 60) {
			input(m, 0);
			updateContent();
			return true;
		}
		if (m.x > x && m.x < x + width && m.y > y + getHeight() && m.y < y + getHeight() + 30) {
			input(m, 1);
			updateContent();
			return true;
		}
		return false;
	}
	@Override
	public void update() {
		
		if (internalList != null) {
			((Module)internalList).update();
		}
		
		super.update();
		
	}
	
	//WIP
	public String getCode() {
		return m.getMethod();
	}
	
	public void updateContent() {
		if (internalList != null) {
			m.setContent(((FunctionModule) internalList).getCode());
		}
		MethodHandler.setMethodContent(m.getName(), m.getContent());
	}
	
}
