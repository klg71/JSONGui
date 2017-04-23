package gui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.components.ComponentValueType;
import gui.components.JSONComponent;

public class ComponentController {
	private HashMap<String, JSONComponent> componentMap;

	public ComponentController() {
		componentMap = new HashMap<>();
	}

	public void addComponent(String name, JSONComponent component) {
		componentMap.put(name, component);
	}

	public JSONObject getSingleResult(String name,ComponentValueType type) throws Exception {
		JSONObject result = null;
		try {
			result = componentMap.get(name).getValue(type);
		} catch (Exception e) {
			throw new Exception("Component not found");
		}
		return result;
	}
	
	public JSONArray getResults(Map<String,ComponentValueType> components) throws Exception{
		JSONArray array = new JSONArray();
		for(Map.Entry<String, ComponentValueType> entry:components.entrySet()){
			array.put(getSingleResult(entry.getKey(), entry.getValue()));
		}
		return array;
	}
	

}
