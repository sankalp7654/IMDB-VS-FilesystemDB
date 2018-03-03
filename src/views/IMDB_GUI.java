package views;
import java.awt.BorderLayout;
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
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import common.InMemoryDB;

public class IMDB_GUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnl_insert_record;
	private JPanel pnl_time_elapsed;
	private JTextField txt_user_name;
	private JTextField txt_first_name;
	private JTextField txt_last_name;
	private JTextField txt_user_id;
	private JTextField txt_gender;
	private JTextField txt_password;
	private JTextField txt_status;
	private JTextField txt_imdb_time;
	private JTextField txt_filesystem_time;
	private String [] userDetails = new String[5];

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
		setBounds(100, 100, 450, 574);
	
		
		
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
	
		
		pnl_insert_record = new JPanel();
		pnl_insert_record.setPreferredSize(new Dimension(200, 365));
		pnl_insert_record.setOpaque(false);
		pnl_insert_record.setBorder(new TitledBorder(null, "INSERT RECORD", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		getContentPane().add(pnl_insert_record, BorderLayout.NORTH);
		
		JLabel lbl_user_id = new JLabel("User ID:");
		lbl_user_id.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		JLabel lbl_user_name = new JLabel("User Name:");
		lbl_user_name.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		JLabel lbl_first_name = new JLabel("First Name:");
		lbl_first_name.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		JLabel lbl_last_name = new JLabel("Last Name:");
		lbl_last_name.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		JLabel lbl_gender = new JLabel("Gender:");
		lbl_gender.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		JLabel lbl_status = new JLabel("Status:");
		lbl_status.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		JLabel lbl_password = new JLabel("Password:");
		lbl_password.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
			
		txt_user_name = new JTextField();
		txt_user_name.setColumns(10);
		
		txt_first_name = new JTextField();
		txt_first_name.setColumns(10);
		
		txt_last_name = new JTextField();
		txt_last_name.setColumns(10);
		
		txt_user_id = new JTextField();
		txt_user_id.setColumns(10);
		
		txt_gender = new JTextField();
		txt_gender.setColumns(10);
		
		txt_password = new JTextField();
		txt_password.setColumns(10);
			
		txt_status = new JTextField();
		txt_status.setColumns(10);
		
		JButton btnInsertIntoImdb = new JButton("INSERT INTO IMDB");
		btnInsertIntoImdb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
					// Displaying exection time in milli-seconds 
					txt_imdb_time.setText(Long.toString(execTime/1000) + " µs");
					// InMemoryDB.displayIMDB(connection);
					connection.close();
				} catch (ClassNotFoundException | FileNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}				
				
			}
		});
		
		JButton btnInsertIntoFilesystem = new JButton("INSERT INTO FILESYSTEM");
		GroupLayout gl_panel = new GroupLayout(pnl_insert_record);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnInsertIntoImdb, GroupLayout.PREFERRED_SIZE, 168, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnInsertIntoFilesystem, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(28)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
									.addComponent(lbl_user_name, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lbl_user_id, GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE))
								.addComponent(lbl_first_name, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
								.addComponent(lbl_last_name)
								.addComponent(lbl_gender)
								.addComponent(lbl_password)
								.addComponent(lbl_status))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(txt_status, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
								.addComponent(txt_password, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
								.addComponent(txt_gender, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
								.addComponent(txt_user_id, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
								.addComponent(txt_last_name, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
								.addComponent(txt_first_name, 282, 282, Short.MAX_VALUE)
								.addComponent(txt_user_name, 282, 282, Short.MAX_VALUE))))
					.addGap(38))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(15)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_user_id)
						.addComponent(txt_user_id, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_user_name)
						.addComponent(txt_user_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_first_name)
						.addComponent(txt_first_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_last_name)
						.addComponent(txt_last_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_gender)
						.addComponent(txt_gender, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_password)
						.addComponent(txt_password, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_status)
						.addComponent(txt_status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnInsertIntoImdb, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnInsertIntoFilesystem, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
					.addGap(28))
		);
		pnl_insert_record.setLayout(gl_panel);
		
		pnl_time_elapsed = new JPanel();
		pnl_time_elapsed.setBackground(new Color(204, 255, 204));
		pnl_time_elapsed.setBorder(new TitledBorder(null, "TIME ELAPSED", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(pnl_time_elapsed, BorderLayout.CENTER);
		
		JLabel lbl_imdb_time = new JLabel("In Memory DataBase:");
		lbl_imdb_time.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		txt_imdb_time = new JTextField();
		txt_imdb_time.setEditable(false);
		txt_imdb_time.setColumns(10);
		
		JLabel lbl_file_system_time = new JLabel("File System:");
		lbl_file_system_time.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		
		txt_filesystem_time = new JTextField();
		txt_filesystem_time.setEditable(false);
		txt_filesystem_time.setColumns(10);
		
		JButton btnNewButton = new JButton("Exit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		JButton btnNewButton_1 = new JButton("Clear");
		btnNewButton_1.addActionListener(new ActionListener() {
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
		GroupLayout gl_pnl_time_elapsed = new GroupLayout(pnl_time_elapsed);
		gl_pnl_time_elapsed.setHorizontalGroup(
			gl_pnl_time_elapsed.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnl_time_elapsed.createSequentialGroup()
					.addGap(28)
					.addGroup(gl_pnl_time_elapsed.createParallelGroup(Alignment.LEADING)
						.addComponent(lbl_imdb_time)
						.addComponent(lbl_file_system_time, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
					.addGroup(gl_pnl_time_elapsed.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_pnl_time_elapsed.createSequentialGroup()
							.addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton))
						.addComponent(txt_filesystem_time, Alignment.LEADING)
						.addComponent(txt_imdb_time, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
					.addGap(38))
		);
		gl_pnl_time_elapsed.setVerticalGroup(
			gl_pnl_time_elapsed.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl_time_elapsed.createSequentialGroup()
					.addGap(15)
					.addGroup(gl_pnl_time_elapsed.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_imdb_time)
						.addComponent(txt_imdb_time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnl_time_elapsed.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbl_file_system_time)
						.addComponent(txt_filesystem_time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnl_time_elapsed.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnl_time_elapsed.setLayout(gl_pnl_time_elapsed);
		
		JMenuBar menuBar = new JMenuBar();	
		setJMenuBar(menuBar);
		
		
	}
	}
