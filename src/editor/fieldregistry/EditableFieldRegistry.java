package editor.fieldregistry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lab.component.LabComponent;
import lab.component.input.InputComponent;

public class EditableFieldRegistry {

	private static final Map<Class<? extends LabComponent>, List<EditableField>> registry = new HashMap<Class<? extends LabComponent>, List<EditableField>>();
	private static Field modifiersField;
	
	static {
		try {
			modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		
	}
	
	public static Set<Class<? extends LabComponent>> getEditableLabComponents() {
		return registry.keySet();
	}
	
	public static void registerField(Class<? extends LabComponent> clazz, String fieldName, InputComponent input) {
		Field field = null;
		
		try {
			field = findAndPrepareField(clazz, fieldName);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return;
		}
		
		List<EditableField> fields;

		if (registry.containsKey(clazz)) {
			fields = registry.get(clazz);
			fields.add(new EditableField(field, input));
			
		} else {
			fields = new ArrayList<EditableField>();
			
			fields.add(new EditableField(field, input));
			
			registry.put(clazz,  fields);
		}

	}

	private static Field findAndPrepareField(Class<? extends LabComponent> clazz, String fieldName) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = clazz.getDeclaredField(fieldName);

		field.setAccessible(true);

		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		
		return field;
	}
	

}
