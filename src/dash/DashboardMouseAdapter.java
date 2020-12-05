package dash;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class DashboardMouseAdapter implements MouseMotionListener, MouseListener {

	GenericManager gm;
	DashboardMouseListener dml = null;
	boolean down = false;
	
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
		gm.dashboardMouse(e.getX(), e.getY(), e.getButton() == MouseEvent.BUTTON1, e.getButton() == MouseEvent.BUTTON1);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) down = true;
		if (dml != null) dml.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1) down = false;
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
		gm.dashboardMouse(e.getX(), e.getY(), false, down);
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (dml != null) dml.mouseMoved(e);
		gm.dashboardMouse(e.getX(), e.getY(), false, down);
		
	}

}
