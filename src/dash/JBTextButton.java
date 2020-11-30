package dash;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JBTextButton {
	private ActionListener event;
	private String label;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean isSelected = false;
	
	public JBTextButton(ActionListener event, String label, int x, int y, int width, int height) {
		super();
		this.event = event;
		this.label = label;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	//This function is called when the button is pressed
	public void pressButton() {
		ActionEvent aEvent = new ActionEvent(this, 0, "Pressed");
		if (event != null) event.actionPerformed(aEvent);
	}
	
	private GenericManager callback;
	public void setCallback(GenericManager gm) {
		callback = gm;
	}
	public void update() {
		if (callback != null) callback.update();
	}
	
	//Getters and setters
	public ActionListener getEvent() {
		return event;
	}
	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public void setEvent(ActionListener event) {
		this.event = event;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
		update();
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
		update();
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
		update();
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
		update();
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
		update();
	}
	
	
}
