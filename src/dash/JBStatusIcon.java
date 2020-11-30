package dash;

public class JBStatusIcon {
	public enum State {
		INACTIVE,
		NOMINAL,
		WARNING,
		ERROR,
		NOTICE
	}
	
	private State state;
	private int x;
	private int y;
	
	public JBStatusIcon(State state, int x, int y) {
		super();
		this.state = state;
		this.x = x;
		this.y = y;
	}
	
	private GenericManager callback;
	public void setCallback(GenericManager gm) {
		callback = gm;
	}
	public void update() {
		if (callback != null) callback.update();
	}
	
	//Getters and setters
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
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
	
	
}
