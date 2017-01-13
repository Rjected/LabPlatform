package lab.component;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class ClickableArea implements MouseInputListener, MouseMotionListener {
	
	private int x, y, width, height;
	private final LabComponent component;
	private Point mousePosition = null;
	private Point clickPosition = null;
	private Point relativeClick = null;
	private boolean hasClick = false;
	private boolean hasHover = false;
	private boolean scale = false;
	
	public ClickableArea(LabComponent component, int x, int y, int width, int height) {
		this.component = component;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public ClickableArea(LabComponent component) {
		this(component, 0, 0, 0, 0);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Point getMousePosition() {
		return mousePosition;
	}

	public Point getClickPosition() {
		return clickPosition;
	}

	public boolean hasClick() {
		return hasClick;
	}
	
	public boolean hasHover() {
		return hasHover;
	}
	
	public Point getClickRelativeToPosition() {
		return relativeClick;
	}
	
	public Point getDragDelta() {
		return new Point(mousePosition.x - clickPosition.x, mousePosition.y - clickPosition.y);
	}
	
	public LabComponent getComponent() {
		return component;
	}

	public boolean canScale() {
		return scale;
	}
	
	public void setScale(boolean scale) {
		this.scale = scale;
	}
	
	public void initializeMouseListeners(JPanel panel) {
		panel.addMouseListener(this);
		panel.addMouseMotionListener(this);
	}
	
	public void check(int sx, int sy, int sw, int sh) {
		
		
		if (clickPosition != null && !hasClick) {
			
			if (isInBounds1(clickPosition, sx, sy, sw, sh)) {
				hasClick = true;
				relativeClick = new Point(sx - clickPosition.x, sy - clickPosition.y);
			} else {
				clickPosition = null;
			}
			
		}
		
		if (mousePosition != null) {
			hasHover = isInBounds1(mousePosition, sx, sy, sw, sh);
		}
		
	}
	
	private boolean isInBounds1(Point p, int sx, int sy, int sw, int sh) {
		double bx, by, bw, bh;
		
		if (sw != component.getWidth() && sh != component.getHeight() && scale) {
		
			bx = ((double) x / component.getWidth()) * sw + sx;
			by = ((double) y / component.getHeight()) * sh + sy;
			
			bw = (double) width / component.getWidth() * sw;
			bh = (double) height / component.getHeight() * sh;
			
		} else {
			bx = x + sx;
			by = y + sy;
			bw = width;
			bh = height;
		}
		
		return isInBounds2(p, bx, by, bw, bh);
	}
	
	private boolean isInBounds2(Point p, double sx, double sy, double sw, double sh) {
		return p.x > sx && p.x < sx + sw && p.y > sy && p.y < sy + sh;
	}
	
	public void checkRaw(int sx, int sy, int sw, int sh) {
		
		if (clickPosition != null && !hasClick) {
			
			if (isInBounds2(clickPosition, sx, sy, sw, sh)) {
				hasClick = true;
				relativeClick = new Point(sx - clickPosition.x, sy - clickPosition.y);
			} else {
				clickPosition = null;
			}
			
			
		}
		
		if (mousePosition != null) {
			hasHover = isInBounds2(mousePosition, sx, sy, sw, sh);
		}
		
	}
	
	
	
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (!hasClick) {
			clickPosition = e.getPoint();
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		hasClick = false;
		clickPosition = null;
		mousePosition = null;
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (clickPosition != null) {
			mousePosition = e.getPoint();
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		mousePosition = e.getPoint();
	}


	@Override
	public void mouseClicked(MouseEvent e) { }
	@Override
	public void mouseEntered(MouseEvent e) { }
	@Override
	public void mouseExited(MouseEvent e) { }
	
	
}