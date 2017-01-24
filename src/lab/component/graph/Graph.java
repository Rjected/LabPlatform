package lab.component.graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import lab.Vector2;
import lab.component.HorizontalGraduation;
import lab.component.LabComponent;
import lab.component.VerticalGraduation;

public class Graph extends LabComponent implements MouseListener, MouseMotionListener {
	
	private final String title;
	private final String xLabel;
	private final String yLabel;
	private final VerticalGraduation vGraduation;
	private final HorizontalGraduation hGraduation;
	private final Map<String,DataSet> data = new HashMap<String,DataSet>();
	private Point mouseCoord = null;
	private boolean drawXLines = true;
	private boolean drawYLines = true;
	
	public Graph(int width, int height, String title, String xLabel, String yLabel, VerticalGraduation vGraduation, HorizontalGraduation hGraduation) {
		super(width, height);
		this.vGraduation = vGraduation;
		this.hGraduation = hGraduation;
		this.title = title;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		
		vGraduation.setTickLength(4);
		vGraduation.setSubTickLength(2);
		hGraduation.setTickLength(2);
		hGraduation.setSubTickLength(2);
		
		vGraduation.setFontSize(9);
		hGraduation.setFontSize(9);
		
		vGraduation.setTextOffset(-20);
		hGraduation.setTextOffset(5);
		
		setOffsetX(30);
		setOffsetY(30);
	}
	
	public void addDataSet(DataSet set) {
		data.put(set.getName(), set);
	}
	
	public void removeDataSet(String name) {
		data.remove(name);
	}
	
	public Collection<DataSet> getDataSets() {
		return data.values();
	}
	
	public VerticalGraduation getvGraduation() {
		return vGraduation;
	}

	public HorizontalGraduation gethGraduation() {
		return hGraduation;
	}
	
	public String getTitle() {
		return title;
	}

	public String getxLabel() {
		return xLabel;
	}

	public String getyLabel() {
		return yLabel;
	}
	
	public boolean canDrawXLines() {
		return drawXLines;
	}

	public void setDrawXLines(boolean drawXLines) {
		this.drawXLines = drawXLines;
	}

	public boolean canDrawYLines() {
		return drawYLines;
	}

	public void setDrawYLines(boolean drawYLines) {
		this.drawYLines = drawYLines;
	}

