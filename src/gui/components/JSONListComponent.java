package gui.components;

import org.apache.pivot.collections.List;
import org.apache.pivot.wtk.Action;
import org.apache.pivot.wtk.ApplicationContext;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ListView;
import org.apache.pivot.wtk.Menu;
import org.apache.pivot.wtk.MenuBar;
import org.apache.pivot.wtk.MenuHandler;
import org.apache.pivot.wtk.Prompt;
import org.json.JSONArray;
import org.json.JSONObject;

import gui.SimpleDialog;

public class JSONListComponent extends JSONComponent {

	private ListView view;

	public JSONListComponent(ListView view, ComponentType type, JSONObject dataObject, JSONObject metaData) {
		super(view, type, dataObject, metaData);
		this.view = view;
		MenuHandler menuHandler = new MenuHandler.Adapter() {

			@Override
			public boolean configureContextMenu(Component component, Menu menu, int x, int y) {

				Menu.Section menuSection = new Menu.Section();
				menu.getSections().add(menuSection);

				Menu.Item addMenu = new Menu.Item("Add");
				addMenu.setAction(new Action() {
					@Override
					public void perform(Component source) {
						SimpleDialog simpleDialog = new SimpleDialog("Add Entry", new SimpleDialog.Callback() {
			
							@Override
							public void addEntry(String entry) {
								List<String> data = (List<String>) view.getListData();
								data.add(entry);
								view.setListData(data);
							}
							
						});
						simpleDialog.open(view.getWindow());
					}
				});
				menuSection.add(addMenu);
				Menu.Item deleteMenu = new Menu.Item("Remove");

				if (view.getSelectedIndex() == -1) {
					deleteMenu.setEnabled(false);
				} else {
					deleteMenu.setAction(new Action() {
						@Override
						public void perform(Component source) {
							List<String> data = (List<String>) view.getListData();
							data.remove(data.get(view.getSelectedIndex()));
							view.setListData(data);
						}
					});
				}

				menuSection.add(deleteMenu);
				return false;
			}
		};
		view.setMenuHandler(menuHandler);
	}

	@Override
	public JSONObject getValue(ComponentValueType type) {

		JSONObject object = super.getValue(type);
		switch (type) {
		case VALUE:
			JSONArray entries = new JSONArray();
			for (Object entry : view.getListData()) {
				entries.put(entry.toString());
			}
			object.put("value", entries);
			break;
		case SELECTION:
			object.put("value", view.getListData().get(view.getSelectedIndex()).toString());
			break;
		}

		return object;
	}

	@Override
	public void setValue(JSONObject object, ComponentValueType type) {
		// TODO Auto-generated method stub

	}

}
