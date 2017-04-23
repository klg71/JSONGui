package gui;

import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.Component;


public class NavigationController {
	private class JSONAction extends Action{
		private NavigationController navigationController;
		private String name;
		
		public JSONAction(NavigationController navigationController, String name) {
			super();
			this.navigationController = navigationController;
			this.name = name;
		}

		@Override
		public void perform(Component arg0) {
			navigationController.actionPerformed(name);
		}
		
	}
	
	public void addAction(String name,Button button){
		button.setAction(new JSONAction(this, name));
	}
	
	private void actionPerformed(String name){
		System.out.println(name);
	}
}
