package dash;

public class JBTextLabel {
	private String content;
	private int x;
	private int y;
	private int width;
	private int height;
	private boolean centered;
	private boolean boxed;
	
	public JBTextLabel(String content, int x, int y, int width, int height, boolean centered) {
		super();
		this.content = content;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.centered = centered;
		boxed = false;
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
	public boolean isBoxed() {
		return boxed;
	}
	public void setBoxed(boolean boxed) {
		this.boxed = boxed;
		update();
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
