package gui.components;

import java.util.Arrays;

public enum ComponentValueType {
	VALUE("value"), SELECTION("selection");
	
	private String text;
	
	private ComponentValueType(String text){
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}
	
	public static ComponentValueType findByJSONName(String name){
		for(ComponentValueType type:Arrays.asList(ComponentValueType.values())){
			if(name.equals(type.toString())){
				return type;
			}
		}
		return null;
	}
	
	
}
