package lab.component.input;

import java.awt.Color;
import java.awt.Component;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SliderComponent extends InputComponent implements ChangeListener {

	public static final int HORIZONTAL = 0;
	public static final int VERTICAL = 1;
	
	private final JSlider slider;
	private final float min;
	private final float max;
	private final float increment;

	public SliderComponent(int width, int height, float min, float max, float increment, int orientation) {
		
		super(width, height);
		
		slider = new JSlider(0, (int) ((max - min) / increment));
		slider.setValue(0);
		slider.setOrientation(orientation);
		slider.setBackground(Color.white);
		slider.addChangeListener(this);
		
		this.min = min;
		this.max = max;
		this.increment = increment;
		
	}
	
	public void setValue(float v) {
		v = Math.max(min, Math.min(max, v));
		v -= min;
		slider.setValue((int) (v / increment));
	}
	
	public float getMin() {
		return min;
	}

	public float getMax() {
		return max;
	}

	public float getValue() {
		return slider.getValue() * increment + min;
	}
	
	public int getOrientation() {
		return slider.getOrientation();
	}
	
	@SuppressWarnings("unchecked")
	public void addValueLabel(float value, String label) {
		Dictionary<Integer, JLabel> labels = slider.getLabelTable();
		
		if (labels == null) {
			labels = new Hashtable<Integer, JLabel>();
		}
		
		labels.put(new Integer((int) ((value - min) / increment)), new JLabel(label));
		
		slider.setLabelTable(labels);
		
		slider.setPaintLabels(true);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		onChange();
	}
	
	public void onChange() {
		
	}

	@Override
	public Component getJComponent() {
		return slider;
	}
	
}
