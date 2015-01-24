package com.weebly.marlookup.main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class MainWindow extends JFrame implements ActionListener {
	static String VERSION = "5.0DEV";
	public MainWindow() {
		
		setSize(400,400);
		setTitle("MarLookUp v"+VERSION);
		setVisible(true);
		
		this.setBackground(Color.getHSBColor(26, 68, 66));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
