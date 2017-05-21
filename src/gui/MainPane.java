package gui;

import java.util.ArrayList;
import java.util.List;

import org.apache.pivot.json.JSON;
import org.apache.pivot.wtk.Orientation;
import org.apache.pivot.wtk.SplitPane;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import gui.ComponentController.ComponentNotFoundException;
import gui.components.ComponentValueType;

public class MainPane extends SplitPane implements PaneListener {
	private ComponentController componentController;
	private ActionController actionController;
	private NavigationController navigationController;

	private ArrayList<Context> contexts;

	public MainPane(Orientation orientation, ComponentController componentController, ActionController actionController,
			NavigationController navigationController) {
		super(orientation);
		this.componentController = componentController;
		this.actionController = actionController;
		this.navigationController = navigationController;

		actionController.addActionListener(this);
		contexts = new ArrayList<>();
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
		JSONObject action = new JSONObject();
		action.put("name", actionName);
		JSONArray jsonFields = new JSONArray();

		System.out.println(actionName);
		for (String field : fields) {
			JSONObject jsonField = new JSONObject();
			jsonField.put("name", field);
			try {
				jsonField.put("value",
						componentController.getSingleResult(field, ComponentValueType.SELECTION).toString());
			} catch (ComponentNotFoundException e) {
				e.printStackTrace();
			}
			System.out.println(field);
		}
		action.put("fields", jsonFields);
		for (Context context : contexts) {
			context.actionPerformed(action);
		}
	}

	public void addContext(Context context) {
		contexts.add(context);
	}

	@Override
	public void navigationPerformed(String name) {
		JSONObject navigation = new JSONObject();
		navigation.put("name", name);
		for (Context context : contexts) {
			context.navigationPerformed(navigation);
		}
	}

}
