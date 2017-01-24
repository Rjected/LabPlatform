package lab.component.sensor;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import lab.component.GraduatedComponent;
import lab.component.Graduation;
import lab.component.VerticalGraduation;
import lab.component.container.ContentState;
import lab.component.container.GraduatedCylinder;

public class Manometer extends GraduatedComponent {
	
	private static final int CONNECTOR_HEIGHT = 30;
	private static final Color MERCURY_COLOR = new Color(75, 75, 75);
	
	protected final GraduatedCylinder cyl1, cyl2;
	
	public Manometer(int width, int height) {
		super(width, height);
		
		setOffsetX(30);
		setOffsetY(30);
		
		cyl1 = new GraduatedCylinder(width / 3, height - CONNECTOR_HEIGHT);
		cyl1.setGraduation(new VerticalGraduation(0, 760, 40, 5));
		
		cyl2 = new GraduatedCylinder(width / 3, height - CONNECTOR_HEIGHT);
		cyl2.setGraduation(new VerticalGraduation(0, 760, 40, 5));
		

		applyGraduationSettings();
		
		setValue(760);
		
	}
	
	private void applyGraduationSettings() {
		cyl1.getGraduation().setShowLabels(true);
		cyl1.setContentState(ContentState.LIQUID);
		cyl1.setContentColor(MERCURY_COLOR);
		
		cyl2.getGraduation().setShowLabels(true);
		cyl2.getGraduation().setSuffix("mm");
		cyl2.setContentState(ContentState.LIQUID);
		cyl2.setContentColor(MERCURY_COLOR);
	}
	
	@Override
	public void setGraduation(Graduation graduation) {
		cyl1.setGraduation(graduation);
		cyl2.setGraduation(new VerticalGraduation(graduation));
		
		applyGraduationSettings();
		
		super.setGraduation(graduation);
	}
	
	@Override
	public void setValue(double mmHg) {
		
		
		cyl1.setValue(mmHg / 2);
		cyl2.setValue(-mmHg / 2 + 760);
		
		// 360 mmHg pressure
		// 560 mmHg right
		// 200 mmHg left
		
		
	}
	
	@Override
	public double getValue() {
		return 760 + (cyl1.getValue() - cyl2.getValue());
	}

	@Override
	public void draw(int x, int y, int width, int height, Graphics g) {
		
		
		g.setColor(MERCURY_COLOR);
		g.fillRoundRect(x, y + height - CONNECTOR_HEIGHT, width, CONNECTOR_HEIGHT, 20, 20);
		
		g.setColor(Color.gray);
		g.drawRoundRect(x, y + height - CONNECTOR_HEIGHT, width, CONNECTOR_HEIGHT, 20, 20);
		
		g.setColor(MERCURY_COLOR);
		
		g.fillRect(x, y + height - CONNECTOR_HEIGHT, width, CONNECTOR_HEIGHT / 2);
		
		g.setColor(Color.gray);
		
		g.drawLine(x, y + height - CONNECTOR_HEIGHT, x, y + height - CONNECTOR_HEIGHT / 2);
		g.drawLine(x + width, y + height - CONNECTOR_HEIGHT, x + width, y + height - CONNECTOR_HEIGHT / 2);
		

		g.setColor(Color.white);
		g.fillRoundRect(x + width / 3, y + height - CONNECTOR_HEIGHT - 40, width / 3, 50, 20, 20);
		
		g.setColor(Color.gray);
		g.drawRoundRect(x + width / 3, y + height - CONNECTOR_HEIGHT - 40, width / 3, 50, 20, 20);
		
		g.setColor(Color.white);
		g.fillRect(x + width / 3 + 1, y + height - CONNECTOR_HEIGHT - 40, width / 3 - 1, 40);
		
		cyl1.draw(x, y, width / 3, height - CONNECTOR_HEIGHT, g);
		cyl2.draw(x + (2 * width / 3), y, width / 3, height - CONNECTOR_HEIGHT, g);
		
		
		g.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		g.setColor(Color.black);
		g.drawString("atm", x, y - 5);
		g.drawString("gas", x + width / 3 * 2, y - 5);
		
		if (canShowValue()) {
			FontMetrics metrics = g.getFontMetrics();
			
			g.setColor(Color.black);
			
			String str = (int) getValue() + "mm Hg";
			
			g.drawString(str, x + metrics.stringWidth(str) / 2, y + height + 20);
		}
	}

}
