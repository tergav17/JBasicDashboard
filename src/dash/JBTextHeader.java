package dash;

public class JBTextHeader {
	private String content;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean centered;
	private int size;
	
	public JBTextHeader(String content, int x, int y, int width, int height, boolean centered, int size) {
		super();
		this.content = content;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.centered = centered;
		this.size = size;
	}
	

	private GenericManager callback;
	public void setCallback(GenericManager gm) {
		callback = gm;
	}
	public void update() {
		if (callback != null) callback.update();
	}
	
	//Getters and setters and such
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
		update();
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
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
	public boolean isCentered() {
		return centered;
	}
	public void setCentered(boolean centered) {
		this.centered = centered;
		update();
	}
	
	
}
