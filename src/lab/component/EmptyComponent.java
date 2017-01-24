package lab.component;

import java.awt.Graphics;

public class EmptyComponent extends LabComponent {

	public EmptyComponent(int width, int height) {
		super(width, height);
	}

	@Override
	public void draw(int x, int y, int width, int height, Graphics g) { }

}
