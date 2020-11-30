package dash;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ConsoleKeyboardAdapter implements KeyListener{
	ConsoleManager m;
	
	public ConsoleKeyboardAdapter(ConsoleManager cm) {
		m = cm;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() < 127 && (e.getKeyChar() > 31 || e.getKeyChar() == 8 || e.getKeyChar() == 10)) { 
			m.inputBuffer.add(e.getKeyChar());
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
