package views;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import common.FileSystemDB;
import common.InMemoryDB;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IMDB_GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txt_user_id;
	private JTextField txt_user_name;
	private JTextField txt_first_name;
	private JTextField txt_last_name;
	private JTextField txt_gender;
	private JTextField txt_password;
	private JTextField txt_status;
	private JPanel panel_2;
	private JTextField txt_filesystem_time;
	private JTextField txt_imdb_time;

	/**
	 * Create the frame.
	 */
	public IMDB_GUI() {
		initComponents();
	}
	
	public void initComponents() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(IMDB_GUI.class.getResource("/resources/icon.png")));
		getContentPane().setBackground(new Color(245, 245, 220));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("IN-Memory DataBase VS FileSystem DataBase");
		setBounds(100, 100, 520, 646);
	
		
		
		// Creating In Memory DataBase Default Dataset
		InMemoryDB object = new InMemoryDB();
		Connection connection;
		try {
			connection = object.getConnection();
			InMemoryDB.createDatabase(connection);
			InMemoryDB.displayIMDB(connection);
		} catch (ClassNotFoundException | FileNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 503, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(35, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 610, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(31, Short.MAX_VALUE))
		);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(245, 255, 250));
		tabbedPane.addTab("INSERT", null, panel, null);
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(255, 239, 213));
		panel_2.setSize(new Dimension(300, 100));
		panel_2.setBorder(new TitledBorder(null, "INSERT RECORD", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JLabel lbl_user_id = new JLabel("User ID:");
		lbl_user_id.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		txt_user_id = new JTextField();
		txt_user_id.setColumns(10);
		
		JLabel lbl_user_name = new JLabel("User Name:");
		lbl_user_name.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		txt_user_name = new JTextField();
		txt_user_name.setColumns(10);
		
		JLabel lbl_first_name = new JLabel("First Name:");
		lbl_first_name.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		txt_first_name = new JTextField();
		txt_first_name.setColumns(10);
		
		JLabel lbl_last_name = new JLabel("Last Name:");
		lbl_last_name.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		txt_last_name = new JTextField();
		txt_last_name.setColumns(10);
		
		JLabel lbl_gender = new JLabel("Gender:");
		lbl_gender.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		txt_gender = new JTextField();
		txt_gender.setColumns(10);
		
		JLabel lbl_password = new JLabel("Password:");
		lbl_password.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		txt_password = new JTextField();
		txt_password.setColumns(10);
		
		JLabel lbl_status = new JLabel("Status:");
		lbl_status.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		txt_status = new JTextField();
		txt_status.setColumns(10);
		
		JButton button = new JButton("INSERT INTO IMDB");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userDetails[] = new String[5];
				int userId = Integer.parseInt(txt_user_id.getText());
				userDetails[0] = txt_user_name.getText().toString();
				userDetails[1] = txt_first_name.getText().toString();
				userDetails[2] = txt_last_name.getText().toString();
				userDetails[3] = txt_gender.getText().toString();
				userDetails[4] = txt_password.getText().toString();
				int status = Integer.parseInt(txt_status.getText());
				try {
					Connection connection = object.getConnection();
					// Returned Time is in nanoseconds
					long execTime = InMemoryDB.insertIntoIMDB(connection, userDetails, userId, status);
					JOptionPane.showMessageDialog(null, "Record has been Inserted Successfully !", "Success", JOptionPane.DEFAULT_OPTION);
					// Displaying execution time in µ-seconds 
					txt_imdb_time.setText(Long.toString(execTime/1000) + " µs");
					// InMemoryDB.displayIMDB(connection);
					connection.close();
				} catch (ClassNotFoundException | FileNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
			}
		});
		
		JButton button_1 = new JButton("INSERT INTO FILESYSTEM");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userDetails[] = new String[7];
				userDetails[0] = txt_user_id.getText().toString();
				userDetails[1] = txt_user_name.getText().toString();
				userDetails[2] = txt_first_name.getText().toString();
				userDetails[3] = txt_last_name.getText().toString();
				userDetails[4] = txt_gender.getText().toString();
				userDetails[5] = txt_password.getText().toString();
				userDetails[6] = txt_status.getText();
				try {
					Connection connection = object.getConnection();
					// Returned Time is in nanoseconds
					long execTime = FileSystemDB.insertIntoFileSystemDB(userDetails);
					JOptionPane.showMessageDialog(null, "Record has been Inserted Successfully !", "Success", JOptionPane.DEFAULT_OPTION);
					// Displaying execution time in µ-seconds 
					txt_filesystem_time.setText(Long.toString(execTime/1000) + " µs");
					// InMemoryDB.displayIMDB(connection);
					connection.close();
				} catch (ClassNotFoundException | FileNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
			}
		});
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 468, Short.MAX_VALUE)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(28)
					.addComponent(lbl_user_id, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(txt_user_id, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(28)
					.addComponent(lbl_user_name)
					.addGap(12)
					.addComponent(txt_user_name, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(28)
					.addComponent(lbl_first_name, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(txt_first_name, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(28)
					.addComponent(lbl_last_name)
					.addGap(16)
					.addComponent(txt_last_name, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(28)
					.addComponent(lbl_gender)
					.addGap(36)
					.addComponent(txt_gender, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(28)
					.addComponent(lbl_password)
					.addGap(21)
					.addComponent(txt_password, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(28)
					.addComponent(lbl_status)
					.addGap(44)
					.addComponent(txt_status, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(43)
					.addComponent(button, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 546, Short.MAX_VALUE)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(15)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(4)
							.addComponent(lbl_user_id))
						.addComponent(txt_user_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(4)
							.addComponent(lbl_user_name))
						.addComponent(txt_user_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(4)
							.addComponent(lbl_first_name))
						.addComponent(txt_first_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(4)
							.addComponent(lbl_last_name))
						.addComponent(txt_last_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(4)
							.addComponent(lbl_gender))
						.addComponent(txt_gender, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(4)
							.addComponent(lbl_password))
						.addComponent(txt_password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(4)
							.addComponent(lbl_status))
						.addComponent(txt_status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
		);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "TIME ELAPSED", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(new Color(204, 255, 204));
		
		JLabel lbl_imdb_time = new JLabel("In Memory DataBase:");
		lbl_imdb_time.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		JLabel lbl_filesystem_time = new JLabel("File System:");
		lbl_filesystem_time.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		JButton button_2 = new JButton("Clear");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_user_id.setText("");
				txt_user_name.setText("");
				txt_first_name.setText("");
				txt_last_name.setText("");
				txt_gender.setText("");
				txt_password.setText("");
				txt_status.setText("");
				txt_imdb_time.setText("");
				txt_filesystem_time.setText("");
			}
		});
		
		JButton button_3 = new JButton("Exit");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		txt_filesystem_time = new JTextField();
		txt_filesystem_time.setEditable(false);
		txt_filesystem_time.setColumns(10);
		
		txt_imdb_time = new JTextField();
		txt_imdb_time.setEditable(false);
		txt_imdb_time.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 485, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(28)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_imdb_time)
						.addComponent(lbl_filesystem_time, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(button_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_3))
						.addComponent(txt_filesystem_time, Alignment.LEADING)
						.addComponent(txt_imdb_time, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
					.addGap(38))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 602, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(15)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_imdb_time)
						.addComponent(txt_imdb_time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_filesystem_time)
						.addComponent(txt_filesystem_time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_3, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(453, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panel_1, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 471, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(30, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(31, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_3 = new JPanel();
		tabbedPane.addTab("SEARCH", null, panel_3, null);
		getContentPane().setLayout(groupLayout);
		
		
	}
	}
