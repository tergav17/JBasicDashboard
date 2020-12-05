package dash;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JBHorizontalSlider {
    private ActionListener event;
    private int x;
    private int y;
    private int width;

    private int realPos;
    private double max;
    private double min;

    private boolean isSelected = false;

    public JBHorizontalSlider(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;

        min = 0;
        max = 1;
    }

    private GenericManager callback;
    public void setCallback(GenericManager gm) {
        callback = gm;
    }
    public void update() {
        if (callback != null) callback.update();
    }

    public double getValue() {
        return min + (realPos * ((max - min) / ((width * callback.getSizeScalar() * 1.0) - (1.0 * callback.getSizeScalar()))));
    }

    public ActionListener getEvent() {
        return event;
    }

    public void setEvent(ActionListener event) {
        this.event = event;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
        update();
    }

    public int getRealPos() {
        return realPos;
    }

    public void setRealPos(int realPos) {
        this.realPos = realPos;

        ActionEvent aEvent = new ActionEvent(this, 0, "Moved");
        if (event != null) event.actionPerformed(aEvent);

        update();
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }
}
