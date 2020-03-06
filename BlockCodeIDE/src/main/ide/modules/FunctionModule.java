package main.ide.modules;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import main.Method;

public class FunctionModule extends Module implements Container {
	static final int DEFAULT_HEADER_HEIGHT = 30;
	static final int DEFAULT_INTERNAL_HEIGHT = 20;
	static final int DEFAULT_WIDTH = 100;
	Method f;
	Font font;
	FontMetrics metrics;
	String name;
	int internalHeight = 20;
	int headerHeight = 30;
	Containable internalList;
	Containable restList;
	Containable[] params;
	public FunctionModule(Method fn) {
		f = fn;
		font = new Font("Calibri", Font.BOLD, 20);
		name = fn.getName();
		params = new Containable[fn.getParameters().length];//placeholder for now !!!
		width = 60 + params.length * 80;
		
	}
	
	public void draw(Graphics g) {
		metrics = g.getFontMetrics(font);
		g.setColor(Color.YELLOW);
		g.fillRoundRect(x, y, width, headerHeight, 10, 10);
		g.fillRoundRect(x, y, 10, headerHeight + 10 + internalHeight, 10, 10);
		g.fillRoundRect(x, y + internalHeight + headerHeight, width, 10, 5, 5);
		g.setColor(Color.BLACK);
		g.setFont(font);
		
		g.drawString(name, x + 10 - metrics.stringWidth(name)/2, y + 15 + metrics.getHeight() / 4);
		height = internalHeight + headerHeight + 10;
		
		if (internalList != null) {
			((Module)internalList).move(x + 10, y + headerHeight);
			internalHeight = ((Module)internalList).getHeight();
			height = internalHeight + headerHeight + 10;
			((Module)internalList).draw(g);
		} else {
			internalHeight = 20;
			height = internalHeight + headerHeight + 10;
		}
		headerHeight = DEFAULT_HEADER_HEIGHT;
		width = 140;
		for (int i = 0; i < params.length; i++) {
			if (params[i] != null) {
				((Module)params[i]).move(x + 40 + i * 80, y + 5);
				((Module) params[i]).draw(g);
				headerHeight = ((Module)params[i]).getHeight() + 10;
				height = internalHeight + headerHeight + 10;
				width = 60 + ((Module) params[i]).getW();
			} else {
				g.setColor(Color.WHITE);
				g.fillRoundRect(x + 40 + i * 80, y + 5, 80, 20, 5, 5);
			}
		}
		
		if (restList != null) {
			((Module)restList).move(x, y + getHeight());
			height = getHeight() + ((Module)restList).height;
			((Module)restList).draw(g);
		}
		
		
	}
	
	public Module duplicate() {
		return new FunctionModule(f);
	}

	@Override
	public void input(Containable c, int slot) {
		switch (slot) {
		case 0: {
			internalList = c;
			break;
		}
		
		case 1: {
			restList = c;
			break;
		}
		
		default: {
			if (slot >= 2 && slot < params.length + 2) {
				params[slot - 2] = c;
			} else {
				internalList = c;
			}
			break;
		}
		}
		((Module)c).setParent(this);
		
	}

	@Override
	public boolean remove(Containable c, int slot) {
		c.initRemoval();
		switch (slot) {
		case 0: {
			if (internalList.equals(c)) {
				internalList = null;
				return true;
			}
		}
		
		case 1: {
			if (restList.equals(c)) {
				restList = null;
				return true;
			}
		}
		
		default: {
			if (slot - 2 < params.length) {
				if (params[slot-2].equals(c)) {
					params[slot-2] = null;
					return true;
				}
			}
			
			if (internalList.equals(c)) {
				internalList = null;
				return true;
			}
		}
		}
		return false;
	}

	@Override
	public int getObjectSlot(Containable c) {
		if (internalList != null && internalList.equals(c)) {
			return 0;
		}
		for (int slot = 2; slot < params.length + 2; slot++) {
			
			if (params[slot-2] != null && params[slot-2].equals(c)) {
				return slot;
			}
		}
		return 1;
	}
	
	@Override
	public boolean dropIn(Module m) {
		if (internalList != null) {
			boolean done = ((Module)internalList).dropIn(m);
			if (done) {
				return true;
			}
		}
		if (restList != null) {
			boolean done = ((Module)restList).dropIn(m);
			if (done) {
				return true;
			}
		}
		
		for (int i = 0; i < params.length; i++) {
			if (params[i] != null && params[i] instanceof Container) {
				boolean done = ((Module)(params[i])).dropIn(m);
				if (done) {
					return true;
				}
			}
			if (m.x > x + 40 + i * 80 && m.x < x + 120 + i * 80 && m.y > y && m.y < y + headerHeight) {
				if (m instanceof VariableModule && (((VariableModule)m).getType().equals(f.getParameters()[i].getType()) || f.getParameters()[i].getType().equals("var"))) {
					input(m, i + 2);
					return true;
				} else {
//					System.out.print(((VariableModule)m).getType() + ", " + f.getParameters()[i].getType()); //!!!
				}
			}
		}
	
		
		if (!(m instanceof FunctionModule)) {
			return false;
		}
		
		if (m.x > x && m.x < x + width && m.y > y + 30 && m.y < y + 60) {
			input(m, 0);
			return true;
		}
		
		
		if (m.x > x && m.x < x + width && m.y > y + getHeight() && m.y < y + getHeight() + 30) {
			input(m, 1);
			return true;
		}
		return false;
	}
	@Override
	public void update() {
		
		if (internalList != null) {
			((Module)internalList).update();
		}
		if (restList != null) {
			((Module)restList).update();
		}
		for (Containable p : params) {
			if (p != null) {
				((Module) p).update();
			}
		}
		super.update();
		
	}
	
	public String getCode() {
		if (internalList != null) {
			f.setContent(((FunctionModule) internalList).getCode());
		} else {
			f.setContent("\r\n");
		}
		String res = f.getFunctionalMethod(paramToCode());
		if (restList != null) {
			res += ((FunctionModule) restList).getCode();
		}
		return res;
	}
	
	public void initParams() {
		//!!!
	}
	
	public String paramToCode() {
		String res = "";
		for (Containable p : params) {
			if (p != null) {
				res = res.concat(", " + ((VariableModule) p).getCode());
			}
		}
		res = res.replaceFirst(", ", "");
		return res;
	}
	
	@Override
	public void initRemoval() {
		if (internalList != null) {
			internalList.initRemoval();
		}
		if (restList != null) {
			restList.initRemoval();
		}
		for (Containable c : params) {
			if (c != null) {
				c.initRemoval();
			}
		}
	}

	@Override
	public void updateContent() {
		parent.updateContent();
	}
}
