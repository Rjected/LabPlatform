package lab.component.container;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import lab.component.VerticalGraduation;

public class Beaker extends Container {

	public Beaker(int width, int height) {
		super(width, height);
		
		setOffsetX(40);
		setOffsetY(40);
		
		setGraduation(new VerticalGraduation(0, 500, 250, 50));
		getGraduation().setSuffix("mL");
		
	}

	@Override
	public void draw(int x, int y, int width, int height, Graphics g) {
		
		List<Point> edge = new ArrayList<Point>();
		edge.add(new Point(x + width, y + height));
		edge.add(new Point(x + width, y + 40));
		getGraduation().setEdge(edge);
		
		g.setColor(getColor());
		g.fillRoundRect(x, y, width, height, 20, 20);
		
		if (!isEmpty()) {
			drawContent(x, y, width, height - 40, g);
		}
		
		g.setColor(Color.white);
		for (int i = 0; i < 20; i++) {
			g.drawRoundRect(x, y, width, height, i, i);	
		}
		
		g.setColor(Color.gray);
		g.drawRoundRect(x, y, width, height, 20, 20);
		
		g.setColor(Color.white);
		g.fillRect(x, y, width + 1, 20);
		
		getGraduation().draw(g, width, height);
		
		if (canShowValue()) {
			g.setColor(Color.black);
			g.drawString((int) getValue() + "mL", x, y + height + 20);
		}
		

		drawLabel(x, y, width, height, g);
		
	}


}
