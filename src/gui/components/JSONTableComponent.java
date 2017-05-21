package gui.components;

import java.util.Iterator;

import org.apache.pivot.collections.List;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.TableView;
import org.json.JSONArray;
import org.json.JSONObject;

public class JSONTableComponent extends JSONComponent {

	private TableView tableView;

	public JSONTableComponent(String name, TableView table, Component pane) {
		super(name, pane);
		this.tableView = table;
	}

	@Override
	public JSONObject getValue(ComponentValueType type) {

		JSONObject returnObject = super.getValue(type);
		switch (type) {
		case SELECTION:
			@SuppressWarnings("unchecked")
			Map<String, String> data = (Map<String, String>) tableView.getTableData().get(tableView.getSelectedIndex());
			Iterator<String> iterator = data.iterator();
			JSONObject tableSelection = new JSONObject();
			while (iterator.hasNext()) {
				String key = iterator.next();
				tableSelection.put(key, data.get(key));
			}
			returnObject.put("value", tableSelection);
			break;
		case CONTENT:
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
	public void setValue(JSONObject object, ComponentValueType type) {
		// TODO Auto-generated method stub

	}

}
