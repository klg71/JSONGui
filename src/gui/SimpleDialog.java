package gui;

import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Dialog;
import org.apache.pivot.wtk.DialogCloseListener;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Orientation;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.Window;


public class SimpleDialog extends Dialog {

	private String title;
	private TextInput input;
	private Callback callback;

	public interface Callback{
		public void addEntry(String entry);
	}
	
	public SimpleDialog(String title, Callback callback) {
		this.title = title;
		this.callback = callback;
		this.setModal(true);
	}

	

	@Override
	public void open(Display display, Window owner, DialogCloseListener dialogCloseListenerArgument) {
		BoxPane mainPane = new BoxPane(Orientation.VERTICAL);
		input = new TextInput();
		mainPane.add(input);
		this.setTitle(title);
		this.setContent(mainPane);
		super.open(display, owner, dialogCloseListenerArgument);
	}
	

	@Override
	public void close(boolean arg0) {
		callback.addEntry(getResultText());
		super.close(arg0);
	}



	public String getResultText() {
		return input.getText();
	}
	
	
	
	

	
}
