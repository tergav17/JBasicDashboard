package dash;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class DashboardKeyboardAdapter implements KeyListener{
    GenericManager gm;
    DashboardKeyboardListener dkl = null;

    public DashboardKeyboardAdapter(GenericManager m) {
        gm = m;
    }

    //Sets the dashboard listener so that the user can hook onto the events
    protected void setDashboardKeyboardListener(DashboardKeyboardListener d) {
        dkl = d;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (dkl != null) dkl.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (dkl != null) dkl.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

}
