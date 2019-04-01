package dev.game.display;

import java.awt.*;

import javax.swing.*;

public class Screen
{
	public String title = "Scoundrel";
	public Canvas canvas;
	public JFrame frame;

	public Screen(String title, int width, int height)
	{
		
		frame = new JFrame(title);
		frame.setSize(width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	public Canvas getCanvas()
	{
		return canvas;
	}
	
	public JFrame getFrame()
	{
		return frame;
	}
}
