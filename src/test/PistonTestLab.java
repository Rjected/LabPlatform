package test;

import lab.LabFrame;
import lab.component.Piston;

public class PistonTestLab extends LabFrame {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new PistonTestLab();
	}
	
	private final Piston piston;
	
	public PistonTestLab() {
		super("Piston Test Lab", 800, 800);
		
		piston = new Piston(300, 300);
		
		addComponent(piston);
		
		start(60);
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
