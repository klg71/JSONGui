package gui;

import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import gui.components.ComponentValueType;

public interface PaneListener {
	public void actionPerformed(String name, Map<String, ComponentValueType> fields);
	public void navigationPerformed(String name);
}
