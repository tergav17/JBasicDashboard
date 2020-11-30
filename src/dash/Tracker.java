package dash;

import java.awt.Color;

public class Tracker {
	Double value;
	Color color;
	
	public Tracker(Double value, Color color) {
		super();
		this.value = value;
		this.color = color;
	}
	
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Color getColor() {
		return color;
	}
	public void setColor(Color color) {
		this.color = color;
	}
	
	
}
