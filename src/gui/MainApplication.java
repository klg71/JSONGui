package gui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.Consumer;

import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;
import org.json.JSONObject;

public class MainApplication implements Application, Context {
	private Window window;
	private JSONController jsonController;
	private Display display;

	@Override
	public void resume() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean shutdown(boolean arg0) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void startup(Display disp, Map<String, String> arg1) throws Exception {

		jsonController = new JSONController();
		
		window = new Window();
		this.display = disp;
		
		
        JSONObject received = jsonController.getStartPage();
		
		reloadWindow(received);

		
		
	}
	
	private void reloadWindow(JSONObject received){
		JSONObject dataObject = received.getJSONObject("data");
		JSONObject metaDataObject = received.getJSONObject("meta");
		System.out.println(received.toString());
		MainPane mainPane = Renderer.render(dataObject, metaDataObject);
		mainPane.addContext(this);
		
		window.setContent(mainPane);
		
		window.setTitle(metaDataObject.getString("title"));
		
		window.open(display);
		
		window.setMaximized(true);
	}

	@Override
	public void suspend() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(JSONObject action) {
		try {
			reloadWindow(jsonController.act(action));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void navigationPerformed(JSONObject navigation) {
		try {
			reloadWindow(jsonController.navigate(navigation));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
}
