package gui;

import org.apache.pivot.wtk.FillPane;
import org.apache.pivot.wtk.Orientation;

public class MainPane extends FillPane {
	private ComponentController componentController;
	private ActionController actionController;
	private NavigationController navigationController;

	public MainPane(ComponentController componentController, ActionController actionController,
			NavigationController navigationController) {
		super();
		this.componentController = componentController;
		this.actionController = actionController;
		this.navigationController = navigationController;
	}

	public MainPane(Orientation orientation, ComponentController componentController, ActionController actionController,
			NavigationController navigationController) {
		super(orientation);
		this.componentController = componentController;
		this.actionController = actionController;
		this.navigationController = navigationController;
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
	

}
