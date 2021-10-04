package dash;

import sun.awt.image.ToolkitImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceLoader {
	private static ResourceLoader rl = new ResourceLoader();
	
	public static Image getImage(String fileName) {
		Image i = null;
		try {
			i = ImageIO.read(rl.getClass().getResource(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedImage b = toBufferedImage(i);

		if (b == null) System.err.println("IMAGE LOAD FAILURE: " + fileName);

		return b;
	}

	//Waits for an image to load, then transfers it to a buffered image
	public static BufferedImage toBufferedImage(Image img) {
		try {
			MediaTracker mt = new MediaTracker(new JPanel());
			mt.addImage(img, 0);
			mt.waitForAll();

			BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			Graphics2D g2d = bi.createGraphics();
			g2d.drawImage(img, 0, 0, null);
			g2d.dispose();

			return bi;
		} catch (InterruptedException ex) {
		}

		return null;
	}
}
