package gui.components;

import org.apache.pivot.wtk.Component;
import org.json.JSONObject;

public abstract class JSONComponent {
	protected String name;
	protected Component component;
	
	public JSONObject getValue(ComponentValueType type){
		JSONObject object = new JSONObject();
		object.put("name", name);
		return object;
	}
	public abstract void setValue(JSONObject object);
	
	public JSONComponent(String name, Component component) {
		super();
		this.name = name;
		this.component = component;
	}
	public String getName() {
		return name;
	}
	public Component getComponent() {
		return component;
	}
	

}
