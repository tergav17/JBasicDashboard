package dash;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DashboardMouseAdapter implements MouseMotionListener, MouseListener {

	GenericManager gm;
	
	protected DashboardMouseAdapter(GenericManager gm) {
		this.gm = gm;
	} 
	
	@Override
	public void mouseClicked(MouseEvent e) {
		gm.dashboardMouse(e.getX(), e.getY(), true);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		gm.update();
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		gm.dashboardMouse(e.getX(), e.getY(), false);
		
	}

}
