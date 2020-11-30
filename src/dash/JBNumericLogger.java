package dash;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class JBNumericLogger {
	private JBLineGraph graph = null;
	private ArrayList<Tracker> trackers = new ArrayList<Tracker>();
	
	private int trackerUpdateTrigger = 0;
	
	public JBNumericLogger(String name, int x, int y, int width, int height, String axis, double duration) {
		graph = new JBLineGraph(name, x, y, width, height, "Time", axis);
		graph.setXMax(Math.abs(duration));
		
		//Timed updater
		ActionListener graphUpdate = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				trackerUpdateTrigger++;

				int delay = (int) (graph.getXMax() / (width * 1.0) );
				
				//Shift every single data point forward, and remove any that are too much forward
				for (Dataset d : graph.getData()) {
					int pastCount = 0;
					
					//Shift back the points
					for (Point p : d.getPoints()) {
						p.setX(p.getX() + 0.05);
						if (p.getX() > graph.getXMax()) pastCount++;
					}
					
					//If there are more than 1 point across the render area, then remove them till there is one left
					for (int i = 1; i < pastCount; i++) {
						//If the points are unpopulated, just continue
						if (d.getPoints().size() == 0) continue;
						
						Point maxPoint = d.getPoints().get(0);
						
						//Get the maximum point
						for (Point p : d.getPoints()) {
							if (p.getX() > maxPoint.getX()) {
								maxPoint = p;
							}
						}
						
						//And remove it
						d.getPoints().remove(maxPoint);
					}
					
				}
				
				//Log any trackers, if update is needed
				if (trackerUpdateTrigger > delay) {
					
					for (Tracker t : trackers) {
						log(t.getValue(), t.getColor());
						
					}
					
					trackerUpdateTrigger = 0;
				}

				if (callback.isFrameDisposed) ((Timer) e.getSource()).stop();

				update();
				
			}
			
		};
		
		Timer t = new Timer(50, graphUpdate);
		t.setRepeats(true);
		t.start();
	}
	
	//Log a value
	public void log(double v, Color c) {
		//Time to look and see if a dataset exists with the proper color

		boolean exists = false;
		
		for (Dataset d : graph.getData()) {
			if (d.getColor().equals(c)) {
				//It exits, log it
				d.add(new Point(0, v));
				
				//And state that it exists too
				exists = true;
			}
		}
		
		//If it doesn't exist, just make one
		if (!exists) {
			Dataset d = new Dataset(c);
			
			d.add(new Point(0, v));

			graph.addDataset(d);
		}
	}
	
	private GenericManager callback;
	public void setCallback(GenericManager gm) {
		callback = gm;
		graph.setCallback(gm);
	}
	public void update() {
		if (callback != null) callback.update();
	}
	
	public JBLineGraph getGraph() {
		return graph;
	}
	
	public void setDuration(double d) {
		graph.setXMax(d);
	}
	
	public double getDuration() {
		return graph.getXMax();
	}

	public ArrayList<Tracker> getTrackers() {
		return trackers;
	}

	public void setTrackers(ArrayList<Tracker> trackers) {
		this.trackers = trackers;
	}
	
	public void addTracker(Tracker t) {
		trackers.add(t);
	}
	
	public void addTracker(Double d, Color c) {
		trackers.add(new Tracker(d, c));
	}
	
}
