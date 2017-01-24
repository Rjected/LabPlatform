package test;

import lab.LabFrame;
import lab.component.LabComponent;

public class UserResizableComponentTestLab extends LabFrame {
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		new UserResizableComponentTestLab();
	}
	
	public UserResizableComponentTestLab() {
		super("Resizable Component Test", 800, 800);
		
		RectComponent c1 = new RectComponent(100, 100);
		c1.setOffsetX(300);
		c1.setOffsetY(300);
		
		RectComponent c2 = new RectComponent(100, 100);
		c2.setOffsetX(250);
		c2.setOffsetY(250);
		c2.setZOrder(2);
		
		addComponent(c2);
		addComponent(c1);
		
		getRoot().setLayout(LabComponent.FREE_FORM);
		
		getRoot().setScaleChildren(false);
		
		start(60);
	}

	@Override
	public void update() {
		
	}

}
