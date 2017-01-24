package lab.component.input;

import java.awt.Button;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class ButtonComponent extends InputComponent implements ActionListener {
	
	private final Button button;
	
	public ButtonComponent(int width, int height, String text) {
		super(width, height);
		
		button = new Button(text);
		
		button.addActionListener(this);

	}
	
	public abstract void doSomething();

	public Button getButton() {
		return button;
	}
	
	public void setFontSize(int size) {
		button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, size));
	}
	
	@Override
	public Component getJComponent() {
		return button;
	}
	
	@Override
	public void actionPerformed(ActionEvent a) {
		doSomething();
	}


}
