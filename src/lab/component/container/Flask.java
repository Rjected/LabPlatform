package lab.component.container;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import lab.component.VerticalGraduation;

public class Flask extends Container {
	
	private static final int OPENING_SIZE = 20;

	public Flask(int width, int height) {
		super(width, height);
		
		setGraduation(new VerticalGraduation(0, 25, 5, 1));
		
		setOffsetX(30);
		setOffsetY(30);
		
	
	}

	@Override
	public void draw(int x, int y, int width, int height, Graphics g) {
		
		List<Point> edge = new ArrayList<Point>();
		edge.add(new Point(x + width, y + height));
		edge.add(new Point(x + (width / 2) + (OPENING_SIZE / 2), y + OPENING_SIZE));
		getGraduation().setEdge(edge);
		
		
		g.setColor(getColor());
		g.fillRect(x, y, width, height);
		
		
		if (!isEmpty()) {
			drawContent(x, y, width, height - OPENING_SIZE, g);
		}
		
		
		int[] polyX = new int[4];
		polyX[0] = x;
		polyX[1] = x + (width / 2) - (OPENING_SIZE / 2);
		polyX[2] = x + (width / 2) - (OPENING_SIZE / 2);
		polyX[3] = x;
		
		int[] polyY = new int[4];
		polyY[0] = y;
		polyY[1] = y;
		polyY[2] = y + OPENING_SIZE;
		polyY[3] = y + height;
		
		g.setColor(Color.white);
		g.fillPolygon(polyX, polyY, 4);
		
		polyX[0] = x + width;
		polyX[1] = x + (width / 2) + (OPENING_SIZE / 2);
		polyX[2] = x + (width / 2) + (OPENING_SIZE / 2);
		polyX[3] = x + width;
		
		polyY[0] = y;
		polyY[1] = y;
		polyY[2] = y + OPENING_SIZE;
		polyY[3] = y + height;
		
		
		g.setColor(Color.white);
		g.fillPolygon(polyX, polyY, 4);
		
		
		
		g.setColor(Color.gray);
		g.drawLine(x + (width / 2) - (OPENING_SIZE / 2), y, x + (width / 2) + (OPENING_SIZE / 2), y);
		
		g.drawLine(x + (width / 2) - (OPENING_SIZE / 2), y, x + (width / 2) - (OPENING_SIZE / 2), y + OPENING_SIZE);
		g.drawLine(x + (width / 2) + (OPENING_SIZE / 2), y, x + (width / 2) + (OPENING_SIZE / 2), y + OPENING_SIZE);
		
		g.drawLine(x + (width / 2) + (OPENING_SIZE / 2), y + OPENING_SIZE, x + width, y + height);
		
		g.drawLine(x + (width / 2) - (OPENING_SIZE / 2), y + OPENING_SIZE, x, y + height);
		
		g.drawLine(x, y + height, x + width, y + height);
		
		
		
		if (getGraduation() != null) {
			getGraduation().draw(g, width, height);
		}
		
		if (canShowValue()) {
			g.setColor(Color.black);
			g.drawString((float) getValue() + "mL", x, y + height + 20);
		}
		

		drawLabel(x, y, width, height, g);
		
		
	}


	
}
