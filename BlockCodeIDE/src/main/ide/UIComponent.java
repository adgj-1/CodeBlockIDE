package main.ide;

import java.awt.Graphics;

public abstract class UIComponent {
	public int x;
	public int y;
	public abstract void draw(Graphics g);
	public abstract void update();
	
	public void setPosition(int x_, int y_) {
		x = x_;
		y = y_;
	}
}
