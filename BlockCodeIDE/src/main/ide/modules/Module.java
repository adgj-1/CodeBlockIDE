package main.ide.modules;

import java.awt.Graphics;

import main.MouseListener_;
import main.MouseMotionListener_;
import main.ide.CodeBlockEditor;
import main.ide.UIComponent;

public class Module extends UIComponent implements Containable {
	int x;
	int y;
	int width;
	int height;
	protected boolean canClick = false;
	Container parent;
	CodeBlockEditor editor;
	public void draw(Graphics g) {
		
	}

	public void move(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getW() {
		return width;
	}
	
	public int getH() {
		return getHeight();
	}
	
	public Module duplicate() {
		return new Module();
	}

	public void setParent(Container p) {
		parent = p;
	}
	
	public void setEditor(CodeBlockEditor e) {
		editor = e;
	}
	
	@Override
	public void update() {
		if (canClick) {
			if (MouseListener_.mDown) {
				int mX = MouseMotionListener_.mX;
				int mY = MouseMotionListener_.mY;
				if (mX > x && mX < x + width && mY > y && mY < y + getHeight()) {
					
					if (editor.inHand == null) {
						editor.inHand = this;
						parent.remove(this, parent.getObjectSlot(this));
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
	
	public boolean dropIn(Module m) {
		return false;
	}

	public int getHeight() {
		return height;
	}
	
	public void initRemoval() {
		
	}
}
