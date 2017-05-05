package gui;

import java.util.List;

import org.apache.pivot.wtk.Orientation;
import org.apache.pivot.wtk.SplitPane;

import gui.components.ComponentValueType;

public class MainPane extends SplitPane implements ActionListener {
	private ComponentController componentController;
	private ActionController actionController;
	private NavigationController navigationController;


	public MainPane(Orientation orientation, ComponentController componentController, ActionController actionController,
			NavigationController navigationController) {
		super(orientation);
		this.componentController = componentController;
		this.actionController = actionController;
		this.navigationController = navigationController;
		
		actionController.addActionListener(this);
	}

	public ComponentController getComponentController() {
		return componentController;
	}

	public ActionController getActionController() {
		return actionController;
	}

	public NavigationController getNavigationController() {
		return navigationController;
	}

	@Override
	public void actionPerformed(String actionName, List<String> fields) {
		System.out.println(actionName);
		for(String field:fields){
			System.out.println(field);
			try {
				System.out.println(componentController.getSingleResult(field, ComponentValueType.SELECTION).toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

}
