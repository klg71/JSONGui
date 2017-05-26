package gui.components;

import org.apache.pivot.wtk.ScrollPane;
import org.apache.pivot.wtk.TextArea;
import org.json.JSONObject;

public class JSONTextAreaComponent extends JSONComponent {

	private TextArea area;
	public JSONTextAreaComponent(String name, TextArea area, ScrollPane scrollPane) {
		super(name, scrollPane);
		this.area = area;
	}

	@Override
	public JSONObject getValue(ComponentValueType type) {
		JSONObject object = super.getValue(type);
		switch(type){
		case VALUE:
			object.put("value", area.getText());
			break;
		case SELECTION:
			object.put("value", area.getSelectedText());
		}
		return object;
	}

	@Override
	public void setValue(JSONObject object, ComponentValueType type) {
		area.setText(object.getString("value"));
	}

}