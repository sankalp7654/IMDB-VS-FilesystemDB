package views;
import java.awt.BorderLayout;
import common.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Toolkit;
import javax.swing.UIManager;

public class GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static int imageCounter = 0;
	private static double response = 0D;
	private JPanel contentPane;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
				try {
						
					// Get default webcam and open it
					Webcam webcam = Webcam.getDefault();
					webcam.setViewSize(WebcamResolution.VGA.getSize());
					
					GUI frame = new GUI(webcam);
						
					WebcamPanel panel = new WebcamPanel(webcam);
					panel.setMirrored(true);
					frame.setVisible(true);
					frame.getContentPane().add(panel);
					frame.setResizable(true);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.pack();
						
					System.out.println("fefer");
			
				} catch (Exception e) {
					e.printStackTrace();
				} 
		
	}

	/**
	 * Create the frame.
	 */
	
	public GUI(Webcam webcam) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/resources/icon.png")));
		setTitle("Authentication Window");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
				
		JButton btnNewButton = new JButton(new ImageIcon(GUI.class.getResource("/resources/capture.png")));
		
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Captures the  image
				BufferedImage image = webcam.getImage();
				
				StringBuilder imageName = new StringBuilder();
				imageName.append("image");
				imageName.append(imageCounter++);
				
				// Save image to PNG file
				try {
					ImageIO.write(image, "PNG", new File("/Users/Sandeep/Documents/eclipse-workspace/FaceAuth-IMDB/captured images/" + imageName + ".png"));

				} catch (IOException e1) {

					e1.printStackTrace();
				}

				PredictAdminViaClarifaiAPI predictUser = new PredictAdminViaClarifaiAPI();
			    response = predictUser.getResponse("/Users/Sandeep/Documents/eclipse-workspace/FaceAuth-IMDB/captured images/" + imageName + ".png");
				System.out.println("Predicted User: " + response);
				
				
				if(response > 0.98000000) {
					
					IMDB_GUI frame1 = new IMDB_GUI();
					frame1.setVisible(true);
					frame1.getContentPane();
					frame1.setResizable(true);
					frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					dispose(); // Destroy the current Frame
					webcam.close(); // Closes the webcam object (Closes the camera)
					
				}	
				else {
					JOptionPane.showMessageDialog(null ,"Invalid Administrator", "Authentication Failed ", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		contentPane.add(btnNewButton, BorderLayout.SOUTH);
	
	}	
}
