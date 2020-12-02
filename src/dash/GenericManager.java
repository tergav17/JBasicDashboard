package dash;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GenericManager {
	
	//Dashboard display objects
	private JPanel panel;
	private Graphics2D canvas;
	private Font dashboardFont;

	//Dashboard input objects
	private DashboardMouseAdapter mouse;
	private DashboardKeyboardAdapter key;
	
	//Dashboard content
	private ArrayList<Object> content = new ArrayList<Object>();

	//Dashboard attributes
	private int sizeScalar = 0;
	private int width = 0;
	private int height = 0;
	
	//Gets the size scalar, just for fun
	public int getSizeScalar() {return sizeScalar;}

	//If the frame has been disposed, this flag will stop all times events
	public boolean isFrameDisposed = false;
	
	//GraphDrawOps for use
	private GraphDrawOps gdo;
	
	//Generic dashboard manager constructor
	protected GenericManager(JPanel panel, int sizeScalar, int width, int height) {
		this.panel = panel;
		this.width = width;
		this.height = height;
		this.sizeScalar = sizeScalar;

		//Set panel state
		panel.setFocusable(true);

		//Set panel layout
		GridLayout layout = new GridLayout(1, 1);
		panel.setLayout(layout);
		
		//Set up mouse adapter
		mouse = new DashboardMouseAdapter(this);
		panel.addMouseListener(mouse);
		panel.addMouseMotionListener(mouse);

		//Set up keyboard adapter
		key = new DashboardKeyboardAdapter(this);
		panel.addKeyListener(key);
		
		//Set up background
		BufferedImage tile = new BufferedImage(sizeScalar*width, sizeScalar*height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gTile = tile.createGraphics();
		gTile.setColor(Color.BLACK);
		gTile.fillRect(0, 0, sizeScalar*width, sizeScalar*height);
		
		dashboardFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);
		gTile.setFont(dashboardFont);
		
		this.canvas = gTile;
		JLabel background = new JLabel(new ImageIcon(tile));
		
		//Add background to panel
		panel.add(background);
		
		//Load up some images
		this.error = ResourceLoader.getImage("/error.png");
		this.inactive = ResourceLoader.getImage("/inactive.png");
		this.nominal = ResourceLoader.getImage("/nominal.png");
		this.notice = ResourceLoader.getImage("/notice.png");
		this.warning = ResourceLoader.getImage("/warning.png");
		
		//Load GraphDrawOps
		gdo = new GraphDrawOps();
		
	}

	//Draws an object onto the canvas
	protected void drawObject(Object o) {
		if (o instanceof JBTextLabel) {
			//Handle the drawing of a TextLabel
			JBTextLabel tl = (JBTextLabel) o;

			canvas.setColor(Color.LIGHT_GRAY);
			drawTextElement(tl.getContent(), tl.getX(), tl.getY(), tl.getWidth(), tl.getHeight(), tl.isCentered(), tl.isBoxed());

		} else if (o instanceof JBTextButton) {
			//Handle the drawing of a TextButton
			JBTextButton tb = (JBTextButton) o;

			if (tb.isSelected()) {
				//Draw as selected
				canvas.setColor(Color.LIGHT_GRAY);
				canvas.fillRect(tb.getX() * sizeScalar, tb.getY() * sizeScalar, tb.getWidth() * sizeScalar, tb.getHeight() * sizeScalar);

				canvas.setColor(Color.BLACK);
				drawTextElement(tb.getLabel(), tb.getX(), tb.getY(), tb.getWidth(), tb.getHeight(), true, false);
			} else {
				//Draw as unselected
				canvas.setColor(Color.LIGHT_GRAY);
				drawTextElement(tb.getLabel(), tb.getX(), tb.getY(), tb.getWidth(), tb.getHeight(), true, true);
			}
		} else if (o instanceof JBStatusIcon) {
			//Handle the drawing of a StatusIcon
			JBStatusIcon si = (JBStatusIcon) o;

			//Draw status symbol
			switch (si.getState()) {
				case INACTIVE:
					canvas.drawImage(inactive, (si.getX() * sizeScalar), (si.getY() * sizeScalar), sizeScalar, sizeScalar, null);
					break;

				case NOMINAL:
					canvas.drawImage(nominal, (si.getX() * sizeScalar), (si.getY() * sizeScalar), sizeScalar, sizeScalar, null);
					break;

				case WARNING:
					canvas.drawImage(warning, (si.getX() * sizeScalar), (si.getY() * sizeScalar), sizeScalar, sizeScalar, null);
					break;

				case ERROR:
					canvas.drawImage(error, (si.getX() * sizeScalar), (si.getY() * sizeScalar), sizeScalar, sizeScalar, null);
					break;

				case NOTICE:
					canvas.drawImage(notice, (si.getX() * sizeScalar), (si.getY() * sizeScalar), sizeScalar, sizeScalar, null);
					break;

				default:
					System.err.println("Bad State From Status Icon");
					break;
			}
		} else if (o instanceof JBLineDivider) {
			//Handle the drawing of a line divider
			JBLineDivider ld = (JBLineDivider) o;

			//Draw the line
			canvas.setColor(Color.LIGHT_GRAY);
			canvas.drawLine((ld.getX1() * sizeScalar) + calculateOrientationX(ld.getPrimaryOrientation()), (ld.getY1() * sizeScalar) + calculateOrientationY(ld.getPrimaryOrientation()), (ld.getX2() * sizeScalar) + calculateOrientationX(ld.getSecondaryOrientation()), (ld.getY2() * sizeScalar) + calculateOrientationY(ld.getSecondaryOrientation()));
		} else if (o instanceof JBLineGraph) {
			gdo.drawLineGraph(canvas, sizeScalar, (JBLineGraph) o);
		} else if (o instanceof JBPlotter) {
			gdo.drawPlot(canvas, sizeScalar, (JBPlotter) o);
		} else if (o instanceof JBNumericLogger) {
			gdo.drawLineGraph(canvas, sizeScalar, ((JBNumericLogger) o).getGraph());
		} else if (o instanceof JBTextHeader) {
			//Handle the drawing of a TextLabel
			JBTextHeader th = (JBTextHeader) o;

			Font newFont = new Font(Font.MONOSPACED, Font.PLAIN, th.getSize());
			canvas.setFont(newFont);

			canvas.setColor(Color.LIGHT_GRAY);
			drawTextElement(th.getContent(), th.getX(), th.getY(), th.getWidth(), th.getHeight(), th.isCentered(), false);

			canvas.setFont(dashboardFont);

		} else if (o instanceof JBImage) {
			//Handle the drawing of a Image
			JBImage im = (JBImage) o;

			canvas.drawImage(((JBImage) o).getImage(), im.getX() * sizeScalar, im.getY() * sizeScalar, im.getWidth() * sizeScalar, im.getHeight() * sizeScalar, null);
		} else if (o instanceof JBTextInput) {
			//Handle the drawing of a TextButton
			JBTextInput ti = (JBTextInput) o;

			if (ti.isSelected()) {
				//Draw as selected
				canvas.setColor(Color.LIGHT_GRAY);
				canvas.fillRect(ti.getX() * sizeScalar, ti.getY() * sizeScalar, ti.getWidth() * sizeScalar, ti.getHeight() * sizeScalar);

				canvas.setColor(Color.BLACK);
				drawTextElement(ti.getVisibleContent(), ti.getX(), ti.getY(), ti.getWidth(), ti.getHeight(), false, false);
			} else {
				//Draw as unselected
				canvas.setColor(Color.LIGHT_GRAY);
				drawTextElement(ti.getVisibleContent(), ti.getX(), ti.getY(), ti.getWidth(), ti.getHeight(), false, true);
			}
		}
	}

	//Redraws the content of the dashboard
	protected void update() {
		canvas.setColor(Color.BLACK);
		canvas.fillRect(0, 0, width*sizeScalar, height*sizeScalar);
		
		for (Object o : content) {
			drawObject(o);
		}
		panel.repaint();
	}
	
	//Calculates X orientation
	int calculateOrientationX(int o) {
		switch (o) {
		case 0:
			return sizeScalar / 2;
		case 1:
			return 0;
		case 2:
			return sizeScalar / 2;
		case 3:
			return sizeScalar-1;
		case 4:
			return sizeScalar-1;
		case 5:
			return sizeScalar-1;
		case 6:
			return sizeScalar /2;
		case 7:
			return 0;
		case 8: 
			return 0;
		default:
			return 0;
		}
	}
	
	//Calculates Y orientation
	int calculateOrientationY(int o) {
		switch (o) {
		case 0: 
			return sizeScalar / 2;
		case 1:
			return 0;
		case 2:
			return 0;
		case 3:
			return 0;
		case 4:
			return sizeScalar / 2;
		case 5:
			return sizeScalar-1;
		case 6:
			return sizeScalar-1;
		case 7:
			return sizeScalar-1;
		case 8:
			return sizeScalar / 2;
		default:
			return 0;
		}
	}

	//Returns the length of a string
	protected int getStringWidth(String s) {
		return canvas.getFontMetrics().stringWidth(s);
	}
	
	//Draws a text element based on specifications
	private void drawTextElement(String str, int x, int y, int w, int h, boolean cent, boolean bor) {
		//Centering Code
		FontMetrics met = canvas.getFontMetrics();
		
		int tx = 0;
		int ty = (y * sizeScalar) + (((h * sizeScalar) - met.getHeight()) / 2) + met.getAscent();
			
				
		if (cent) {
			tx = (x * sizeScalar) + ((w * sizeScalar) - met.stringWidth(str)) / 2;
		} else {
			tx = x * sizeScalar;
		}
		
		canvas.drawString(str, tx, ty);
		
		if (bor) canvas.drawRect(x * sizeScalar, y * sizeScalar, w * sizeScalar, h * sizeScalar);
	}
	
	//Adds an object into the dashboard
	protected void add(Object o) {
		//Set correct callback for objects
		if (o instanceof JBLineDivider) ((JBLineDivider) o).setCallback(this);
		else if (o instanceof JBStatusIcon) ((JBStatusIcon) o).setCallback(this);
		else if (o instanceof JBTextButton) ((JBTextButton) o).setCallback(this);
		else if (o instanceof JBTextLabel) ((JBTextLabel) o).setCallback(this);
		else if (o instanceof JBLineGraph) ((JBLineGraph) o).setCallback(this);
		else if (o instanceof JBPlotter) ((JBPlotter) o).setCallback(this);
		else if (o instanceof JBNumericLogger) ((JBNumericLogger) o).setCallback(this);
		else if (o instanceof JBTextHeader) ((JBTextHeader) o).setCallback(this);
		else if (o instanceof JBImage) ((JBImage) o).setCallback(this);
		else if (o instanceof JBTextInput) ((JBTextInput) o).setCallback(this);
		
		//Add and update
		content.add(o);
		update();
	}
	
	//Removes an object from the dashboard
	protected void remove(Object o) {
  		//Remove and update
		content.remove(o);
		update();
	}
	
	//This function handles mouse-related events
	protected void dashboardMouse(int x, int y, boolean pressed) {
		boolean willUpdate = false;
		for (Object o : content) {
			if (o instanceof JBTextButton) {
				JBTextButton tb = (JBTextButton) o;
				
				if ((sizeScalar * tb.getX()) <= x && x <= (sizeScalar * tb.getX()) + (sizeScalar * tb.getWidth()) && (sizeScalar * tb.getY()) <= y && y <= (sizeScalar * tb.getY()) + (sizeScalar * tb.getHeight())) {
					if (!tb.isSelected()) willUpdate = true;
					tb.setSelected(true);
					
					if (pressed) tb.pressButton();
				} else {
					if (tb.isSelected()) willUpdate = true;
					tb.setSelected(false);
				}
			}

			if (o instanceof JBTextInput) {
				JBTextInput ti = (JBTextInput) o;


				if (pressed) {
					if (ti.isFocused()) willUpdate = true;
					ti.setFocused(false);
				}

				if ((sizeScalar * ti.getX()) <= x && x <= (sizeScalar * ti.getX()) + (sizeScalar * ti.getWidth()) && (sizeScalar * ti.getY()) <= y && y <= (sizeScalar * ti.getY()) + (sizeScalar * ti.getHeight())) {
					if (!ti.isSelected()) willUpdate = true;
					ti.setSelected(true);

					if (pressed) {
						ti.setFocused(true);
						willUpdate = true;
					}
				} else {
					if (ti.isSelected()) willUpdate = true;
					ti.setSelected(false);
				}
			}
		}
		
		if (willUpdate) update();
	}

	//This function handles keyboard-related events
	protected void dashboardKey(char c) {
		for (Object o : content) {
			if (o instanceof JBTextInput) {
				JBTextInput ti = (JBTextInput) o;

				if (ti.isFocused()) {
					if (c < 127 && (c > 31 || c == 8 || c == 10)) {
						if (c == 8) {
							//Remove 1 off of the end of the string
							if (ti.getContent().length() != 0) ti.setContent(ti.getContent().substring(0, ti.getContent().length()-1));
						} else if (c == 10) {
							ti.enterString(ti.getContent());
							ti.update();
						} else {
							ti.setContent(ti.getContent() + c);
						}
					}
				}
			}
		}
	}
	//Images down here, loaded once and never again
	private Image error;
	private Image inactive;
	private Image nominal;
	private Image notice;
	private Image warning;

	//Binds a mouse listener to a generic dashboard
	public void bindDashboardMouseListener(DashboardMouseListener d) {
		mouse.setDashboardMouseListener(d);
	}

	//Binds a keyboard listener to a generic dashboard
	public void bindDashboardKeyboardListener(DashboardKeyboardListener d) {
		key.setDashboardKeyboardListener(d);
	}
}
