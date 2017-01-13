package editor.fieldregistry;

import java.lang.reflect.Field;

import lab.component.LabComponent;
import lab.component.input.InputComponent;

public class EditableField {

	private final Field field;
	private final InputComponent input;
	
	public EditableField(Field field, InputComponent input) {
		this.field = field;
		this.input = input;
	}
	
	public InputComponent getInputComponent() {
		return input;
	}
	
	public String getName() {
		return field.getName();
	}
	
	public Object getValue(LabComponent c) {
		try {
			return field.get(c);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void setValue(LabComponent c, Object value) {
		try {
			field.set(c, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
