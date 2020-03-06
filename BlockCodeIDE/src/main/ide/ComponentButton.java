package main.ide;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.MouseListener_;
import main.MouseMotionListener_;

public class ComponentButton extends UIComponent {
	int width;
	int height;
	String name;
	boolean canPress;
	Runnable r;
	Font font;
	public ComponentButton(String name, int x, int y, int w, int h, Runnable r) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.name = name;
		this.r = r;
		font = new Font("Calibri", Font.BOLD, 20);
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(x, y, width, height);
		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString(name, x + 10, y + 20);
		
	}

	@Override
	public void update() {
		if (MouseMotionListener_.mX > x && MouseMotionListener_.mX < x + width && MouseMotionListener_.mY > y && MouseMotionListener_.mY < y + height) {
			if (!MouseListener_.mDown) {
				canPress = true;
			}
		} else {
			canPress = false;
		}
		
		if (canPress) {
			if (MouseListener_.mDown) {
				canPress = false;
				runCommand();
			}
		}
	}

	public void runCommand() {
		r.run();
	}
}
