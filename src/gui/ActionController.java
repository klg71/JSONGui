package gui;

import java.util.ArrayList;
import java.util.List;

import org.apache.pivot.json.JSON;
import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.Component;
import org.json.JSONArray;
import org.json.JSONObject;

public class ActionController {
	
	private ArrayList<PaneListener> listeners;
	
	private class JSONAction extends Action{
		private ActionController actionController;
		private String name;
		private List<String> fields;
		
		public JSONAction(ActionController actionController, String name, List<String> fields) {
			super();
			this.actionController = actionController;
			this.name = name;
			this.fields = new ArrayList<String>(fields);
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
	
	public void addAction(String name,Button button, List<String> fields){
		button.setAction(new JSONAction(this, name, fields));
	}
	
	private void actionPerformed(String name, List<String> fields){
		for(PaneListener listener: listeners){
			listener.actionPerformed(name, fields);
		}
	}
}
