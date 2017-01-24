package lab.component.sensor;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import lab.LabFrame;
import lab.component.GraduatedComponent;
import lab.component.VerticalGraduation;

public class Thermometer extends GraduatedComponent {
	
	public Thermometer(int height) {
		super(10, height);
		
		setGraduation(new VerticalGraduation(-30, 100, 10, 2));
		getGraduation().setSuffix("C");
		
		setOffsetX(30);
		setOffsetY(30);
		
	}
	
	@Override
	public void draw(int x, int y, int width, int height, Graphics g) {
		List<Point> edge = new ArrayList<Point>();
		edge.add(new Point(x + width, y + height));
		edge.add(new Point(x + width, y));
		getGraduation().setEdge(edge);
		
		g.setColor(Color.red);
		
		int ow = (int) (20 / LabFrame.inst.getWidthScalingRatio());
		int oh = (int) (20 / LabFrame.inst.getHeightScalingRatio());
		
		g.fillOval(x + (width / 2) - ow / 2, y + height, ow, oh);
		
		g.setColor(Color.black);
		
		g.drawOval(x + (width / 2) - ow / 2, y + height, ow, oh);
		
		
		g.setColor(Color.lightGray);
		g.fillRect(x, y, width, height);
		
		g.setColor(Color.gray);
		
		g.drawLine(x, y, x, y + height);
		g.drawLine(x + width, y, x + width, y + height);
		g.drawLine(x, y + height, x + width, y + height);
		g.drawLine(x, y, x + width, y);
		
		g.setColor(Color.red);
		double range = getGraduation().getEnd() - getGraduation().getStart();
		g.fillRect(x, getGraduation().getBottomTick() - (int) ((getValue() - getGraduation().getStart()) / range * height), width, (int) ((getValue() - getGraduation().getStart()) / range * height));			
		
		
		if (getGraduation() != null) {
			getGraduation().draw(g, width, height);
		}
		
		
		
		if (canShowValue()) {
			FontMetrics metrics = g.getFontMetrics();
			
			g.setColor(Color.black);
			g.drawString((int) getValue() + "C", x - (metrics.stringWidth((int) getValue() + "C") / 4), y + height + 30);
		}
		
		
		
	}

	
	
}
