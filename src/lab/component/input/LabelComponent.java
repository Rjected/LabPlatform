package lab.component.input;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class LabelComponent extends InputComponent {

	private String labelText;
	private final JLabel label;
	
	public LabelComponent(int width, int height, String text) {
		super(width,height);
		
		label = new JLabel();
		labelText = text;
		
	}
	
	@Override
	public Component getJComponent() {
		return label;
	}
	
	public void setText(String labelText) {
		this.labelText = labelText;
		label.setText(labelText);
	}
	
	public String getText() {
		return labelText;
	}
	
	public void setFontSize(int size) {
		label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, size));
	}
	
	public void setFont(Font font) {
		label.setFont(font);
	}
	
	@Override
	public void draw(int x, int y, int width, int height, Graphics g) {

	}

	@Override
	public void drawInputs(int x, int y, int width, int height, JPanel panel) {
		panel.remove(label);
		panel.add(label);
		label.setSize(width, height);
		label.setLocation(x, y);
		label.setEnabled(this.isActivated());
		
		label.setText(labelText);

	}
	

}