	@Override
	public void draw(int x, int y, int width, int height, Graphics g) {
		vGraduation.setEdge(Arrays.asList(new Point(x, y + height), new Point(x, y)));
		hGraduation.setEdge(Arrays.asList(new Point(x, y + height + 2), new Point(x + width, y + height + 2)));
		
		double vRange = vGraduation.getEnd() - vGraduation.getStart();
		double hRange = hGraduation.getEnd() - hGraduation.getStart();
	
		g.setColor(Color.lightGray);
		
		Point p;
		
		if (drawXLines) {
			
			for (double j = 0; (float) j <= vRange; j += vGraduation.getSubLineIntervals()) {
				
				p = VerticalGraduation.findLinePosition(vGraduation.getEdge(), vGraduation.getBottomTick() - (int) (j / vRange * height));
				g.drawLine(p.x, p.y, p.x + width, p.y);
				
			}
		}
		
		if (drawYLines) {
			
			for (double j = 0; (float) j <= hRange; j += hGraduation.getSubLineIntervals()) {
					
				p = HorizontalGraduation.findLinePosition(hGraduation.getEdge(), hGraduation.getBottomTick() + (int) (j / hRange * width));
				g.drawLine(p.x, p.y, p.x, p.y - height);
					
			}
		}
		
		
		g.setColor(Color.black);
		g.drawLine(x, y + height, x + width, y + height);
		g.drawLine(x, y, x , y + height);
		
		g.setColor(Color.gray);
		
		if (0 > hGraduation.getStart() && 0 < hGraduation.getEnd()) {
			p = HorizontalGraduation.findLinePosition(hGraduation.getEdge(), hGraduation.getBottomTick() + (int) ((double) (hRange - hGraduation.getEnd()) / hRange * width));
			g.drawLine(p.x, p.y, p.x, p.y - height);
		}
		
		if (0 > vGraduation.getStart() && 0 < vGraduation.getEnd()) {
			p = VerticalGraduation.findLinePosition(vGraduation.getEdge(), vGraduation.getBottomTick() - (int) ((double) (vRange - vGraduation.getEnd()) / vRange * height));
			g.drawLine(p.x, p.y, p.x + width, p.y);
		}
		
		vGraduation.draw(g, width, height);
		hGraduation.draw(g, width, height);
		
		FontMetrics metrics = g.getFontMetrics();
		
		double dx = 0, dy = 0, pdx = 0, pdy = 0;
		Vector2 c;
		boolean first;
		
		for (DataSet dataSet : data.values()) {
			
			g.setColor(dataSet.getColor());
			
			first = true;
			
			for (int i = 0; i < dataSet.getPoints().size(); i++) {
				
				c = dataSet.getPoints().get(i);
				
				dx = (double) (-c.getX() + hGraduation.getStart()) / -hRange * width + x;
				dy = (double) (-c.getY() + vGraduation.getStart()) / vRange * height + y + height;
				
				
			
				if (dataSet.isConnectPoints()) {
					
					boolean prevYOut = pdy < y || pdy > y + height;
					boolean currentYOut = dy < y || dy > y + height;
					boolean prevXOut = pdx < x || pdx > x + width;
					boolean currentXOut = dx < x || dx > x + width;
					
					if ((prevXOut || prevYOut) && (currentXOut || currentYOut)) {
						pdx = dx;
						pdy = dy;
						continue;
					}
					
					Point iTop = getLineIntersectionPoint(x, y, x + width, y, 						dx, dy, pdx, pdy);
					Point iBottom = getLineIntersectionPoint(x, y + height, x + width, y + height, 	dx, dy, pdx, pdy);
					Point iLeft = getLineIntersectionPoint(x, y, x, y + height, 					dx, dy, pdx, pdy);
					Point iRight = getLineIntersectionPoint(x + width, y, x + width, y + height, 	dx, dy, pdx, pdy);
					
					if (iTop != null && iBottom != null) {
						if (!first) g.drawLine((int) iTop.getX(), (int) iTop.getY(), (int) iBottom.getX(), (int) iBottom.getY());
						pdx = dx;
						pdy = dy;
						continue;
					} else if (iTop != null && iRight != null) {
						if (!first) g.drawLine((int) iTop.getX(), (int) iTop.getY(), (int) iRight.getX(), (int) iRight.getY());
						pdx = dx;
						pdy = dy;
						continue;
					} else if (iTop != null && iLeft != null) {
						if (!first) g.drawLine((int) iTop.getX(), (int) iTop.getY(), (int) iLeft.getX(), (int) iLeft.getY());
						pdx = dx;
						pdy = dy;
						continue;
					} else if (iLeft != null && iRight != null) {
						if (!first) g.drawLine((int) iLeft.getX(), (int) iLeft.getY(), (int) iRight.getX(), (int) iRight.getY());
						pdx = dx;
						pdy = dy;
						continue;
					} else if (iLeft != null && iBottom != null) {
						if (!first) g.drawLine((int) iLeft.getX(), (int) iLeft.getY(), (int) iBottom.getX(), (int) iBottom.getY());
						pdx = dx;
						pdy = dy;
						continue;
					} else if (iRight != null && iBottom != null) {
						if (!first) g.drawLine((int) iRight.getX(), (int) iRight.getY(), (int) iBottom.getX(), (int) iBottom.getY());
						pdx = dx;
						pdy = dy;
						continue;
					}
					
					double ddx = dx, ddy = dy, dpdx = pdx, dpdy = pdy;
					
					if (iTop != null) {
						if (prevYOut) {
							dpdx = iTop.getX();
							dpdy = iTop.getY();
						}
						
						if (currentYOut) {
							ddx = iTop.getX();
							ddy = iTop.getY();
						}
					} else if (iBottom != null) {
						if (prevYOut) {
							dpdx = iBottom.getX();
							dpdy = iBottom.getY();
						}
						
						if (currentYOut) {
							ddx = iBottom.getX();
							ddy = iBottom.getY();
						}
					} else if (iRight != null) {
						if (prevXOut) {
							dpdx = iRight.getX();
							dpdy = iRight.getY();
						}
						
						if (currentXOut) {
							ddx = iRight.getX();
							ddy = iRight.getY();
						}
					} else if (iLeft != null) {
						if (prevXOut) {
							dpdx = iLeft.getX();
							dpdy = iLeft.getY();
						}
						
						if (currentXOut) {
							ddx = iLeft.getX();
							ddy = iLeft.getY();
						}
					}
					
					
					if (!first) {
						g.drawLine((int) ddx, (int) ddy, (int) dpdx, (int) dpdy);
					}
					
					pdx = dx;
					pdy = dy;
					
					
				} else {
					
					if (c.getX() > hGraduation.getEnd() || c.getX() < hGraduation.getStart() || c.getY() < vGraduation.getStart() || c.getY() > vGraduation.getEnd()) {
						pdx = dx;
						pdy = dy;
						
						continue;
					}
					
					g.drawOval((int) dx - 2, (int) dy, 4, 4);
					
				}
				
				first = false;
				
				
			}
			
			if (dataSet.isShowName()) {
				g.drawString(dataSet.getName(), (int) (dx + metrics.stringWidth(dataSet.getName()) / 2), (int) dy + 2);
			}
			
		}
		
		
		g.setColor(Color.black);
		
		g.drawString(yLabel, x - metrics.stringWidth(yLabel), y - 5);
		
		g.drawString(xLabel, x + width + 4, y + height + 5);
		
		if (mouseCoord != null) {
			
			double mx = mouseCoord.x;
			double my = mouseCoord.y;
			
			if (mx > x && mx < x + width && my > y && my < y + height) {
				mx -= x;
				my -= y;
				
				mx = (mx / width * hRange) + hGraduation.getStart();
				my = vRange - (my / height * vRange) + vGraduation.getStart();
				
				String str = (float) mx + ", " + (float) my;
				
				g.setColor(new Color(100, 100, 100, 127));
				g.fillOval(mouseCoord.x - 3, mouseCoord.y - 3, 6, 6);
				
				g.setColor(Color.yellow);
				g.fillRoundRect(mouseCoord.x + 10, mouseCoord.y + 17, metrics.stringWidth(str), 10, 5, 5);
				
				g.setColor(Color.black);
				g.drawString(str, mouseCoord.x + 10, mouseCoord.y + 25);
				
			}
				
			
		}
		
		
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
		g.drawString(title, x + (width / 2) - metrics.stringWidth(title) / 2, y - 10);
		
		
	}
	
