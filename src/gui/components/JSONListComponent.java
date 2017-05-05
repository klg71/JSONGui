package gui.components;

import org.apache.pivot.wtk.ListView;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONListComponent extends JSONComponent {

	private ListView view;

	public JSONListComponent(String name, ListView view) {
		super(name, view);
		this.view = view;
	}

	@Override
	public JSONObject getValue(ComponentValueType type) {

		JSONObject object = super.getValue(type);
		switch (type) {
		case CONTENT:
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
