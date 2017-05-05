package gui.components;

import java.util.Iterator;

import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.TableView;
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
		switch(type){
		case SELECTION:
			@SuppressWarnings("unchecked") Map<String, String> data = (Map<String, String>) tableView.getTableData().get(tableView.getSelectedIndex());
			Iterator<String> iterator = data.iterator();
			JSONObject tableSelection = new JSONObject();
			while(iterator.hasNext()){
				String key = iterator.next();
				tableSelection.put(key, data.get(key));
			}
			returnObject.put("value", tableSelection);
			break;
		case CONTENT:
			break;
		default:
			break;
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
