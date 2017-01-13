package lab.component.input;

import java.awt.Button;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

public abstract class ButtonComponent extends InputComponent {
	
	private final Button button;
	public ButtonComponent(int width, int height, String text) {
		super(width, height);
		
		button = new Button(text);
		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent a) {
				doSomething();
			}

		});

	}
	
	public abstract void doSomething();

	public Button getButton() {
		return button;
	}
	
	public void setFontSize(int size) {
		button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, size));
	}

	@Override
	public void draw(int x, int y, int width, int height, Graphics g) {
		
	}

	@Override
	public void drawInputs(int x, int y, int width, int height, JPanel panel) {
		panel.add(button);
		button.setSize(width, height);
		button.setLocation(x, y);
	}
	
	@Override
	public Component getJComponent() {
		return button;
	}

}
