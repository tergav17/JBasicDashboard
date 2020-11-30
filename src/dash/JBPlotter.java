package dash;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class JBPlotter {
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
	private boolean linear = false;
	
	private Point last = null;
	
	private BufferedImage plot = null;
	private Color currColor = Color.green;
	
	public JBPlotter(String name, int x, int y, int width, int height, String xAxis, String yAxis) {
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
		
		clearPlot();
	}
	private GenericManager callback;
	public void setCallback(GenericManager gm) {
		callback = gm;
	}
	public void update() {
		if (callback != null) callback.update();
	}
	
	//Plot functions
	public void resetLine() {
		last = null;
	}
	public void clearPlot() {
		int sizeScalar = Dashboard.sizeScalar;
		Point v1 = new Point((getX() * sizeScalar) + (5 * sizeScalar) + 1, (getY() * sizeScalar) + ((2 + (getHeight() - 5)) * sizeScalar) - 1);
		Point v2 = new Point((getX() * sizeScalar) + ((5 + (getWidth() - 8)) * sizeScalar), (getY() * sizeScalar) + (2 * sizeScalar));
		
		plot = new BufferedImage((int) (v2.getX() - v1.getX()), (int) (v1.getY() - v2.getY()), BufferedImage.TYPE_INT_ARGB);
		
		last = null;
	}
	public void fastPlot(double x, double y) {
		double scaleX = (plot.getWidth() * 1.0) / (xMax - xMin);
		double scaleY = (plot.getHeight() * 1.0) / (yMax - yMin);
		
		Point pos = new Point(((x - xMin) * scaleX), (plot.getHeight() - ((y - yMin) * scaleY)));

		Graphics g = plot.getGraphics();
		g.setColor(currColor);
		
		if (linear) {
			if (last != null) {
				g.drawLine((int) last.getX(), (int) last.getY(), (int) pos.getX(), (int) pos.getY());
			}
		} else {
			g.drawLine((int) pos.getX() - 1, (int) pos.getY() - 1, (int) pos.getX() + 1, (int) pos.getY() + 1);
			g.drawLine((int) pos.getX() - 1, (int) pos.getY() + 1, (int) pos.getX() + 1, (int) pos.getY() - 1);
		}
		
		last = pos;
	}
	
	public void plot(double x, double y) {
		fastPlot(x, y);
		update();
	}
	
	public BufferedImage getPlot() {
		return plot;
	}
	public Color getCurrentColor() {
		return currColor;
	}
	public void setCurrentColor(Color c) {
		currColor = c;
	}
	
	//Getters and setters
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
		update();
	}
	public boolean isLinear() {
		return linear;
	}
	public void setLinear(boolean linear) {
		this.linear = linear;
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
