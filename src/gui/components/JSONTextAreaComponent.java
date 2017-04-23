package gui.components;

import org.apache.pivot.wtk.ScrollPane;
import org.apache.pivot.wtk.TextArea;
import org.json.JSONObject;

public class JSONTextAreaComponent extends JSONComponent {

	private TextArea area;
	private ScrollPane scrollPane;
	
	public JSONTextAreaComponent(String name, TextArea area, ScrollPane scrollPane) {
		super(name, scrollPane);
		this.area = area;
		this.scrollPane = scrollPane;
	}

	
	
	@Override
	public JSONObject getValue(ComponentValueType type) {
		// TODO Auto-generated method stub
		JSONObject object = super.getValue(type);
		switch(type){
		case CONTENT:
			object.put("value", area.getText());
			break;
		case SELECTION:
			object.put("value", area.getSelectedText());
		}
		return object;
	}



	@Override
	public void setValue(JSONObject object) {
		// TODO Auto-generated method stub

	}

}
