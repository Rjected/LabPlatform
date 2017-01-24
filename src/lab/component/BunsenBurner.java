package lab.component;

import java.awt.Color;
import java.awt.Graphics;

import lab.component.fx.Flame;

public class BunsenBurner extends LabComponent {

	private final Flame flame;
	
	public BunsenBurner(int width, int height) {
		super(width, height);
		
		flame = new Flame(width, height / 7, width / 2, height / 20);
		
		flame.setIntensity(150);
		flame.setNoiseIncrement(20);
		
		setOffsetX(30);
		setOffsetY(30);
		
		addChild(flame);
	}
	
	public Flame getFlames() {
		return flame;
	}

	@Override
	public void draw(int x, int y, int width, int height, Graphics g) {
		g.setColor(Color.gray);
		g.fillRect(x, y, width, height);
		g.setColor(Color.darkGray);
		g.fillRect(x - width / 5, y + height - height / 10, width + width / 5 * 2, height / 10);
	}

}
