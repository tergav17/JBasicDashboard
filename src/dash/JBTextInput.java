package dash;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JBTextInput {
	private ActionListener event;
	private String content;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean isSelected = false;
	private boolean isFocused = false;

	public JBTextInput(ActionListener event, String content, int x, int y, int width, int height) {
		this.event = event;
		this.content = content;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}


	
	//This function is called when the button is pressed
	public void enterString(String s) {
		ActionEvent aEvent = new ActionEvent(this, 0, s);
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

	public boolean isFocused() {
		return isFocused;
	}

	public void setFocused(boolean focused) {
		isFocused = focused;
	}

	public void setEvent(ActionListener event) {
		this.event = event;
	}
	public String getContent() {
		return content;
	}

	public String getVisibleContent() {
		String currCon = content + (isFocused ? "_" : "");;

		while (callback.getStringWidth(currCon) > (width * callback.getSizeScalar())) {
			currCon = currCon.substring(1);
		}

		return currCon;
	}

	public void setContent(String content) {
		this.content = content;
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
