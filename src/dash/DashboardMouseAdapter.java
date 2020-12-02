package dash;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DashboardMouseAdapter implements MouseMotionListener, MouseListener {

	GenericManager gm;
	DashboardMouseListener dml = null;
	
	protected DashboardMouseAdapter(GenericManager gm) {
		this.gm = gm;
	}

	//Sets the dashboard listener so that the user can hook onto the events
	protected void setDashboardMouseListener(DashboardMouseListener d) {
		dml = d;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		if (dml != null) dml.mouseClicked(e);
		gm.dashboardMouse(e.getX(), e.getY(), true);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (dml != null) dml.mousePressed(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (dml != null) dml.mouseReleased(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (dml != null) dml.mouseEntered(e);
		gm.update();
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (dml != null) dml.mouseExited(e);
		gm.update();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (dml != null) dml.mouseDragged(e);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (dml != null) dml.mouseMoved(e);
		gm.dashboardMouse(e.getX(), e.getY(), false);
		
	}

}
