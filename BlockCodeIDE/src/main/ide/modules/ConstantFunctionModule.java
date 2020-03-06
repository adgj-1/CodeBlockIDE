package main.ide.modules;

import java.awt.Color;
import java.awt.Graphics;

import main.Method;

public class ConstantFunctionModule extends FunctionModule {
	String inner;
	int strWidth = 0;
	public ConstantFunctionModule(Method fn, String inner) {
		super(fn);
		this.inner = inner;
	}

	public void draw(Graphics g) {
		metrics = g.getFontMetrics(font);
		g.setColor(Color.YELLOW);
		g.fillRoundRect(x, y, width, headerHeight, 10, 10);
		g.setColor(Color.BLACK);
		g.setFont(font);
		strWidth = metrics.stringWidth(name);
		g.drawString(name, x + 10, y + 15 + metrics.getHeight() / 4);
		
		height = headerHeight;
		headerHeight = DEFAULT_HEADER_HEIGHT;
		int tempWidth = 60;
		for (int i = 0; i < params.length; i++) {
			if (params[i] != null) {
				((Module)params[i]).move(x + strWidth + 15 + i * 80, y + 5);
				((Module) params[i]).draw(g);
				headerHeight = ((Module)params[i]).getHeight() + 10;
				height = headerHeight;
				tempWidth += ((Module) params[i]).getW();
			} else {
				g.setColor(Color.WHITE);
				g.fillRoundRect(x + strWidth + 15 + i * 80, y + 5, 80, 20, 5, 5);
				tempWidth += 80;
			}
		}
		width = tempWidth;
		if (restList != null) {
			((Module)restList).move(x, y + getHeight());
			height = getHeight() + ((Module)restList).height;
			((Module)restList).draw(g);
		}
		
		
	}
	
	public Module duplicate() {
		return new ConstantFunctionModule(f, inner);
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
		
		int pos = x + (strWidth + 15);
		for (int i = 0; i < params.length; i++) {
			if (params[i] != null && params[i] instanceof Container) {
				boolean done = ((Module)(params[i])).dropIn(m);
				if (done) {
					return true;
				}
			}
			int p_w = 80;
			if (params[i] != null) {
				p_w = ((Module) params[i]).getW();
			}
			if (m.x > pos  && m.x < pos + p_w && m.y > y && m.y < y + headerHeight) {
				if (((VariableModule)m).getType().equals(f.getParameters()[i].getType()) || f.getParameters()[i].getType().equals("var")) {
					input(m, i + 2);
					return true;
				} else {
					//System.out.print(((VariableModule)m).getType() + ", " + f.getParameters()[i].getType()); //!!!
				}
				
			}
			pos += p_w;
		}
	
		
		if (!(m instanceof FunctionModule)) {
			return false;
		}
		
		
		
		if (m.x > x && m.x < x + width && m.y > y + getHeight() && m.y < y + getHeight() + 30) {
			input(m, 1);
			return true;
		}
		return false;
	}
	
	@Override
	public String getCode() {
		String res = inner;
		for (int i = 0; i < params.length; i++) {
			if (params[i] != null) {
				String p_code = ((VariableModule) params[i]).getCode();
				res = res.replaceAll(("arg" + i), p_code) + ";";
			}
		}
		return res;
	}
}
