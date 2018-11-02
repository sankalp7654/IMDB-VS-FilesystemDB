package views;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import common.FileSystemDB;
import common.InMemoryDB;

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
	private JTextField txt_search_user_id;
	private JTextField txt_search_filesystem_time;
	private JTextField txt_search_imdb_time;
	private JTextField txt_search_username;
	private JTextField txt_search_first_name;
	private JTextField txt_search_last_name;
	private JTextField txt_search_gender;
	private JTextField txt_search_password;
	private JTextField txt_search_status;

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
		setBounds(100, 100, 519, 646);
	
		
		
		// Creating In Memory DataBase Default Dataset
		Connection connection;
		try {
			connection = InMemoryDB.getConnection();
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
		
		JButton btn_insert_into_imdb = new JButton("INSERT INTO IMDB");
		btn_insert_into_imdb.addActionListener(new ActionListener() {
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
					Connection connection = InMemoryDB.getConnection();
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
		
		JButton btn_insert_into_filesystem = new JButton("INSERT INTO FILESYSTEM");
		btn_insert_into_filesystem.addActionListener(new ActionListener() {
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
					Connection connection = InMemoryDB.getConnection();
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
					.addComponent(btn_insert_into_imdb, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btn_insert_into_filesystem, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE))
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
						.addComponent(btn_insert_into_imdb, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_insert_into_filesystem, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)))
		);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "TIME ELAPSED", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBackground(new Color(204, 255, 204));
		
		JLabel lbl_imdb_time = new JLabel("In Memory DataBase:");
		lbl_imdb_time.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		JLabel lbl_filesystem_time = new JLabel("File System:");
		lbl_filesystem_time.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		JButton btn_clear = new JButton("Clear");
		btn_clear.addActionListener(new ActionListener() {
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
		
		JButton btn_exit = new JButton("Exit");
		btn_exit.addActionListener(new ActionListener() {
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
							.addComponent(btn_clear, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_exit))
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
						.addComponent(btn_exit, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_clear, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
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
		panel_3.setBackground(new Color(248, 248, 255));
		tabbedPane.addTab("SEARCH", null, panel_3, null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setSize(new Dimension(300, 100));
		panel_4.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "SEARCH A RECORD", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_4.setBackground(new Color(255, 239, 213));
		
		JLabel lbl_search_user_id = new JLabel("User ID:");
		lbl_search_user_id.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		txt_search_user_id = new JTextField();
		txt_search_user_id.setColumns(10);
		
		JButton btn_search_insert_in_imdb = new JButton("SEARCH IN IMDB");
		btn_search_insert_in_imdb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int userId = Integer.parseInt(txt_search_user_id.getText().toString());
				
					Connection connection;
					try {
						connection = InMemoryDB.getConnection();
						String [] record = InMemoryDB.search(connection, userId);
						if((record[8].compareTo("found")) == 0) {
							
							txt_search_username.setText(record[1]);
							txt_search_first_name.setText(record[2]);
							txt_search_last_name.setText(record[3]);
							txt_search_gender.setText(record[4]);
							txt_search_password.setText(record[5]);
							txt_search_status.setText(record[6]);
							txt_search_imdb_time.setText(record[7] + " µs");
							
							JOptionPane.showMessageDialog(null, "Record Found!", "Success", JOptionPane.INFORMATION_MESSAGE);

						}
						else {
							
							txt_search_username.setText("N/A");
							txt_search_first_name.setText("N/A");
							txt_search_last_name.setText("N/A");
							txt_search_gender.setText("N/A");
							txt_search_password.setText("N/A");
							txt_search_status.setText("N/A");
							txt_search_imdb_time.setText(record[7] + " µs");
							JOptionPane.showMessageDialog(null, "Record Not Found!", "Unsuccess", JOptionPane.ERROR_MESSAGE);
							
						}
						connection.close();
					} catch (ClassNotFoundException | FileNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, e1);
						e1.printStackTrace();
					}
	
				
				
				
			}
		});
		
		JButton btn_search_in_filesystemdb = new JButton("SEARCH IN FILESYSTEM");
		btn_search_in_filesystemdb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int userId = Integer.parseInt(txt_search_user_id.getText());
				String [] userDetails = new String[7];
				userDetails = FileSystemDB.search(userId);
				
				if(FileSystemDB.flag == true) {
					txt_search_username.setText(userDetails[1]);
					txt_search_first_name.setText(userDetails[2]);
					txt_search_last_name.setText(userDetails[3]);
					txt_search_gender.setText(userDetails[4]);
					txt_search_password.setText(userDetails[5]);
					txt_search_status.setText(userDetails[6]);
					txt_search_filesystem_time.setText(Long.toString(FileSystemDB.searchTime) + " µs");
					
					JOptionPane.showMessageDialog(null, "Record Found!", "Success", JOptionPane.INFORMATION_MESSAGE);

				
				}
				
			}
		});
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(32)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
								.addComponent(btn_search_in_filesystemdb)
								.addGroup(gl_panel_4.createSequentialGroup()
									.addComponent(lbl_search_user_id, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
									.addGap(12)
									.addComponent(txt_search_user_id, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(28)
							.addComponent(btn_search_insert_in_imdb, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(36, Short.MAX_VALUE))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(15)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(4)
							.addComponent(lbl_search_user_id))
						.addComponent(txt_search_user_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(btn_search_insert_in_imdb, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
						.addComponent(btn_search_in_filesystemdb, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel_4.setLayout(gl_panel_4);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "TIME ELAPSED", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_5.setBackground(new Color(204, 255, 204));
		
		JLabel lbl_search_imdb_time = new JLabel("In Memory DataBase:");
		lbl_search_imdb_time.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		JLabel lbl_search_filesystem_time = new JLabel("File System:");
		lbl_search_filesystem_time.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		JButton btn_search_clear = new JButton("Clear");
		btn_search_clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txt_search_user_id.setText("");
				txt_search_username.setText("");
				txt_search_first_name.setText("");
				txt_search_last_name.setText("");
				txt_search_gender.setText("");
				txt_search_status.setText("");
				txt_search_password.setText("");
				txt_search_imdb_time.setText("");
				txt_search_filesystem_time.setText("");
			}
		});
		
		JButton btn_search_exit = new JButton("Exit");
		btn_search_exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		txt_search_filesystem_time = new JTextField();
		txt_search_filesystem_time.setEditable(false);
		txt_search_filesystem_time.setColumns(10);
		
		txt_search_imdb_time = new JTextField();
		txt_search_imdb_time.setEditable(false);
		txt_search_imdb_time.setColumns(10);
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addGap(33)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_search_imdb_time)
						.addComponent(lbl_search_filesystem_time, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 68, Short.MAX_VALUE)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_panel_5.createSequentialGroup()
							.addComponent(btn_search_clear, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_search_exit))
						.addComponent(txt_search_filesystem_time, Alignment.LEADING)
						.addComponent(txt_search_imdb_time, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
					.addGap(33))
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap(240, Short.MAX_VALUE)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_search_imdb_time)
						.addComponent(txt_search_imdb_time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_search_filesystem_time)
						.addComponent(txt_search_filesystem_time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_search_exit, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(btn_search_clear, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addGap(18))
		);
		panel_5.setLayout(gl_panel_5);
		
		JPanel pnl_record_details = new JPanel();
		pnl_record_details.setBackground(new Color(230, 230, 250));
		pnl_record_details.setBorder(new TitledBorder(null, "RECORD DETAILS", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
						.addComponent(pnl_record_details, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
						.addComponent(panel_4, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 470, Short.MAX_VALUE)
						.addComponent(panel_5, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnl_record_details, GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JLabel lbl_search_username = new JLabel("User Name:");
		lbl_search_username.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		txt_search_username = new JTextField();
		txt_search_username.setEditable(false);
		txt_search_username.setColumns(10);
		
		JLabel lbl_search_first_name = new JLabel("First Name:");
		lbl_search_first_name.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		txt_search_first_name = new JTextField();
		txt_search_first_name.setEditable(false);
		txt_search_first_name.setColumns(10);
		
		txt_search_last_name = new JTextField();
		txt_search_last_name.setEditable(false);
		txt_search_last_name.setColumns(10);
		
		JLabel lbl_search_last_name = new JLabel("Last Name:");
		lbl_search_last_name.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		JLabel lbl_search_gender = new JLabel("Gender:");
		lbl_search_gender.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		txt_search_gender = new JTextField();
		txt_search_gender.setEditable(false);
		txt_search_gender.setColumns(10);
		
		txt_search_password = new JTextField();
		txt_search_password.setEditable(false);
		txt_search_password.setColumns(10);
		
		JLabel lbl_search_password = new JLabel("Password:");
		lbl_search_password.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		JLabel lbl_search_status = new JLabel("Status:");
		lbl_search_status.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		txt_search_status = new JTextField();
		txt_search_status.setEditable(false);
		txt_search_status.setColumns(10);
		GroupLayout gl_pnl_record_details = new GroupLayout(pnl_record_details);
		gl_pnl_record_details.setHorizontalGroup(
			gl_pnl_record_details.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_record_details.createSequentialGroup()
					.addGap(31)
					.addGroup(gl_pnl_record_details.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnl_record_details.createSequentialGroup()
							.addComponent(lbl_search_username, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(txt_search_username, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnl_record_details.createSequentialGroup()
							.addComponent(lbl_search_first_name, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
							.addGap(12)
							.addComponent(txt_search_first_name, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnl_record_details.createSequentialGroup()
							.addComponent(lbl_search_last_name, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
							.addGap(16)
							.addComponent(txt_search_last_name, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnl_record_details.createSequentialGroup()
							.addComponent(lbl_search_gender, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addGap(36)
							.addComponent(txt_search_gender, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnl_record_details.createSequentialGroup()
							.addComponent(lbl_search_password, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addGap(21)
							.addComponent(txt_search_password, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnl_record_details.createSequentialGroup()
							.addComponent(lbl_search_status, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addGap(44)
							.addComponent(txt_search_status, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(37, Short.MAX_VALUE))
		);
		gl_pnl_record_details.setVerticalGroup(
			gl_pnl_record_details.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_record_details.createSequentialGroup()
					.addGap(15)
					.addGroup(gl_pnl_record_details.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnl_record_details.createSequentialGroup()
							.addGap(4)
							.addComponent(lbl_search_username, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
						.addComponent(txt_search_username, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_pnl_record_details.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnl_record_details.createSequentialGroup()
							.addGap(4)
							.addComponent(lbl_search_first_name, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
						.addComponent(txt_search_first_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_pnl_record_details.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnl_record_details.createSequentialGroup()
							.addGap(4)
							.addComponent(lbl_search_last_name, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
						.addComponent(txt_search_last_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_pnl_record_details.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnl_record_details.createSequentialGroup()
							.addGap(4)
							.addComponent(lbl_search_gender, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
						.addComponent(txt_search_gender, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_pnl_record_details.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnl_record_details.createSequentialGroup()
							.addGap(4)
							.addComponent(lbl_search_password, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
						.addComponent(txt_search_password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addGroup(gl_pnl_record_details.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnl_record_details.createSequentialGroup()
							.addGap(4)
							.addComponent(lbl_search_status, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
						.addComponent(txt_search_status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(16, Short.MAX_VALUE))
		);
		pnl_record_details.setLayout(gl_pnl_record_details);
		panel_3.setLayout(gl_panel_3);
		getContentPane().setLayout(groupLayout);
		
		
	}
	}
