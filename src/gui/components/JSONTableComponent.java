package gui.components;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.pivot.collections.HashMap;
import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.ApplicationContext;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.apache.pivot.wtk.ScrollPane;
import org.apache.pivot.wtk.TableView;
import org.apache.pivot.wtk.TableViewHeader;
import org.apache.pivot.wtk.ScrollPane.ScrollBarPolicy;
import org.apache.pivot.wtk.TableView.Column;
import org.json.JSONArray;
import org.json.JSONObject;

import gui.JSONController;

public class JSONTableComponent extends JSONComponent {

	private TableView tableView;
	private ArrayList<ComponentType> columnTypes;

	public JSONTableComponent(ScrollPane scrollPane, JSONObject dataObject, JSONObject metaData) {
		super(scrollPane, ComponentType.TABLE, dataObject, metaData);
		
		scrollPane.setPreferredHeight(100);
		scrollPane.setHorizontalScrollBarPolicy(ScrollBarPolicy.FILL);
		scrollPane.setVerticalScrollBarPolicy(ScrollBarPolicy.FILL_TO_CAPACITY);
		scrollPane.setMaximumHeight(200);

		TableView view = new TableView();
		this.tableView = view;
		
		setValue(dataObject);

		columnTypes = new java.util.ArrayList<>();
		for (int i = 0; i < metaData.getJSONArray("columns").length(); i++) {
			JSONObject actionData = metaData.getJSONArray("columns").getJSONObject(i);
			Column column = new Column(actionData.getString("name"));
			column.setHeaderData(actionData.getString("name"));
			view.getColumns().add(column);
			columnTypes.add(ComponentType.valueOf(actionData.getString("type").toUpperCase()));
		}
		TableViewHeader header = new TableViewHeader(view);
		header.getStyles().put("includeTrailingVerticalGridLine", true);
		// header.setTableView(view);

		scrollPane.setColumnHeader(header);

		scrollPane.setVisible(true);
		scrollPane.setEnabled(true);
		scrollPane.setView(view);
		
	}

	@Override
	public JSONObject getValue(ComponentValueType type) throws Exception {

		JSONObject returnObject = super.getValue(type);
		switch (type) {
		case SELECTION:
			if (tableView.getSelectedIndex() > -1) {
				@SuppressWarnings("unchecked")
				Map<String, String> data = (Map<String, String>) tableView.getTableData()
						.get(tableView.getSelectedIndex());
				Iterator<String> iterator = data.iterator();
				JSONObject tableSelection = new JSONObject();
				while (iterator.hasNext()) {
					String key = iterator.next();
					tableSelection.put(key, data.get(key));
				}
				returnObject.put("value",tableSelection);
			} else {
				returnObject.put("value", new JSONObject());
			}
			break;
		case VALUE:
			@SuppressWarnings("unchecked")
			List<Map<String, String>> tableData = (List<Map<String, String>>) tableView.getTableData();
			JSONArray tableArray = new JSONArray();
			Iterator<Map<String, String>> rowIterator = tableData.iterator();
			while (rowIterator.hasNext()) {
				JSONObject rowObject = new JSONObject();
				Map<String, String> mapRow = rowIterator.next();
				Iterator<String> columnIterator = mapRow.iterator();
				while (columnIterator.hasNext()) {
					String key = columnIterator.next();
					rowObject.put(key, mapRow.get(key));
				}
				tableArray.put(rowObject);
			}
			returnObject.put("value", tableArray);
		}
		return returnObject;
	}

	public TableView getTableView() {
		return tableView;
	}

	@Override
	public void setValue(JSONObject data) {
		this.dataObject = data;
		updateValue();
	}
	
	public void updateValue(){

		List<Map<String, String>> tableData = new org.apache.pivot.collections.ArrayList<Map<String, String>>();
		for (int i = 0; i < dataObject.getJSONArray("users").length(); i++) {
			JSONObject rowData = dataObject.getJSONArray("users").getJSONObject(i);
			HashMap<String, String> map = new HashMap<>();

			for (int k = 0; k < metaData.getJSONArray("columns").length(); k++) {
				JSONObject columnData = metaData.getJSONArray("columns").getJSONObject(k);
				map.put(columnData.getString("name"), rowData.get(columnData.get("name").toString()).toString());
			}
			tableData.add(map);
		}
		tableView.setTableData(tableData);
	}
	
	
}
