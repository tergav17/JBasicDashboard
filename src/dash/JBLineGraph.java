package dash;

import java.util.ArrayList;

public class JBLineGraph {
	private ArrayList<Dataset> data = new ArrayList<Dataset>();
	private String name;
	private int x;
	private int y;
	private int width;
	private int height;
	private String xAxis;
	private String yAxis;
	private double xMin;
	private double xMax;
	private double yMin;
	private double yMax;
	
	
	
	public JBLineGraph(String name, int x, int y, int width, int height, String xAxis, String yAxis) {
		super();
		this.name = name;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		
		xMin = 0.0;
		xMax = 1.0;
		yMin = 0.0;
		yMax = 1.0;
	}
	private GenericManager callback;
	public void setCallback(GenericManager gm) {
		callback = gm;
	}
	public void update() {
		if (callback != null) callback.update();
	}
	public void addDataset(Dataset d) {
		data.add(d);
		update();
	}
	public void removeDataset(Dataset d) {
		data.remove(d);
		update();
	}
	
	
	//Getters and setters
	public ArrayList<Dataset> getData() {
		return data;
	}
	public void setData(ArrayList<Dataset> data) {
		this.data = data;
		update();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getXAxis() {
		return xAxis;
	}
	public void setXAxis(String xAxis) {
		this.xAxis = xAxis;
		update();
	}
	public String getYAxis() {
		return yAxis;
	}
	public void setYAxis(String yAxis) {
		this.yAxis = yAxis;
		update();
	}
	public double getXMin() {
		return xMin;
	}
	public void setXMin(double xMin) {
		this.xMin = xMin;
		update();
	}
	public double getXMax() {
		return xMax;
	}
	public void setXMax(double xMax) {
		this.xMax = xMax;
		update();
	}
	public double getYMin() {
		return yMin;
	}
	public void setYMin(double yMin) {
		this.yMin = yMin;
		update();
	}
	public double getYMax() {
		return yMax;
	}
	public void setYMax(double yMax) {
		this.yMax = yMax;
		update();
	}
	
}
