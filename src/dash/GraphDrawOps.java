package dash;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class GraphDrawOps {
	
	//Java AWT gore zone
	
	public void drawLineGraph(Graphics2D canvas, int sizeScalar, JBLineGraph lg) {
		int width = lg.getWidth() - 8;
		int height = lg.getHeight() - 5;
		
		//Set color
		canvas.setColor(Color.LIGHT_GRAY);
		
		//Bounding rectangle
		canvas.drawRect(lg.getX() * sizeScalar, lg.getY() * sizeScalar, (lg.getWidth() * sizeScalar) - 1, (lg.getHeight() * sizeScalar) - 1);
		
		//Draw bounds of the graph
		canvas.drawLine((lg.getX() * sizeScalar) + (5 * sizeScalar), (lg.getY() * sizeScalar) + (2 * sizeScalar), (lg.getX() * sizeScalar) + (5 * sizeScalar), (lg.getY() * sizeScalar) + ((2 + height) * sizeScalar));
		canvas.drawLine((lg.getX() * sizeScalar) + (5 * sizeScalar), (lg.getY() * sizeScalar) + ((2 + height) * sizeScalar), (lg.getX() * sizeScalar) + ((5 + width) * sizeScalar), (lg.getY() * sizeScalar) + ((2 + height) * sizeScalar));
		
		Point v1 = new Point((lg.getX() * sizeScalar) + (5 * sizeScalar) + 1, (lg.getY() * sizeScalar) + ((2 + height) * sizeScalar) - 1);
		Point v2 = new Point((lg.getX() * sizeScalar) + ((5 + width) * sizeScalar), (lg.getY() * sizeScalar) + (2 * sizeScalar));
		
		//Draw named componenets
		drawTextElement(lg.getName(), lg.getX(), lg.getY(), lg.getWidth(), 1, 0, false, canvas, sizeScalar);
		drawTextElement(lg.getXAxis(), lg.getX(), lg.getY() + (lg.getHeight() - 1), lg.getWidth(), 1, 0, false, canvas, sizeScalar);
		drawTextElement(lg.getYAxis(), lg.getX(), lg.getY(), 1, 1, -1, false, canvas, sizeScalar);
		
		//Axis time!
		if (lg.getYMin() >= lg.getYMax() || lg.getXMin() >= lg.getYMax()) return;
		
		//Generate Y axis
		double diff = lg.getYMax() - lg.getYMin();
		double inc = diff / (height * 1.0);
		
		//Calculate line coord scales
		double scalarX = Math.abs((1.0 * (v2.getX())) - (1.0 * (v1.getX()))) / ((1.0 * (lg.getXMax())) - (1.0 * (lg.getXMin())));
		double scalarY = Math.abs((1.0 * (v2.getY())) - (1.0 * (v1.getY()))) / ((1.0 * (lg.getYMax())) - (1.0 * (lg.getYMin())));
		
		//Draw numbers
		for (int i = 0; i < height; i++) {
			drawNumber(lg.getYMax() - (inc * i), canvas, sizeScalar, lg.getX() + 5, lg.getY() + 2 + i, 1);
		} 
		
		//Draw the X axis numbers
		drawNumber(lg.getXMax(), canvas, sizeScalar, lg.getX() + (lg.getWidth() - 6), lg.getY() + (lg.getHeight() - 3), 0);
		drawNumber(lg.getXMin(), canvas, sizeScalar, lg.getX() + 2, lg.getY() + (lg.getHeight() - 3), 0);
		
		BufferedImage lines = new BufferedImage((int) (v2.getX() - v1.getX()), (int) (v1.getY() - v2.getY()), BufferedImage.TYPE_INT_ARGB);
		Graphics linesG = lines.getGraphics();

		
		//Draw Graph Lines
		for (Dataset d : lg.getData()) {
			linesG.setColor(d.getColor());
			
			//Sorting algorithm from hell, don't do this folks
			double minX = 0 - Double.MAX_VALUE;
			
			Point curr = null;
			Point last = null;
			
			for (int i = 0; i < d.getPoints().size(); i++) {
				
				double sortX = Double.MAX_VALUE;
				
				for (Point p : d.getPoints()) {
					if (sortX >= p.getX() && minX < p.getX()) {
						curr = p;
						sortX = p.getX();
					}
				}
				
				//If this is the case, do this instead of shitting the bed
				if (curr == null) return;
				minX = curr.getX();
				
				//Draw the line
				if (last != null) {
					//Correct for the graph bounds
					Point v3 = new Point(last.getX() - lg.getXMin(), last.getY() - lg.getYMin());
					Point v4 = new Point(curr.getX() - lg.getXMin(), curr.getY() - lg.getYMin());
					
					//Actually draw it
					linesG.drawLine((int) (v3.getX() * scalarX), (int) (lines.getHeight() - (v3.getY() * scalarY)), (int) (v4.getX() * scalarX), (int) (lines.getHeight() - (v4.getY() * scalarY)));
				} 
				
				last = curr;
			}
		}
		
		canvas.drawImage(lines, (int) v1.getX(), ((int) v1.getY()) - lines.getHeight(), null);
	}
	
	public void drawPlot(Graphics2D canvas, int sizeScalar, JBPlotter p) {
		int width = p.getWidth() - 8;
		int height = p.getHeight() - 5;
		
		//Set color
		canvas.setColor(Color.LIGHT_GRAY);
		
		//Bounding rectangle
		canvas.drawRect(p.getX() * sizeScalar, p.getY() * sizeScalar, (p.getWidth() * sizeScalar) - 1, (p.getHeight() * sizeScalar) - 1);
		
		//Draw bounds of the graph
		canvas.drawLine((p.getX() * sizeScalar) + (5 * sizeScalar), (p.getY() * sizeScalar) + (2 * sizeScalar), (p.getX() * sizeScalar) + (5 * sizeScalar), (p.getY() * sizeScalar) + ((2 + height) * sizeScalar));
		canvas.drawLine((p.getX() * sizeScalar) + (5 * sizeScalar), (p.getY() * sizeScalar) + ((2 + height) * sizeScalar), (p.getX() * sizeScalar) + ((5 + width) * sizeScalar), (p.getY() * sizeScalar) + ((2 + height) * sizeScalar));
		
		Point v1 = new Point((p.getX() * sizeScalar) + (5 * sizeScalar) + 1, (p.getY() * sizeScalar) + ((2 + height) * sizeScalar) - 1);
		//Point v2 = new Point((p.getX() * sizeScalar) + ((5 + width) * sizeScalar), (p.getY() * sizeScalar) + (2 * sizeScalar));
		
		//Draw named componenets
		drawTextElement(p.getName(), p.getX(), p.getY(), p.getWidth(), 1, 0, false, canvas, sizeScalar);
		drawTextElement(p.getXAxis(), p.getX(), p.getY() + (p.getHeight() - 1), p.getWidth(), 1, 0, false, canvas, sizeScalar);
		drawTextElement(p.getYAxis(), p.getX(), p.getY(), 1, 1, -1, false, canvas, sizeScalar);
		
		//Axis time!
		if (p.getYMin() >= p.getYMax() || p.getXMin() >= p.getYMax()) return;
		
		//Generate Y axis
		double diff = p.getYMax() - p.getYMin();
		double inc = diff / (height * 1.0);
		
		//Draw numbers
		for (int i = 0; i < height; i++) {
			drawNumber(p.getYMax() - (inc * i), canvas, sizeScalar, p.getX() + 5, p.getY() + 2 + i, 1);
		} 
		
		//Draw the X axis numbers
		drawNumber(p.getXMax(), canvas, sizeScalar, p.getWidth() - 6, p.getHeight() - 3, 0);
		drawNumber(p.getXMin(), canvas, sizeScalar, 2, p.getHeight() - 3, 0);
		
		canvas.drawImage(p.getPlot(), (int) v1.getX(), ((int) v1.getY()) - p.getPlot().getHeight(), null);
	}
	
	private void drawNumber(double num, Graphics2D canvas, int sizeScalar, int x, int y, int o) {
		drawTextElement(compactNumber(num, 7), x, y, 7, 1, o, false, canvas, sizeScalar);
	}
	
	//Returns a string with a amount of character equal or less than len
	private String compactNumber(double num, int len) {
		BigDecimal bd = new BigDecimal(num).setScale(2, RoundingMode.HALF_DOWN);
		
		num = bd.doubleValue();
		
		String str = String.valueOf(num);
		
		//If string is already good, no need to mess with it
		if (str.length() > len) {
			str = String.format("%.0f", num);
			//If minimum percision is good, loop to find the maximum valid percision
			if (str.length() <= len){

				int per = 1;
				while (true) {
					String tes = String.format("%."+per+"f", num);
					
					if (tes.length() > len) {
						break;
					} else {
						per++;
						str = tes;
					}
				}
			} else {
				//Else, put in scientific notation
				int factor = 1;
				while (true) {
					String tes = String.format("%.1f", num / (Math.pow(10.0, factor*1.0))) + "e" + factor;
					
					if ((num / (Math.pow(10.0, factor*1.0))) < 0.01) return "TOOBIG!";
					
					if (tes.length() <= len ) {
						str = tes;
						break;
					} else {
						factor++;
					}
				}
			}
		}
		
		return str;
		
	}
	
	//Draws a text element based on specifications
	private void drawTextElement(String str, int x, int y, int w, int h, int or, boolean bor, Graphics2D canvas, int sizeScalar) {
		//Centering Code
		FontMetrics met = canvas.getFontMetrics();
		
		int tx = 0;
		int ty = (y * sizeScalar) + (((h * sizeScalar) - met.getHeight()) / 2) + met.getAscent();
			
				
		if (or == 0) {
			//Draw in middle
			tx = (x * sizeScalar) + ((w * sizeScalar) - met.stringWidth(str)) / 2;
		} else if (or == 1) {
			//Draw shifted left
			tx = (x * sizeScalar) - met.stringWidth(str) ;
		} else {
			//Draw shifted right
			tx = x * sizeScalar + 2;
		}
		
		canvas.drawString(str, tx, ty);
		
		if (bor) canvas.drawRect(x * sizeScalar, y * sizeScalar, w * sizeScalar, h * sizeScalar);
	}
}
