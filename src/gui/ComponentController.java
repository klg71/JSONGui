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

	public JSONObject getSingleResult(String name,ComponentValueType type) throws ComponentNotFoundException {
		JSONObject result = null;
		try {
			result = componentMap.get(name).getValue(type);
		} catch (Exception e) {
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
