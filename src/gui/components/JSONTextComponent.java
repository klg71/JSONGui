package gui.components;

import org.apache.pivot.wtk.TextInput;
import org.json.JSONObject;

public class JSONTextComponent extends JSONComponent {

	private TextInput component;
	

	public JSONTextComponent(String name, TextInput component) {
		super(name, component);
		this.component = component;
	}

	@Override
	public JSONObject getValue(ComponentValueType type) {
		JSONObject object = new JSONObject(type);
		object.put("name", name);
		switch(type){
		case CONTENT:
			object.put("value", component.getText());
			break;
		case SELECTION:
			object.put("value", component.getSelectedText());
			break;
		}
		return object;
	}

	@Override
	public void setValue(JSONObject object, ComponentValueType type) {
		// TODO Auto-generated method stub
		
	}

}
