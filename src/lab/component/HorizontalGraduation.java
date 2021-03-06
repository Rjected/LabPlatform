package lab.component;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.List;

import lab.LabFrame;

public class HorizontalGraduation extends Graduation {

	public HorizontalGraduation(double start, double end, double lineIntervals, double subLineIntervals) {
		super(start, end, lineIntervals, subLineIntervals);
	}
	
	public HorizontalGraduation(double end, double lineIntervals, double subLineIntervals) {
		super(0, end, lineIntervals, subLineIntervals);
	}
	
	public HorizontalGraduation(Graduation g) {
		super(g);
	}

	@Override
	public void draw(int x, int y, int w, int h, Graphics g) {
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, this.fontSize));
		FontMetrics metrics = g.getFontMetrics();
		float textHeight = metrics.getLineMetrics("0", g).getHeight();
		Point left, right, p;
		double width, range;
		
		left = edge.get(0);
		right = edge.get(edge.size() - 1);
		
		width = right.x - left.x; // top will have a lower y value than bottom (confusing)
		range = end - start;
		
		
		g.setColor(color);
		
		float i;
		
		for (double j = 0; j <= range; j += subLineIntervals) {
			
			i = (float) j; // cast to float because of inaccuracies in adding doubles
			
			p = findLinePosition(edge, (int) ((i / range * width) + left.x));
			
			p.x += offset;
			if (i % lineIntervals == 0) {
				g.drawLine(p.x, p.y, p.x, (int) (p.y - ((double) lineLength * LabFrame.inst.getHeightScalingRatio())));
				
				if (showLabels) {
					String s = Integer.toString((int) (-range + i + end)) + (i == range ? suffix : "");
					g.drawString(s, p.x - metrics.stringWidth(s) / 2, (int) (p.y + (textHeight)) + textOffset);
				}
				
			} else {
				g.drawLine(p.x, p.y, p.x, (int) (p.y - ((double) subLineLength * LabFrame.inst.getHeightScalingRatio())));
			}
			
		}
		
	}

	@Override
	public int getBottomTick() {
		return edge.get(0).x + offset;
	}

	public static Point findLinePosition(List<Point> edge, int x) {
		Point p1 = null, p2 = null;
		
		
		for (int i = 1; i < edge.size(); i++) {
			
			p1 = edge.get(i);
			p2 = edge.get(i - 1);
			
			if ((x <= p1.getX() && x >= p2.getX()) || (x >= p1.getX() && x <= p2.getX())) {
				break;
			}
			
		}
		
		// x = my + b
		// x - my = b
		
		double m, b;
		
		m = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
		b = p1.getY() - m * p1.getX();
		
		return new Point(x, (int) (x * m + b));
		
	}

}
