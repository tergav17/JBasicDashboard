package dash;

public class JBLineDivider {
	private int x1;
	private int y1;
	private int primaryOrientation;
	private int x2;
	private int y2;
	private int secondaryOrientation;
	
	public JBLineDivider(int x1, int y1, int primaryOrientation, int x2, int y2, int secondaryOrientation) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.primaryOrientation = primaryOrientation;
		this.x2 = x2;
		this.y2 = y2;
		this.secondaryOrientation = secondaryOrientation;
	}
	
	public JBLineDivider(int x1, int y1, int x2, int y2) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.primaryOrientation = 0;
		this.x2 = x2;
		this.y2 = y2;
		this.secondaryOrientation = 0;
		
	}
	
	private GenericManager callback;
	public void setCallback(GenericManager gm) {
		callback = gm;
	}
	public void update() {
		if (callback != null) callback.update();
	}
	
	//Getters and setters
	public int getX1() {
		return x1;
	}
	public void setX1(int x1) {
		this.x1 = x1;
		update();
	}
	public int getY1() {
		return y1;
	}
	public void setY1(int y1) {
		this.y1 = y1;
		update();
	}
	public int getPrimaryOrientation() {
		return primaryOrientation;
	}
	public void setPrimaryOrientation(int primaryOrientation) {
		this.primaryOrientation = primaryOrientation;
		update();
	}
	public int getX2() {
		return x2;
	}
	public void setX2(int x2) {
		this.x2 = x2;
		update();
	}
	public int getY2() {
		return y2;
	}
	public void setY2(int y2) {
		this.y2 = y2;
		update();
	}
	public int getSecondaryOrientation() {
		return secondaryOrientation;
	}
	public void setSecondaryOrientation(int secondaryOrientation) {
		this.secondaryOrientation = secondaryOrientation;
		update();
	}
	
	
}
