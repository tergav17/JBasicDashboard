package dash;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


//Main dashboard object
//When a new object is constructed, a dashboard will be generated using the passed arguments
public class Dashboard {
	
	//Controls the size of a grid square
	public static final int sizeScalar = 15;
	
	public enum Type {
		GENERIC,
		CONSOLE
	}
	
	//GUI Objects
	private JFrame frame;
	private JPanel panel;
	
	//Dashboard Attributes
	private Type dType;
	
	//Dashboard Managers
	private ConsoleManager cm = null;
	private GenericManager gm = null;
	
	public Dashboard(Type t, String name, int width, int height) {
		startGarbageCollector();

		dType = t;
		
		//Set up JFrame for use with generic dashboard
		if (t == Type.GENERIC) {
			//Create JFrame object
			frame = new JFrame(name);


			//Set up JPanel attributes
			panel = new JPanel();
			panel.setPreferredSize(new Dimension(width * sizeScalar, height * sizeScalar));
		
			gm = new GenericManager(panel, sizeScalar, width, height);
			
			//Set up JFrame attributes
			frame.add(panel);
			frame.pack();
			frame.setResizable(false);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			gm.update();
			
		//Otherwise, set up as a console
		} else if (t == Type.CONSOLE) {
			//Create JFrame object
			frame = new JFrame(name);
			
			//Create text area for console
			JTextArea textArea = new JTextArea();
			textArea.setRows(height);
			textArea.setColumns(width);
			
			//Text area configuration
		    textArea.setBackground(Color.BLACK);
		    textArea.setForeground(Color.LIGHT_GRAY);
		    textArea.setEditable(false);
		    textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		    
		    //Set up the console manager
		    cm = new ConsoleManager(textArea);
		    
		    //Set of JFrame with text area
		    frame.add(textArea);
			frame.pack();
			frame.setResizable(false);
			frame.setVisible(true);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		} else {
			//Uh oh :(
			System.err.println("Bad Dashboard Type!");
		}
	}
	
	public void dashboardExitOnClose() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	//Prints a string onto the console
	public void print(String str) {
		if (!frame.isDisplayable()) return;

		if (dType == Type.CONSOLE) {
			cm.print(str);
		} else System.err.println("Incorrect Dashboard Call Type");
	}
	
	//Prints a character onto the console
	public void putChar(Character c) {
		if (!frame.isDisplayable()) return;

		if (dType == Type.CONSOLE) {
				cm.putChar(c);
		} else System.err.println("Incorrect Dashboard Call Type");
	}
	
	//Scan in string from console
	public String scanString() {
		if (!frame.isDisplayable()) return null;

		if (dType == Type.CONSOLE) {
			return cm.scanString();
		} else System.err.println("Incorrect Dashboard Call Type");
		return null;
	}
	
	//Scan in char from console
	public Character scanChar() {
		if (!frame.isDisplayable()) return null;

		if (dType == Type.CONSOLE) {
			return cm.scanChar();
		} else System.err.println("Incorrect Dashboard Call Type");
		return null;
	}
	
	//Add element to dashboard
	public void add(Object o) {
		if (!frame.isDisplayable()) return;

		if (dType == Type.GENERIC) {
			gm.add(o);
			gm.update();
		} else System.err.println("Incorrect Dashboard Call Type");
	}
	
	//Remove element from dashboard
	public void remove(Object o) {
		if (!frame.isDisplayable()) return;

		if (dType == Type.GENERIC) {
			gm.remove(o);
			gm.update();
		} else System.err.println("Incorrect Dashboard Call Type");
	}
	
	//Updates dashboard
	public void update() {
		if (!frame.isDisplayable()) return;

		if (dType == Type.GENERIC) {
			gm.update();
		} else System.err.println("Incorrect Dashboard Call Type");
	}

	//Prints a string onto the console, with linebreak
	public void println(String str) {
		if (!frame.isDisplayable()) return;

		if (dType == Type.CONSOLE) {
			cm.println(str);
		} else System.err.println("Incorrect Dashboard Call Type");
	}


	//For some reason unless this exists, memory just keeps piling up on my system until it reaches like ~200MB
	//I hate it here
	//
	//We will also dispose of any unused JFrames here, events are for losers
	private void startGarbageCollector() {
		ActionListener garbageUpdate = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!frame.isDisplayable()) {
					frame.dispose();
					frame = null;

					if (dType == Type.GENERIC) gm.isFrameDisposed = true;

					((Timer) e.getSource()).stop();
				}

				System.gc();
			}
		};

		Timer t = new Timer(20000, garbageUpdate);
		t.setRepeats(true);
		t.start();
	}

	//Binds a mouse clicked listener to a generic dashboard

}
