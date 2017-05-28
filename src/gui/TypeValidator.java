package gui;

import gui.components.ComponentType;

public class TypeValidator implements org.apache.pivot.wtk.validation.Validator{

	private ComponentType componentType;
	
	public TypeValidator(ComponentType type) {
		this.componentType = type;
	}
	
	@Override
	public boolean isValid(String s) {
		try {
			switch (componentType) {
			case DOUBLE:
				Double.parseDouble(s.replace(",", "."));
				break;
			case INTEGER:
				Integer.parseInt(s);
				break;
			case STRING:
				break;
			default:
				break;
	
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;
	}

}
