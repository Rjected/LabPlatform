package lab.component.input;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

public class TextFieldComponent extends InputComponent implements ActionListener {

	private final JFormattedTextField textField;

	public TextFieldComponent(int width, int height, String format) {
		super(width, height);

		textField = new JFormattedTextField(createFormatter(format));
		textField.setColumns(format.length());
		textField.addActionListener(this);

	}

	public TextFieldComponent(int width, int height, int nonDecimal, int decimal) {
		this(width, height, buildDecimal(nonDecimal, decimal));
	}

	protected MaskFormatter createFormatter(String s) {

		MaskFormatter formatter = null;

		try {
			formatter = new MaskFormatter(s);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return formatter;
	}

	public double getValue() {
		return (double) textField.getValue();
	}

	public void setText(String s) {
		try {
			textField.setValue(s);
		} catch (Exception e) {
			textField.setValue(0);
		}
	}

	@Override
	public JComponent getJComponent() {
		return textField;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		onChanged();
	}

	public void onChanged() {

	}

	private static String buildDecimal(int n, int d) {

		String s = "";
		for (int i = 0; i < n; i++) {
			s += "#";
		}
		if (d > 0) {
			s += ".";
		}
		for (int i = 0; i < d; i++) {
			s += "#";
		}
		return s;

	}

}
