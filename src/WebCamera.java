import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

public class WebCamera {
	
	public static void main(String[] args) throws InterruptedException, IOException {

		// Get default webcam and open it
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
		
		// Captures the  image
		BufferedImage image = webcam.getImage();

		// Save image to PNG file
		ImageIO.write(image, "PNG", new File("/Users/Sandeep/Documents/eclipse-workspace/user.png"));

		PredictAdmin predictUser = new PredictAdmin();
		double response = predictUser.getResponse("/Users/Sandeep/Documents/eclipse-workspace/user.png");
		System.out.println("Predicted User: " + response);
	}
}





