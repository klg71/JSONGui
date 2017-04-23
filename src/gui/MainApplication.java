package gui;

import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JTextArea;
import org.apache.pivot.collections.Map;
import org.apache.pivot.wtk.Application;
import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.FillPane;
import org.apache.pivot.wtk.HorizontalAlignment;
import org.apache.pivot.wtk.Label;
import org.apache.pivot.wtk.ListView;
import org.apache.pivot.wtk.Orientation;
import org.apache.pivot.wtk.Panel;
import org.apache.pivot.wtk.ScrollPane;
import org.apache.pivot.wtk.ScrollPane.ScrollBarPolicy;
import org.apache.pivot.wtk.TextArea;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.VerticalAlignment;
import org.apache.pivot.wtk.Window;
import org.json.JSONObject;

import gui.components.ComponentValueType;

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

		StringBuilder data = new StringBuilder();
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("test.json"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			data.append(scanner.nextLine() + "\r\n");
		}
		StringBuilder metaData = new StringBuilder();
		scanner = null;
		try {
			scanner = new Scanner(new File("test_meta.json"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		while (scanner.hasNextLine()) {
			metaData.append(scanner.nextLine() + "\r\n");
		}
		

		JSONObject dataObject = new JSONObject(data.toString());
		JSONObject metaDataObject = new JSONObject(metaData.toString());
		MainPane mainPane = Renderer.render(dataObject, metaDataObject);
		window.setContent(mainPane);
		
		window.open(disp);
		
		window.setMaximized(true);
		
		System.out.println(mainPane.getComponentController().getSingleResult("firstname", ComponentValueType.CONTENT).toString());
	}

	@Override
	public void suspend() throws Exception {
		// TODO Auto-generated method stub
		
	}
}
