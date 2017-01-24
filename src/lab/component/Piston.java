package lab.component;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public class Piston extends GraduatedComponent {

	private Color gasColor = Color.red;
	private double gasMass = 1.0;
	private double transparencyModifier = 1.0;
	private ClickableArea dragArea = new ClickableArea(this);
	
	public Piston(int width, int height) {
		super(width, height);

		setGraduation(new VerticalGraduation(0, 25, 5, 1));
		getGraduation().setSuffix("mL");
		//getGraduation().setColor(Color.white);
		
		setOffsetX(30);
		setOffsetY(30);
		
		setValue(0);
		
		setShowValue(true);
		
		dragArea.setScale(false);
	}
	
	public Color getGasColor() {
		return gasColor;
	}

	public void setGasColor(Color gasColor) {
		this.gasColor = gasColor;
	}

	public double getGasMass() {
		return gasMass;
	}

	public void setGasMass(double gasMass) {
		this.gasMass = gasMass;
	}
	
	public double getTransparencyModifier() {
		return transparencyModifier;
	}

	public void setTransparencyModifier(double transparencyModifier) {
		this.transparencyModifier = transparencyModifier;
	}
	
	@Override
	public void draw(int x, int y, int width, int height, Graphics g) {
		List<Point> edge = new ArrayList<Point>();
		edge.add(new Point(x + width, y + height));
		edge.add(new Point(x + width, y));
		getGraduation().setEdge(edge);
		
		double range = getGraduation().getEnd() - getGraduation().getStart();
		
		double pistonY = (range - getValue()) / range * height;
		double pistonHeight = getGraduation().getSubLineIntervals() / range * height;
		
		
		
		g.setColor(Color.lightGray);
		g.fillRect(x, y, width, height);
		
		double transparency = pistonY / height * transparencyModifier *  255;
		
		transparency = Math.max(transparency, 0);
		transparency = Math.min(transparency, 255);
		
		g.setColor(new Color(gasColor.getRed(), gasColor.getGreen(), gasColor.getBlue(), (int) (transparency)));
		g.fillRect(x, (int) (y + pistonY), width + 1, (int) (height - pistonY) + 1);
		
		g.setColor(Color.darkGray);
		g.fillRect(x - 5, y - (int) pistonHeight, 5, height + (int) pistonHeight);
		g.fillRect(x - 5, y + height, width + 10, 5);
		g.fillRect(x + width, y - (int) pistonHeight, 5, height + (int) pistonHeight);
		
		
		
		g.setColor(Color.darkGray);
		g.fillRect(x, (int) (pistonY + y - pistonHeight), width + 1, (int) pistonHeight + 1);
		
		
		if (getGraduation() != null) {
			getGraduation().draw(g, width, height);
		}
		
		dragArea.checkRaw(x, (int) (pistonY - pistonHeight) + y, width, (int) pistonHeight);
		
		
		if (dragArea.hasClick()) {
			setValue(range - (double) (dragArea.getMousePosition().y - y) / height * range);
		}
		
		

		if (getValue() > range) {
			setValue(range);
		}
		
		if (getValue() < 0) {
			setValue(0);
		}
		
		
		if (canShowValue()) {
			g.setColor(Color.black);
			g.drawString((float) getValue() + "mL", x, y + height + 20);
		}

	}
	
	@Override
	public void initJPanel(JPanel panel) {
		dragArea.initializeMouseListeners(panel);
	}
	
}
