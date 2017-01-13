package test;

import lab.LabFrame;
import lab.component.BunsenBurner;
import lab.component.fx.Flame;

public class FlameTestLab extends LabFrame {

	private static final long serialVersionUID = -8388015545013019361L;

	public static void main(String[] args) {
		new FlameTestLab();

	}
	
	
	public FlameTestLab() {
		super("Flame Test", 400, 400);
		
		
		Flame flame = new Flame(200, 200, 200, 50);
		
		flame.setOffsetX(100);
		flame.setOffsetY(100);
		
		flame.setIntensity(80);
		flame.setNoiseIncrement(1);
		
		addComponent(flame);
		
		
		/*
		BunsenBurner burner = new BunsenBurner(30, 300);
		burner.setOffsetX(200);
		
		addComponent(burner);
		*/
		
		start(60);
	}

	@Override
	public void update() {
		
	}

}
