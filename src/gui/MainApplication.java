package gui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.function.Consumer;

import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Window;
import org.json.JSONObject;

public class MainApplication implements Application {
	private Window window;

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

		
		
		window = new Window();
		
		URL yahoo = new URL("http://127.0.0.1:8001");
        URLConnection yc = yahoo.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                yc.getInputStream()));
        StringBuilder result = new StringBuilder();
        in.lines().forEach(new Consumer<String>() {

			@Override
			public void accept(String t) {
				result.append(t);
			}
		});
        JSONObject received = new JSONObject(result.toString());
		
		

		JSONObject dataObject = received.getJSONObject("data");
		JSONObject metaDataObject = received.getJSONObject("meta");
		System.out.println(received.toString());
		MainPane mainPane = Renderer.render(dataObject, metaDataObject);
		window.setContent(mainPane);
		
		window.setTitle(metaDataObject.getString("title"));
		
		window.open(disp);
		
		window.setMaximized(true);
		
	}

	@Override
	public void suspend() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
