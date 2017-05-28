package gui;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import gui.components.ComponentValueType;
import gui.components.JSONComponent;

public class ComponentController {
	
	public class ComponentNotFoundException extends Exception {
		/**
		 * Thrown when component could not be found in controller
		 */
		private static final long serialVersionUID = 1L;

		public ComponentNotFoundException(String name){
			super("Component not found: "+name);
		}
	}
	
	private HashMap<String, JSONComponent> componentMap;

	public ComponentController() {
		componentMap = new HashMap<>();
	}

	public void addComponent(String name, JSONComponent component) {
		componentMap.put(name, component);
	}
	
	public void resetComponents(){
		for(JSONComponent comp: componentMap.values()){
			comp.stopUpdate();
		}
		componentMap.clear();
	}

	public JSONObject getSingleResult(String name,ComponentValueType type) throws Exception {
		JSONObject result = null;
		if (componentMap.containsKey(name)){
			result = componentMap.get(name).getValue(type);
		} else {
			throw new ComponentNotFoundException(name);
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
