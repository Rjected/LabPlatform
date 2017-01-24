package test;

import lab.LabFrame;
import lab.component.input.TextFieldComponent;

public class InputTestLab extends LabFrame {
	
	private static final long serialVersionUID = 1761957305529612612L;

	public static void main(String[] args) {
		new InputTestLab();
	}
	
	
	public InputTestLab() {
		super("Input Test Lab", 800, 800);
		
	
		addComponent(new TextFieldComponent(100, 20, "####"));
		addComponent(new TextFieldComponent(100, 20, "####"));
		addComponent(new TextFieldComponent(100, 20, "####"));
		
		
		
		
		
		
		start(60);
	}

	@Override
	public void update() {
		
	}

}
