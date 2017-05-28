package gui.components;

import org.apache.pivot.wtk.ScrollPane;
import org.apache.pivot.wtk.TextArea;
import org.apache.pivot.wtk.ScrollPane.ScrollBarPolicy;
import org.json.JSONObject;

public class JSONTextAreaComponent extends JSONComponent {

	private TextArea area;
	public JSONTextAreaComponent(ScrollPane scrollPane, ComponentType type, JSONObject dataObject, JSONObject metaData) {
		super(scrollPane, type,dataObject, metaData);
		
		scrollPane.setPreferredHeight(200);
		scrollPane.setHorizontalScrollBarPolicy(ScrollBarPolicy.FILL);
		scrollPane.setVerticalScrollBarPolicy(ScrollBarPolicy.FILL_TO_CAPACITY);
		scrollPane.setMaximumHeight(100);

		String text = dataObject.getString(metaData.get("name").toString());
		TextArea area = new TextArea();
		area.setText(text);

		scrollPane.setVisible(true);
		scrollPane.setEnabled(true);
		scrollPane.setView(area);
		
		this.area = area;
	}

	@Override
	public JSONObject getValue(ComponentValueType type) throws Exception {
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
	public void setValue(JSONObject data) {
		this.dataObject = data;
		updateValue();
	}

	@Override
	public void updateValue() {
		area.setText(dataObject.getString(metaData.get("name").toString()));
		
	}

}