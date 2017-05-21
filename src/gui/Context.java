package gui;

import org.json.JSONObject;

public interface Context {
	public void actionPerformed(JSONObject action);
	public void navigationPerformed(JSONObject navigation);
}
