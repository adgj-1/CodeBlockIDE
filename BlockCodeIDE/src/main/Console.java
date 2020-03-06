package main;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class Console extends JPanel {

	JScrollPane pane;
	JTextArea com;
	JButton clear;
	PrintStream printstream;
	ByteArrayOutputStream baos;
	int height;
	Font font;
	public Console() {
		height = 800;
		setLayout(new BorderLayout());
		font = new Font("Serif", Font.PLAIN, 16);
		com = new JTextArea("",30,30);
		//com.setEditable(false);
		com.setWrapStyleWord(true);
		pane = new JScrollPane(com, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		com.setFont(font);
		com.setLineWrap(false);
		com.setAutoscrolls(true);
		
		clear = new JButton("Clear");
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clear();
			}});
		
		add(pane, BorderLayout.CENTER);
		add(clear, BorderLayout.PAGE_END);
		
		
		baos = new ByteArrayOutputStream();
		printstream = new PrintStream(baos);
		System.setOut(printstream);
	}
	
	public void updateStream() {
		if (!com.getText().equals(baos.toString())) {
			com.setText(baos.toString());
			pane.getVerticalScrollBar().setValue(pane.getVerticalScrollBar().getMaximum());
		}
	}
	
	public void clear() {
		baos.reset();
	}
}
