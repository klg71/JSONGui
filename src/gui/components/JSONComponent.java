package gui.components;

import org.apache.pivot.wtk.Component;
import org.json.JSONObject;

public abstract class JSONComponent {
	protected String name;
	protected Component component;
	protected ComponentType componentType;
	protected JSONObject dataObject;
	protected JSONObject metaData;

	public JSONComponent(Component component, ComponentType type, JSONObject dataObject, JSONObject metaData) {
		super();
		this.name = metaData.getString("name");
		this.component = component;
		this.componentType = type;
		this.dataObject = dataObject;
		this.metaData = metaData;
	}
	
	public JSONObject getValue(ComponentValueType type){
		JSONObject object = new JSONObject();
		object.put("name", name);
		return object;
	}
	public abstract void setValue(JSONObject object, ComponentValueType type);
	
	public String getName() {
		return name;
	}
	public Component getComponent() {
		return component;
	}
	

}
