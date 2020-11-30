package dash;

import java.awt.event.ActionEvent;

import javax.swing.text.TextAction;

public class NullDeletePrevCharAction extends TextAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8210774578418948140L;

	public NullDeletePrevCharAction(String name) {
		super(name);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//Do nothing lmao
	}


}
