package gui.components;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.DesktopApplicationContext;
import org.json.JSONObject;

import gui.JSONController;

public abstract class JSONComponent {
	protected String name;
	protected Component component;
	protected ComponentType componentType;
	protected JSONObject dataObject;
	protected JSONObject metaData;
	protected Timer updateTimer;

	public JSONComponent(Component component, ComponentType type, JSONObject dataObject, JSONObject metaData) {
		super();
		this.name = metaData.getString("name");
		this.component = component;
		this.componentType = type;
		this.dataObject = dataObject;
		this.metaData = metaData;
		this.createUpdateTimer();
	}

	public JSONObject getValue(ComponentValueType type) throws Exception {
		JSONObject object = new JSONObject();
		object.put("name", name);
		return object;
	}

	public abstract void setValue(JSONObject object);

	public abstract void updateValue();

	public String getName() {
		return name;
	}

	public Component getComponent() {
		return component;
	}

	protected void createUpdateTimer() {
		JSONComponent thisCom = this;
		if (this.metaData.has("update")) {
			Integer timeout = this.metaData.getInt("update");
			updateTimer = new Timer();
			updateTimer.schedule(new TimerTask() {

				@Override
				public void run() {
					JSONObject action = new JSONObject();
					action.put("field", metaData.get("name"));
					action.put("name", "update");
					JSONObject received = null;
					try {
						received = JSONController.getInstance().act(action);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					thisCom.dataObject = received.getJSONObject("data");

					DesktopApplicationContext.queueCallback(new Runnable() {

						@Override
						public void run() {
							thisCom.updateValue();
						}
					});
				}
			}, timeout, timeout);
		}

	}

	public void stopUpdate() {
		if (updateTimer != null) {
			updateTimer.cancel();
			updateTimer.purge();
		}
	}

}
