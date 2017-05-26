package gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pivot.json.JSON;
import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.Component;
import org.json.JSONArray;
import org.json.JSONObject;

import gui.components.ComponentValueType;

public class ActionController {
	
	private ArrayList<PaneListener> listeners;
	
	
	private class JSONAction extends Action{
		private ActionController actionController;
		private String name;
		private Map<String,ComponentValueType> fields;
		
		public JSONAction(ActionController actionController, String name, Map<String,ComponentValueType> fields) {
			super();
			this.actionController = actionController;
			this.name = name;
			this.fields = new HashMap<String,ComponentValueType>(fields);
		}

		@Override
		public void perform(Component arg0) {
			actionController.actionPerformed(name, fields);
		}
		
	}
	
	public ActionController(){
		listeners = new ArrayList<>();
	}
	
	public void addActionListener(PaneListener listener){
		listeners.add(listener);
	}
	
	public void addAction(String name,Button button, Map<String,ComponentValueType> fields){
		button.setAction(new JSONAction(this, name, fields));
	}
	
	private void actionPerformed(String name, Map<String,ComponentValueType> fields){
		for(PaneListener listener: listeners){
			listener.actionPerformed(name, fields);
		}
	}
}