	public double getMaxYSubTicks(double threshhold) {
		return getMaxSubTicks(threshhold, vGraduation.getEnd() - vGraduation.getStart());
	}
	
	public double getMaxXSubTicks(double threshhold) {
		return getMaxSubTicks(threshhold, hGraduation.getEnd() - hGraduation.getStart());
	}
	
	private double getMaxSubTicks(double threshhold, double range) {
		double e = range / threshhold;
		
		double a,b,c,d,da,db,dc,dd;
		
		if (e < 1) {
			a = 0.2;
			b = 0.25;
			c = 0.5;
			
			da = Math.abs(e - a);
			db = Math.abs(e - b);
			dc = Math.abs(e - c);
			
			if (da < db && da < dc) {
				return a;
			} else if (db < da && db < dc) {
				return b;
			} else {
				return c;
			}
			
		} else {
			
			a = 1;
			b = 2;
			c = 5;
			d = 10;
			
			da = Math.abs(e - a);
			db = Math.abs(e - b);
			dc = Math.abs(e - c);
			dd = Math.abs(e - d);
			
			if (da < db && da < dc && da < dd) {
				return a;
			} else if (db < da && db < dc && db < dd) {
				return b;
			} else if (dc < da && dc < db && dc < dd) {
				return c;
			} else {
				return d;
			}
			
		}
	}
	

	@Override
	public void initJPanel(JPanel panel) {
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseCoord = null;
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseCoord = e.getPoint();
		
	}

	@Override
	public void mouseMoved(MouseEvent e) { }

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) { }

	
	private static Point getLineIntersectionPoint(double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
		
		if (y1 == y2) {
			x1 += 0.01;
		}
		
		if (y3 == y4) {
			x3 += 0.01;
		}
		
		if (x1 == x2) {
			y1 += 0.01;
		}
		
		if (x3 == x4) {
			y3 += 0.01;
		}
		
		
		
		double denom = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);
		if (denom == 0.0) { // Lines are parallel.
			return null;
		}
		double ua = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / denom;
		double ub = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / denom;
		if (ua >= 0.0f && ua <= 1.0f && ub >= 0.0f && ub <= 1.0f) {
			// Get the intersection point.
			return new Point((int) (x1 + ua * (x2 - x1)), (int) (y1 + ua * (y2 - y1)));
		}

		return null;
	}
	
	
}
