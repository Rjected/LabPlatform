package lab.component.container;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import lab.component.VerticalGraduation;

public class Bulb extends Container {
	
	private static final int VERTICES = 30;
	private static final int OPENING_WIDTH = 5;
	private static final int OPENING_HEIGHT = 10;
	
	public Bulb(int size) {
		super(size, size);
		
		setOffsetX(30);
		setOffsetY(30);
		
		setGraduation(new VerticalGraduation(0, 100, 50, 50));
		
	}

	@Override
	public void draw(int x, int y, int width, int height, Graphics g) {
		
		int[] polyX1 = new int[(VERTICES / 2) + 5];
		int[] polyY1 = new int[(VERTICES / 2) + 5];
		int[] polyX2 = new int[(VERTICES / 2) + 5];
		int[] polyY2 = new int[(VERTICES / 2) + 5];
		
		List<Point> edge = new ArrayList<Point>();
		edge.add(new Point(x + width / 2, y + height));
		
		width = Math.min(width, height);
		height = Math.min(width, height);
		
		Point p;
		double angle;
		
		for (int i = 1; i <= VERTICES / 2; i++) {
			
			p = new Point(0, (height / 2) - OPENING_HEIGHT);
			
			angle = i / (VERTICES / 2.0) * 180.0;
			
			p = rotate(p, angle);
			
			if (p.x + x + (width / 2) < x + (width / 2) - (OPENING_WIDTH / 2))
				polyX1[i + 4] = p.x + x + (width / 2);
			else
				polyX1[i + 4] = x + (width / 2) - (OPENING_WIDTH / 2);
			
			polyY1[i + 4] = p.y + y + (height / 2) + OPENING_HEIGHT;
			
			
			
			p = new Point(0, (height / 2) - OPENING_HEIGHT);
			
			angle = i / (VERTICES / 2.0) * 180.0;
			
			p = rotate(p, -angle);
			
			if (p.x + x + (width / 2) > x + (width / 2) + (OPENING_WIDTH / 2))
				polyX2[i + 4] = p.x + x + (width / 2);
			else
				polyX2[i + 4] = x + (width / 2) + (OPENING_WIDTH / 2);
			
			polyY2[i + 4] = p.y + y + (height / 2) + OPENING_HEIGHT;
			

			
			edge.add(new Point(polyX2[i + 4], polyY2[i + 4]));
			
		}
		
		polyX1[0] = x + (width / 2) - (OPENING_WIDTH / 2);
		polyY1[0] = y + OPENING_HEIGHT;
		
		polyX1[1] = x + (width / 2) - (OPENING_WIDTH / 2);
		polyY1[1] = y;
		
		polyX1[2] = x;
		polyY1[2] = y;
		
		polyX1[3] = x;
		polyY1[3] = y + height;
		
		polyX1[4] = x + width / 2;
		polyY1[4] = y + height;
		
		
		polyX2[0] = x + (width / 2) + (OPENING_WIDTH / 2);
		polyY2[0] = y + OPENING_HEIGHT;
		
		polyX2[1] = x + (width / 2) + (OPENING_WIDTH / 2);
		polyY2[1] = y;
		
		polyX2[2] = x + width;
		polyY2[2] = y;
		
		polyX2[3] = x + width;
		polyY2[3] = y + height;
		
		polyX2[4] = x + width / 2;
		polyY2[4] = y + height;
		
		g.setColor(getColor());
		g.fillRect(x, y, width, height);
		

		getGraduation().setEdge(edge);
		
		if (!isEmpty()) {
			drawContent(x, y, width, height, g);
		}
		
		g.setColor(Color.white);
		
		g.fillPolygon(polyX1, polyY1, polyX1.length);
		g.fillPolygon(polyX2, polyY2, polyX2.length);
		
		g.setColor(Color.gray);
		g.drawOval(x + OPENING_HEIGHT, y + OPENING_HEIGHT * 2, width - OPENING_HEIGHT * 2, height - OPENING_HEIGHT * 2);
		
		g.setColor(Color.gray);
		g.drawRect(x + (width / 2) - (OPENING_WIDTH / 2), y, OPENING_WIDTH, OPENING_HEIGHT * 2);
		
		drawLabel(x, y, width, height, g);
		
		//g.drawRect(x, y, width, height);
		
		//getGraduation().draw(g);
		
	}
	
	public static Point rotate(Point p, double angle)
	{
		angle = angle / 180.0 * Math.PI;
		double s = Math.sin(angle);
		double c = Math.cos(angle);
		return new Point((int) (p.x * c - p.y * s), (int) (p.x * s + p.y * c));
	}


}
