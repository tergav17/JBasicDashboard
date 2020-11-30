package dash;

import java.awt.Color;
import java.util.ArrayList;

public class Dataset {
	private Color color;
	private ArrayList<Point> points = new ArrayList<Point>();
	
	public Dataset(Color c) {
		this.color = c;
	}
	
	public void add(Point p) {
		points.add(p);
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public ArrayList<Point> getPoints() {
		return points;
	}

	public void setPoints(ArrayList<Point> points) {
		this.points = points;
	}

	
	
}
