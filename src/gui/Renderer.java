package gui;

import java.awt.Font;

import org.apache.pivot.collections.ArrayList;
import org.apache.pivot.collections.HashMap;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Border;
import org.apache.pivot.wtk.BoxPane;
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
import org.apache.pivot.wtk.Separator;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TableView.Column;
import org.apache.pivot.wtk.validation.Validator;
import org.apache.pivot.wtk.TableViewHeader;
import org.json.JSONArray;
import org.json.JSONObject;

import gui.components.ComponentType;
import gui.components.ComponentValueType;
import gui.components.JSONComponent;
import gui.components.JSONListComponent;
import gui.components.JSONTableComponent;
import gui.components.JSONTextAreaComponent;
import gui.components.JSONTextComponent;

public class Renderer {

	public static final int componentWidth = 200;

	public static int getMaxLabelWidth(JSONArray metaObject) {
		int length = 0;
		for (int i = 0; i < metaObject.length(); i++) {
			JSONObject metaData = metaObject.getJSONObject(i);
			if (metaData.getString("displayname").length() > length) {
				length = metaData.getString("displayname").length();
			}
		}
		return length;
	}

	public static MainPane render(JSONObject dataObject, JSONObject metaObject) {

		ComponentController controller = new ComponentController();
		ActionController actionController = new ActionController();
		NavigationController navigationController = new NavigationController();
		MainPane mainPane = new MainPane(Orientation.HORIZONTAL, controller, actionController, navigationController);
		mainPane.setSplitRatio(0.2f);
		// mainPane.setLocked(true);
		BoxPane navigationPane = new BoxPane(Orientation.VERTICAL);
		Separator navigation = new Separator("Navigation");
		navigation.getStyles().put("font","{size:14, bold:true}");
		navigationPane.add(navigation);
		for (int i = 0; i < metaObject.getJSONArray("navigation").length(); i++) {
			JSONObject metaData = metaObject.getJSONArray("navigation").getJSONObject(i);
			PushButton navigationButton = new PushButton(metaData.getString("displayname"));
			navigationController.addAction(metaData.getString("name"), navigationButton);
			navigationPane.add(navigationButton);
		}
		navigationPane.getStyles().put("horizontalAlignment", HorizontalAlignment.LEFT);
		navigationPane.getStyles().put("verticalAlignment", VerticalAlignment.CENTER);
		navigationPane.getStyles().put("padding", "20");

		mainPane.setLeft(new Border(navigationPane));

		BoxPane contentPane = new BoxPane(Orientation.VERTICAL);
		Separator title = new Separator(metaObject.getString("title"));
		title.getStyles().put("font","{size:14, bold:true}");
		contentPane.add(title);
		contentPane.add(buildComponentPane(dataObject, metaObject.getJSONArray("data"), controller));

		contentPane.add(buildActionPane(dataObject, metaObject.getJSONArray("actions"), actionController));

		contentPane.getStyles().put("horizontalAlignment", HorizontalAlignment.CENTER);
		contentPane.getStyles().put("verticalAlignment", VerticalAlignment.CENTER);
		mainPane.setRight(contentPane);

		return mainPane;
	}

	public static BoxPane buildComponentPane(JSONObject dataObject, JSONArray metaObject,
			ComponentController controller) {
		int maxLabelWidth = getMaxLabelWidth(metaObject);
		BoxPane componenPanel = new BoxPane(Orientation.VERTICAL);

		for (int i = 0; i < metaObject.length(); i++) {
			JSONObject metaData = metaObject.getJSONObject(i);
			componenPanel.add(buildDataPanel(dataObject, metaData, maxLabelWidth, controller));
			componenPanel.setVisible(true);
		}

		componenPanel.setVisible(true);
		componenPanel.setEnabled(true);

		componenPanel.getStyles().put("horizontalAlignment", HorizontalAlignment.CENTER);
		componenPanel.getStyles().put("verticalAlignment", VerticalAlignment.CENTER);
		componenPanel.getStyles().put("spacing", "10");
		return componenPanel;
	}

