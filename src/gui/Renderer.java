package gui;

import java.awt.Font;

import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Button;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.FillPane;
import org.apache.pivot.wtk.FlowPane;
import org.apache.pivot.wtk.HorizontalAlignment;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.ListView;
import org.apache.pivot.wtk.Orientation;
import org.apache.pivot.wtk.PushButton;
import org.apache.pivot.wtk.ScrollPane;
import org.apache.pivot.wtk.TextArea;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.VerticalAlignment;
import org.apache.pivot.wtk.ScrollPane.ScrollBarPolicy;
import org.json.JSONObject;

import gui.components.JSONComponent;
import gui.components.JSONListComponent;
import gui.components.JSONTextAreaComponent;
import gui.components.JSONTextComponent;

public class Renderer {



	private static int componentWidth=200;
	
	public static int getMaxLabelWidth(JSONObject metaObject){
		int length = 0;
		for (int i = 0; i < metaObject.getJSONArray("data").length(); i++) {
			JSONObject metaData = metaObject.getJSONArray("data").getJSONObject(i);
			if(metaData.getString("displayname").length()>length){
				length = metaData.getString("displayname").length();
			}
		}
		return length;
	}

	
	public static MainPane render(JSONObject dataObject, JSONObject metaObject) {
		
		int maxLabelWidth = getMaxLabelWidth(metaObject);
		ComponentController controller = new ComponentController();
		ActionController actionController = new ActionController();
		MainPane mainPane = new MainPane(Orientation.VERTICAL,controller,actionController);
		BoxPane componenPanel = new BoxPane(Orientation.VERTICAL);
		
		for (int i = 0; i < metaObject.getJSONArray("data").length(); i++) {
			JSONObject metaData = metaObject.getJSONArray("data").getJSONObject(i);
			componenPanel.add(buildDataPanel(dataObject, metaData,maxLabelWidth,controller));
			componenPanel.setVisible(true);
		}
		
		componenPanel.setVisible(true);
		componenPanel.setEnabled(true);
		
		componenPanel.getStyles().put("horizontalAlignment", HorizontalAlignment.CENTER);
		componenPanel.getStyles().put("verticalAlignment", VerticalAlignment.CENTER);
		componenPanel.getStyles().put("spacing","10");
		
		FlowPane actionPane = new FlowPane();
		
		for (int i = 0; i < metaObject.getJSONArray("actions").length(); i++) {
			JSONObject actionData = metaObject.getJSONArray("actions").getJSONObject(i);
			PushButton button = new PushButton(actionData.getString("displayname"));
			actionController.addAction(actionData.getString("name"), button);
			actionPane.add(button);
		}
		actionPane.setVisible(true);
		actionPane.setEnabled(true);
		
		actionPane.getStyles().put("alignment", HorizontalAlignment.CENTER);
		
		
		mainPane.setVisible(true);
		mainPane.setEnabled(true);
		mainPane.add(componenPanel);
		mainPane.add(actionPane);
		return mainPane;
	}
	
	public static BoxPane buildDataPanel(JSONObject dataObject, JSONObject metaData, int maxLabelWidth, ComponentController controller) {
		BoxPane dataPanel = new BoxPane(Orientation.HORIZONTAL);
		dataPanel.setVisible(true);
		Label label = new Label(metaData.getString("displayname"));
		Font font = (Font) label.getStyles().get("font");
		label.setMinimumWidth((int) (maxLabelWidth*(font.getSize2D()*0.7)));
		dataPanel.add(label);
		JSONComponent component = null;
		if (metaData.getString("type").equals("String")) {
			component = buildDataTextfield(dataObject, metaData);
		}
		if (metaData.getString("type").equals("List")) {
			component = buildDataList(dataObject, metaData);
		}
		
		controller.addComponent(metaData.getString("name"), component);
		dataPanel.add(component.getComponent());
		
		dataPanel.getStyles().put("fill", "true");
		dataPanel.setVisible(true);
		dataPanel.setEnabled(true);
		return dataPanel;

	}

	public static JSONComponent buildDataTextfield(JSONObject dataObject, JSONObject metaData) {
		String text = dataObject.getString(metaData.getString("name"));
		if(text.length()>50){
			return buildTextArea(dataObject, metaData);
		}else {
			return buildTextinput(dataObject, metaData);
		}
	}
	
	public static JSONTextAreaComponent buildTextArea(JSONObject dataObject, JSONObject metaData) {
		String text = dataObject.getString(metaData.getString("name"));
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setPreferredHeight(200);
		scrollPane.setHorizontalScrollBarPolicy(ScrollBarPolicy.FILL);
		scrollPane.setVerticalScrollBarPolicy(ScrollBarPolicy.FILL_TO_CAPACITY);
		scrollPane.setMaximumWidth(componentWidth);
		scrollPane.setMaximumHeight(100);
		
		TextArea area = new TextArea();
		area.setText(text);
		JSONTextAreaComponent areaComponent = new JSONTextAreaComponent(metaData.getString("name"), area, scrollPane);
		
		scrollPane.setVisible(true);
		scrollPane.setEnabled(true);
		scrollPane.setView(area);
		
		return areaComponent;
	}
	
	public static JSONTextComponent buildTextinput(JSONObject dataObject, JSONObject metaData) {
		TextInput input = new TextInput();
		input.setPreferredWidth(componentWidth);
		input.setText(dataObject.getString(metaData.getString("name")));
		JSONTextComponent textComponent = new JSONTextComponent(metaData.getString("name"), input);
		return textComponent;
	}

	public static JSONComponent buildDataList(JSONObject dataObject, JSONObject metaData) {
		if (metaData.getString("entry_type").equals("String")) {
			return buildDataStringList(dataObject, metaData);
		}
		return null;
	}

	public static JSONComponent buildDataStringList(JSONObject dataObject, JSONObject metaData) {
		org.apache.pivot.collections.List<String> stringList = new org.apache.pivot.collections.ArrayList<>();
		ListView listField = new ListView();
		
		for (int k = 0; k < dataObject.getJSONArray(metaData.getString("name")).length(); k++) {
			stringList.add(dataObject.getJSONArray(metaData.getString("name")).getString(k));
		}
		listField.setPreferredWidth(componentWidth);
		listField.setListData(stringList);
		JSONListComponent listComponent = new JSONListComponent(metaData.getString("name"), listField);
		return listComponent;

	}
}
