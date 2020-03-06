package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMotionListener_ implements MouseMotionListener {
	public static int mX;
	public static int mY;
	@Override
	public void mouseDragged(MouseEvent e) {
		mX = e.getX();
		mY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mX = e.getX();
		mY = e.getY();

	}

}
