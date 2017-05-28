package gui.components;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.validation.Validator;
import org.json.JSONObject;

import gui.TypeValidator;

public class JSONTextComponent extends JSONComponent {

	private TextInput component;

	public JSONTextComponent(TextInput component, ComponentType type, JSONObject dataObject,
			JSONObject metaData) {
		super(component, type, dataObject, metaData);
		this.component = component;
		updateValue();
		component.setValidator(new TypeValidator(type));

	}

	@Override
	public JSONObject getValue(ComponentValueType type) throws Exception {
		JSONObject object = new JSONObject(type);
		object.put("name", name);
		if(!component.isTextValid()){
			 new org.apache.pivot.wtk.Alert("Please enter a valid String").open(component.getWindow());
			 throw new Exception("Invalid input");
		}
		
		switch (type) {
		case VALUE:
			switch (componentType) {
			case DOUBLE:
				object.put("value", Float.parseFloat(component.getText().replace(",", ".")));
				break;
			case INTEGER:
				object.put("value", Integer.parseInt(component.getText()));
				break;

			case STRING:
				object.put("value", component.getText());
				break;
			}
			break;
		case SELECTION:
			object.put("value", component.getSelectedText());
			break;
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

		switch (componentType) {
		case STRING:
			component.setText(dataObject.getString(metaData.getString("name")));
			break;
		case DOUBLE:
			Double value = dataObject.getDouble(metaData.getString("name"));
			component.setText(String.format("%." + Integer.toString(metaData.getInt("precision")) + "f", value));
			break;
		case INTEGER:
			component.setText(Integer.toString(dataObject.getInt(metaData.getString("name"))));
			break;
		}

		if (metaData.has("editable")) {
			if (!metaData.getBoolean("editable")) {
				component.setEditable(false);
			}
		}
		
	}

}
