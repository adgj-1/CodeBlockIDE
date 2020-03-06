package main.ide.modules;

import java.awt.Color;
import java.awt.Graphics;

import main.Predicate;

public class PredicateModule extends VariableModule implements Container {

	Containable arg1;
	Containable arg2;
	public PredicateModule(Predicate p) {
		super(p);
	}

	@Override
	public void draw(Graphics g) {
		metrics = g.getFontMetrics(font);
		
		int widthTemp = 100;
		int heightTemp = 30;
		if (arg1 != null) {
			widthTemp = 40 + metrics.stringWidth(name) + ((Module) arg1).getW() * 2;
			heightTemp = ((Module) arg1).getH() + 10; 
		}
		if (arg2 != null) {
			int widthTemp2 = 40 + metrics.stringWidth(name) + ((Module) arg2).getW() * 2;
			int heightTemp2 = ((Module) arg2).getH() + 10;
			widthTemp = Math.max(widthTemp, widthTemp2);
			heightTemp = Math.max(heightTemp, heightTemp2);
		}
		
		width = widthTemp;
		height = heightTemp;
		
		g.setColor(Color.GREEN);
		g.fillRoundRect(x, y, width, getHeight(), getHeight()/2, getHeight()/2);
		g.setColor(Color.LIGHT_GRAY);
		g.drawRoundRect(x, y, width, getHeight(), getHeight()/2, getHeight()/2);
		g.setColor(Color.BLACK);
		g.setFont(font);
		
		if (arg1 != null) {
			((Module) arg1).move(x + 10, y + (height - ((Module) arg1).height)/2);
			((Module) arg1).draw(g);
		} else {
			g.setColor(Color.WHITE);
			g.fillRect(x + 10, y + (height - 20) / 2, 20, 20);
		}
		if (arg2 != null) {
			((Module) arg2).move(x + width - ((Module) arg2).getW() - 10, y + (height - ((Module) arg2).height)/2);
			((Module) arg2).draw(g);
		} else {
			g.setColor(Color.WHITE);
			g.fillRect(x + width - 30, y + (height - 20) / 2, 20, 20);
		}
		g.drawString(name, x + width/2 - metrics.stringWidth(name)/2, y + height/2 + metrics.getHeight() / 4);
		
		
	}
	
	@Override
	public void update() {
		if (arg1 != null) {
			((Module) arg1).update();
		}
		if (arg2 != null) {
			((Module) arg2).update();
		}
		super.update();
	}
	
	@Override
	public void input(Containable c, int slot) {
		if (slot == 0) {
			arg1 = c;
		} else {
			arg2 = c;
		}
		((Module) c).setParent(this);
	}

	@Override
	public boolean remove(Containable c, int slot) {
		c.initRemoval();
		if (slot == 0) {
			if (c.equals(arg1)) {
				arg1 = null;
				return true;
			}
		} else {
			if (c.equals(arg2)) {
				arg2 = null;
				return true;
			}
		}
		return false;
	}

	@Override
	public int getObjectSlot(Containable c) {
		if (c.equals(arg2)) {
			return 1;
		}
		return 0;
	}
	
	@Override
	public boolean dropIn(Module m) {
		if (arg1 != null) {
			boolean done = ((Module)arg1).dropIn(m);
			if (done) {
				return true;
			}
		}
		if (arg2 != null) {
			boolean done = ((Module)arg2).dropIn(m);
			if (done) {
				return true;
			}
		}
		
		if (m.x > x && m.x < x + width/2 && m.y > y && m.y < y + height) {
			if (((VariableModule)m).getType().equals(((Predicate) v).getTypeA())) {
				input(m, 0);
				return true;
			}
		}
		if (m.x > x + width/2 && m.x < x + width && m.y > y && m.y < y + height) {
			if (((VariableModule)m).getType().equals(((Predicate) v).getTypeB())) {
				input(m, 1);
				return true;
			}
		}
		return false;
	}

	public Module duplicate() {
		return new PredicateModule((Predicate) v);
	}
	
	public String getCode() {
		String res = "(";
		if (arg1 != null) {
			res += ((VariableModule) arg1).getCode()+ " ";
		}
		res += name;
		if (arg2 != null) {
			res += " " + ((VariableModule) arg2).getCode();
		}
		res += ")";
		return res;
	}
	
	@Override
	public void initRemoval() {
		if (arg1 != null) {
			arg1.initRemoval();
		}
		
		if (arg2 != null) {
			arg2.initRemoval();
		}
	}
	
	@Override
	public void updateContent() {
		parent.updateContent();
	}
}
