package gui;

import org.apache.pivot.wtk.BoxPane;
import org.apache.pivot.wtk.Component;
import org.apache.pivot.wtk.ComponentKeyListener;
import org.apache.pivot.wtk.Dialog;
import org.apache.pivot.wtk.DialogCloseListener;
import org.apache.pivot.wtk.Display;
import org.apache.pivot.wtk.Orientation;
import org.apache.pivot.wtk.TextInput;
import org.apache.pivot.wtk.Window;
import org.apache.pivot.wtk.Keyboard.KeyCode;
import org.apache.pivot.wtk.Keyboard.KeyLocation;
import gui.components.ComponentType;


public class SimpleDialog extends Dialog implements ComponentKeyListener {

	private String title;
	private TextInput input;
	private Callback callback;
	private ComponentType type;

	public interface Callback{
		public void addEntry(String entry);
	}
	
	public SimpleDialog(String title, Callback callback, ComponentType type) {
		this.title = title;
		this.callback = callback;
		this.type = type;
		this.setModal(true);
	}

	

	@Override
	public void open(Display display, Window owner, DialogCloseListener dialogCloseListenerArgument) {
		BoxPane mainPane = new BoxPane(Orientation.VERTICAL);
		input = new TextInput();
		input.setValidator(new TypeValidator(type));
		input.getComponentKeyListeners().add(this);
		mainPane.add(input);
		this.setTitle(title);
		this.setContent(mainPane);
		super.open(display, owner, dialogCloseListenerArgument);
	}
	

	@Override
	public void close(boolean arg0) {
		super.close(arg0);
	}



	public String getResultText() {
		return input.getText();
	}



	@Override
	public boolean keyPressed(Component arg0, int arg1, KeyLocation arg2) {
		if (arg1==KeyCode.ENTER){
			if(input.isTextValid()){
				callback.addEntry(getResultText());
			} else {
				 new org.apache.pivot.wtk.Alert("Please enter a valid String").open(this.getWindow());
				 return true;
			}
		}
		return false;
	}



	@Override
	public boolean keyTyped(Component arg0, char arg1) {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean keyReleased(Component arg0, int arg1, KeyLocation arg2) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	

	
}