	public static FlowPane buildActionPane(JSONObject dataObject, JSONArray metaObject, ActionController controller) {

		FlowPane actionPane = new FlowPane();

		for (int i = 0; i < metaObject.length(); i++) {
			JSONObject actionData = metaObject.getJSONObject(i);
			PushButton button = new PushButton(actionData.getString("displayname"));
			java.util.HashMap<String, ComponentValueType> fields = new java.util.HashMap<>();
			for (int k = 0; k < actionData.getJSONArray("fields").length(); k++) {
				JSONObject field = actionData.getJSONArray("fields").getJSONObject(k);
				fields.put(field.getString("name"), ComponentValueType.findByJSONName(field.getString("type")));
			}
			controller.addAction(actionData.getString("name"), button, fields);
			actionPane.add(button);
		}
		actionPane.setVisible(true);
		actionPane.setEnabled(true);

		actionPane.getStyles().put("alignment", HorizontalAlignment.CENTER);
		return actionPane;
	}

	public static BoxPane buildDataPanel(JSONObject dataObject, JSONObject metaData, int maxLabelWidth,
			ComponentController controller) {
		BoxPane dataPanel = new BoxPane(Orientation.HORIZONTAL);
		dataPanel.setVisible(true);
		Label label = new Label(metaData.getString("displayname"));
		Font font = (Font) label.getStyles().get("font");
		label.setMinimumWidth((int) (maxLabelWidth * (font.getSize2D() * 0.7)));
		JSONComponent component = null;
		if (metaData.getString("type").equals("String") || metaData.getString("type").equals("Integer")
				|| metaData.getString("type").equals("Double")) {
			component = buildDataTextfield(dataObject, metaData);
		}
		if (metaData.getString("type").equals("List")) {
			component = buildDataList(dataObject, metaData);
		}
		if (metaData.getString("type").equals("table")) {
			component = buildDataTable(dataObject, metaData);
			dataPanel.setOrientation(Orientation.VERTICAL);
		}

		controller.addComponent(metaData.getString("name"), component);
		if (component != null) {
			dataPanel.add(label);
			dataPanel.add(component.getComponent());
		}
		dataPanel.getStyles().put("fill", "true");
		dataPanel.setVisible(true);
		dataPanel.setEnabled(true);
		return dataPanel;

	}

	private static JSONTableComponent buildDataTable(JSONObject dataObject, JSONObject metaData) {
		ScrollPane scrollPane = new ScrollPane();
		JSONTableComponent tableComponent = new JSONTableComponent(scrollPane, dataObject, metaData);
		return tableComponent;
	}

	public static JSONComponent buildDataTextfield(JSONObject dataObject, JSONObject metaData) {
		String text = dataObject.get(metaData.getString("name")).toString();
		if (text.length() > 50) {
			return buildTextArea(dataObject, metaData);
		} else {
			return buildTextinput(dataObject, metaData);
		}
	}

	public static JSONTextAreaComponent buildTextArea(JSONObject dataObject, JSONObject metaData) {
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setMaximumWidth(componentWidth);
		JSONTextAreaComponent areaComponent = new JSONTextAreaComponent(scrollPane,
				ComponentType.valueOf(metaData.getString("type").toUpperCase()), dataObject, metaData);
		return areaComponent;
	}

	public static JSONTextComponent buildTextinput(JSONObject dataObject, JSONObject metaData) {
		TextInput input = new TextInput();

		input.setPreferredWidth(componentWidth);

		JSONTextComponent textComponent = new JSONTextComponent(input,
				ComponentType.valueOf(metaData.getString("type").toUpperCase()), dataObject, metaData);
		return textComponent;
	}

	public static JSONComponent buildDataList(JSONObject dataObject, JSONObject metaData) {

		ListView listField = new ListView();
		JSONListComponent listComponent = new JSONListComponent(listField,
				ComponentType.valueOf(metaData.getString("entry_type").toUpperCase()), dataObject, metaData);
		return listComponent;
	}
}
