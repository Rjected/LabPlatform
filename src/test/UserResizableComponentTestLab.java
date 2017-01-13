package test;

import lab.LabFrame;

public class UserResizableComponentTestLab extends LabFrame {
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new UserResizableComponentTestLab();
	}
	
	public UserResizableComponentTestLab() {
		super("Resizable Component Test", 800, 800);
		
		RectComponent c = new RectComponent(100, 100);
		c.setOffsetX(300);
		c.setOffsetY(300);
		
		addComponent(c);
		
		getRoot().setScaleChildren(false);
		
		start(60);
	}

	@Override
	public void update() {
		
	}

}
