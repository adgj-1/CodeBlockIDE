package main.ide;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import main.MouseListener_;
import main.MouseMotionListener_;
import main.ide.modules.Containable;
import main.ide.modules.Container;
import main.ide.modules.MethodModule;
import main.ide.modules.Module;

public class CodeBlockEditor extends UIComponent implements Container {
	public Module inHand = null;
	public List<Module> modulePool = new ArrayList<Module>();
	ModuleToolbar mtb;
	public CodeBlockEditor(int x, int y, ModuleToolbar mtb) {
		this.x = x;
		this.y = y;
		mtb.setPosition(x, y);
		mtb.parent = this;
		this.mtb = mtb;
	}
	@Override
	public void draw(Graphics g) {
		mtb.draw(g);
		
		for (int i = 0; i < modulePool.size(); i++) {
			modulePool.get(i).draw(g);
		}
		
		if (inHand != null) {
			inHand.draw(g);
		}
	}

	@Override
	public void update() {
		for (int i = modulePool.size() - 1; i >= 0 ; i--) {
			modulePool.get(i).update();
		}
		
		int mX = MouseMotionListener_.mX;
		int mY = MouseMotionListener_.mY;
		boolean mDown = MouseListener_.mDown;
		mtb.update();
		if (inHand != null) {
			inHand.move(mX, mY);
			if (!mDown) {
				if (mX > mtb.x && mX < mtb.x + mtb.width && mY > mtb.y && mY < mtb.y + mtb.height) {
					inHand.initRemoval();
					inHand = null;
				} else {
					boolean success = false;
					for (int i = modulePool.size() - 1; i >= 0 ; i--) {
						boolean s = modulePool.get(i).dropIn(inHand);
						if (s) {
							success = true;
							break;
						}
					}
					if (!success) {
						input(inHand,0);
					}
					inHand = null;
				}
			}
		}
		
	}
	@Override
	public void input(Containable c, int slot) {
		modulePool.add((Module)c);
		((Module)c).setParent(this);
		
	}
	@Override
	public boolean remove(Containable c, int slot) {
		c.initRemoval();
		return modulePool.remove((Module)c);
		
	}
	@Override
	public int getObjectSlot(Containable c) {
		return 0;
	}

	@Override
	public void updateContent() {
		for (Module m : modulePool) {
			if (m instanceof MethodModule) {
				((MethodModule) m).updateContent();
			}
		}
	}
}
