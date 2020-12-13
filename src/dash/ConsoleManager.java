package dash;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;

//Controls how the text area on a console dashboard acts
public class ConsoleManager {
	
	//Reference to working textArea
	private JTextArea textArea;

	private String buffer;
	
	//Input buffer from keyboard adapter
	protected List<Character> inputBuffer = Collections.synchronizedList(new ArrayList<Character>());
	
	protected ConsoleManager(JTextArea textArea) {
		this.textArea = textArea;

		//Add keyboard adapter
		textArea.addKeyListener(new ConsoleKeyboardAdapter(this));
		textArea.setLineWrap(false);
		
		//Windows likes to make a noise when the backspace is pressed in the text area
		//This code will make sure that doesn't happen
		textArea.getActionMap().put(DefaultEditorKit.deletePrevCharAction, new NullDeletePrevCharAction(null));
		
		//Fill up text area with blank spaces
		String blank = "";
		for (int i = 0; i < textArea.getRows(); i++) {
			for (int j = 0; j < textArea.getColumns(); j++) {
				blank = blank + ((i + j == 0) ? "_" : " ");
			}
			if (i != textArea.getRows() - 1) blank = blank + "\n";
		}
		
		buffer = blank;
		update();
		
	}
	
	//Prints a string onto the console
	protected void print(String s) {
		for (Character c : s.toCharArray()) {
			putChar(c);
		}
	}

	//Updates the content of the textArea
	//Ensures that textArea doesn't lockup and crash
	protected void update() {
		try {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					textArea.setText(buffer);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//Prints a string onto the console, with line break
	protected void println(String s) {
		print(s + "\n");
	}
	
	//Cursor locations
	int cursorX = 0;
	int cursorY = 0;
	
	//Puts a character on the console
	protected void putChar(Character c) {
		//Draw character
		if (c == 8) {
			drawCharAt(' ', cursorX, cursorY);
			if (cursorX + cursorY != 0) {
				cursorX--;
				if (cursorX == -1) {
					cursorX = textArea.getColumns() - 1;
					cursorY--;
					if (cursorY == -1) cursorY = 0;
				}
			}
		} else if (c == 10) {
			drawCharAt(' ', cursorX, cursorY);
			cursorY++;
			cursorX = 0;
		} else {
			drawCharAt(c, cursorX, cursorY);
			cursorX++;
		}
		
		//Cursor control
		if (cursorX == textArea.getColumns()) {
			cursorX = 0;
			cursorY++;
		}
		if (cursorY == textArea.getRows()) {
			cursorY--;
			buffer = buffer.substring(textArea.getColumns() + 1);
			buffer = buffer + "\n";
			for (int i = 0; i < textArea.getColumns(); i++) buffer = buffer + " ";
			update();
		}
		
		drawCharAt('_', cursorX, cursorY);
	}
	
	//Draws in a chararcter at a specific position
	private void drawCharAt(Character c, int x, int y) {
		int index = (y * (textArea.getColumns() + 1)) + x;

		buffer = buffer.substring(0, index) + c + buffer.substring(index + 1);
		update();

	}
	
	//Scans in a string from the console
	protected String scanString() {
		String str = "";
		
		while (true) {
			if (inputBuffer.size() > 0) {
				Character c = inputBuffer.get(0);
				inputBuffer.remove(0);

				if (!(str.length() == 0 && c == 8)) putChar(c);
				
				if (c == 8) {
					if (str.length() > 0) str = str.substring(0, str.length() - 1);
				} else if (c == 10) {
					break;
				} else {
					str = str + c;
				}
			} else {
				if (forfit() == 1) return null;

			}
		}
		
		return str;
	}
	
	//Scans in a character from the console
	protected Character scanChar() {
		while (true) {
			if (inputBuffer.size() > 0) {
				Character c = inputBuffer.get(0);
				inputBuffer.remove(0);
				
				putChar(c);
				
				return c;
			} else {
				if (forfit() == 1) return null;
			}
		}
	}

	//Returns a char from the input buffer if one exists, otherwise returns 0
	protected Character getChar() {
		if (inputBuffer.size() > 0) {
			Character c = inputBuffer.get(0);
			inputBuffer.remove(0);

			return c;
		} else {
			return 0;
		}

	}
	//Waits, as to not eat up all of the CPU power lol
	//Folks, don't try this one at home
	private int forfit() {

		//getParent() from hell
		if (!textArea.getParent().getParent().getParent().getParent().isDisplayable()) return 1;

		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return 0;
	}

}
