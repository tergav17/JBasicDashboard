package test;

import dash.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//This class acts as a test suite to ensure that everything is working fine

public class DashboardTest {

    public static void main(String[] args) {

        System.out.println("Starting test...");

        Double times = 1.0;

        System.out.println("Generating Test Dashboard 1...");
        Dashboard d = new Dashboard(Dashboard.GENERIC, "Test Dashboard 1", 20, 16);

        System.out.println("Generating Logger...");
        Dashboard graph = new Dashboard(Dashboard.GENERIC, "Logger", 24, 15);

        System.out.println("Generating Console...");
        Dashboard con = new Dashboard(Dashboard.CONSOLE, "Console", 45, 10);

        System.out.println("Generating Plotter");
        Dashboard plot = new Dashboard(Dashboard.GENERIC, "Equation Plotter" , 24, 15);

        d.dashboardExitOnClose();

        d.add(new JBTextInput(new ActionListener() {


            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(((JBTextInput)e.getSource()).getContent());
                ((JBTextInput) e.getSource()).setContent("");
            }
        }, "Text Input Box!", 5, 8, 10, 1));

        JBHorizontalSlider hs = new JBHorizontalSlider(5, 10, 10);
        hs.setMax(25);
        d.add(hs);


        d.add(new JBTextHeader("Hello There!", 5, 0, 10, 1, true, 18));
        d.add(new JBTextLabel("- Times Pressed -", 5, 5, 10, 1, true));

        JBTextLabel counter = new JBTextLabel("0", 5, 6, 10, 1, true);

        d.add(counter);

        JBNumericLogger g = new JBNumericLogger("Slider", 0, 0, 24, 15, "Value", 5);

        g.getGraph().setYMax(25);

        Tracker t = new Tracker(0.0, Color.green);
        g.addTracker(t);

        hs.setEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                t.setValue(((JBHorizontalSlider) e.getSource()).getValue());
            }
        });



        d.update();

        graph.add(g);

        JBPlotter p = new JBPlotter("Result", 0, 0, 24, 15, "X Axis", "Y Axis");
        p.setXMax(10);
        p.setYMax(10);
        p.setYMin(-10);
        p.setXMin(-10);
        p.setLinear(true);
        p.setCurrentColor(Color.red);

        plot.add(p);
        con.println("Equation: Y = [A]X^3 + [B]X^2 + [C]X + [D]");

        con.print("A: ");
        String in = con.scanString();
        int valueA = 0;
        if (in != null) valueA = Integer.parseInt(in);

        con.print("B: ");
        in = con.scanString();
        int valueB = 0;
        if (in != null) valueB = Integer.parseInt(in);

        con.print("C: ");
        in = con.scanString();
        int valueC = 0;
        if (in != null) valueC = Integer.parseInt(in);

        con.print("D: ");
        in = con.scanString();
        int valueD = 0;
        if (in != null) valueD = Integer.parseInt(in);

        con.print("Plotting...\n");

        for (double x = -10; x < 10; x = x + 0.1) {
            p.fastPlot(x, (valueA * Math.pow(x, 3)) + (valueB * Math.pow(x, 2)) + (valueC * x) + (valueD));
        }

        p.update();

        con.print("Done");
    }
}
