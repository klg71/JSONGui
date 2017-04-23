package gui;

import org.apache.pivot.wtk.FillPane;
import org.apache.pivot.wtk.Orientation;

public class MainPane extends FillPane {
	private ComponentController componentController;
	private ActionController actionController;

	public MainPane(ComponentController componentController,ActionController actionController) {
		super();
		this.componentController = componentController;
		this.actionController = actionController;
	}
	
	public MainPane(Orientation orientation, ComponentController componentController, ActionController actionController) {
		super(orientation);
		this.componentController = componentController;
		this.actionController = actionController;
	}

	public ComponentController getComponentController() {
		return componentController;
	}
	
	public ActionController getActionController(){
		return actionController;
	}
	
	

}
