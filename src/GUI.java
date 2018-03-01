import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int counter = 0;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
						
					// Get default webcam and open it
						Webcam webcam = Webcam.getDefault();
						webcam.setViewSize(WebcamResolution.VGA.getSize());

						WebcamPanel panel = new WebcamPanel(webcam);
					//	panel.setFPSDisplayed(true);
					//	panel.setDisplayDebugInfo(true);
					//	panel.setImageSizeDisplayed(true);
						panel.setMirrored(true);
						
						GUI frame = new GUI(webcam);
						frame.setVisible(true);
						frame.getContentPane().add(panel);
						frame.setResizable(true);
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.pack();
						frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI(Webcam webcam) {
		setTitle("Authentication Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		// Takes out the location of the icon to be used with the Button 
		// It takes pathname of the image as a Parameter
		ImageIcon icon = new ImageIcon("/Users/Sandeep/Documents/eclipse-workspace/WebCamWithAPI/capture.png");
		
		JButton btnNewButton = new JButton(icon);
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Captures the  image
				BufferedImage image = webcam.getImage();
				
				StringBuilder imageName = new StringBuilder();
				imageName.append("image");
				imageName.append(counter++);
				
				// Save image to PNG file
				try {
					ImageIO.write(image, "PNG", new File("/Users/Sandeep/Documents/eclipse-workspace/WebCamWithAPI/captured images/" + imageName + ".png"));

				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				PredictAdminViaClarifaiAPI predictUser = new PredictAdminViaClarifaiAPI();
				double response = predictUser.getResponse("/Users/Sandeep/Documents/eclipse-workspace/WebCamWithAPI/captured images/" + imageName + ".png");
				System.out.println("Predicted User: " + response);
			}
			
		});
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
	}
	

}
