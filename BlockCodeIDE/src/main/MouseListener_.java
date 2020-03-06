package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseListener_ implements MouseListener {
	public static boolean mDown = false;
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		mDown = true;

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mDown = false;

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
