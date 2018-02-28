import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

public class WebCamera extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws InterruptedException, IOException {

		Webcam webcam = Webcam.getDefault();
		webcam.setViewSize(WebcamResolution.VGA.getSize());

		WebcamPanel panel = new WebcamPanel(webcam);
	//	panel.setFPSDisplayed(true);
	//	panel.setDisplayDebugInfo(true);
	//	panel.setImageSizeDisplayed(true);
		panel.setMirrored(true);

		
		JFrame window = new JFrame("WebCam Panel");
		window.add(panel);
		window.setResizable(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setVisible(true);
		
		// get image
		BufferedImage image = webcam.getImage();

		// save image to PNG file
		ImageIO.write(image, "PNG", new File("/Users/Sandeep/Desktop/test.png"));
		
	}
}






