package test;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import lab.component.LabComponent;
import lab.component.UserComponentResizing;

public class RectComponent extends LabComponent {
	
	private final UserComponentResizing resize = new UserComponentResizing(this);
	
	public RectComponent(int width, int height) {
		super(width, height);
		
	}
	
	@Override
	public void draw(int x, int y, int width, int height, Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.black);
		g.drawRect(x, y, width, height);
		
		resize.check(x, y, width, height);

	}

	@Override
	public void drawInputs(int x, int y, int width, int height, JPanel panel) {
		resize.initializeMouseListeners(panel);
	}

	
}
