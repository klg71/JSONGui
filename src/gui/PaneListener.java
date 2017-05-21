package gui;

import java.util.List;

import org.json.JSONObject;

public interface PaneListener {
	public void actionPerformed(String name, List<String> fields);
	public void navigationPerformed(String name);
}
