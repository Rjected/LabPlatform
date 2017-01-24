package lab.component.input;

import java.awt.Component;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SwitchComponent extends InputComponent implements ChangeListener {

	private final JCheckBox switchField;
	
	public SwitchComponent(int width, int height) {
		
		super(width, height);
		
		switchField = new JCheckBox(new ImageIcon("src/resources/offSwitch.png", "Off"));
		switchField.setSelectedIcon(new ImageIcon("src/resources/onSwitch.png", "On"));
		
		switchField.addChangeListener(this);
	}

	public void onAction() {
		
	}

	public void offAction() {
		
	}
	
	@Override
	public Component getJComponent() {
		return switchField;
	}

	
	@Override
	public void stateChanged(ChangeEvent e) {
		
		JCheckBox source = (JCheckBox) e.getSource();
		
		if (source.isSelected()) {
			
			onAction();
			
		} else {
			
			offAction();
			
		}
	}

}
