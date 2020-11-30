package dash;

import java.awt.Image;
import java.awt.Toolkit;

public class ResourceLoader {
	private static ResourceLoader rl = new ResourceLoader();
	
	public static Image getImage(String fileName) {
		return Toolkit.getDefaultToolkit().getImage(rl.getClass().getResource(fileName));
	}
}
