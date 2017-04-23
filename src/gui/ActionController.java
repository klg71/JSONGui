package gui;

import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.Component;

public class ActionController {

	private class JSONAction extends Action{
		private ActionController actionController;
		private String name;
		
		public JSONAction(ActionController actionController, String name) {
			super();
			this.actionController = actionController;
			this.name = name;
		}

		@Override
		public void perform(Component arg0) {
			actionController.actionPerformed(name);
		}
		
	}
	
	public void addAction(String name,Button button){
		button.setAction(new JSONAction(this, name));
	}
	
	private void actionPerformed(String name){
		System.out.println(name);
	}
}
