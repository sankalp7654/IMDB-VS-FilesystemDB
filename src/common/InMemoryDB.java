package common;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import javax.swing.JOptionPane;

// H2 In-Memory Database Example shows about storing the database contents into memory.

public class InMemoryDB {

    private static String url = "jdbc:h2:mem:student;DB_CLOSE_DELAY=-1";
    private static String username = "";
    private static String password = "";
    public static long createTime, searchTime;
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException, FileNotFoundException {
     	Class.forName("org.h2.Driver");	
   		Connection connection =  DriverManager.getConnection(url, username, password);
   		return connection;
    }
    
   public static void createDatabase(Connection connection) throws ClassNotFoundException, SQLException, FileNotFoundException {
	    
	   //start 
	    long startCreateTime = System.nanoTime();

	    String CreateDB = "CREATE TABLE user_details (user_id int(11) NOT NULL AUTO_INCREMENT, user_name varchar(255) DEFAULT NULL, first_name varchar(50) DEFAULT NULL, last_name varchar(50) DEFAULT NULL, gender varchar(10) DEFAULT NULL, password varchar(50) DEFAULT NULL, status tinyint(10) DEFAULT NULL, PRIMARY KEY (user_id) ) AUTO_INCREMENT=10001";        		
    			
	    // Insert Default Data using File InsertDefaultData.txt
	    @SuppressWarnings("resource")
	    String InsertDefaultData = new Scanner(new File("/Users/Sandeep/Documents/eclipse-workspace/IMDB-VS-FilesystemDB-master/src/resources/InsertDefaultData.txt")).useDelimiter("\\A").next();
    		       
    			
	    PreparedStatement createTable = connection.prepareStatement(CreateDB);		
	    createTable.executeUpdate();
	    PreparedStatement insertDataToDB = connection.prepareStatement(InsertDefaultData);
            
	    System.out.println(insertDataToDB.executeUpdate());
    
	    long endCreateTime = System.nanoTime();
    
	    //time elapsed
	    createTime = endCreateTime - startCreateTime;
  
	    System.out.println("Elapsed time in nanoseconds: " + createTime);
	    connection.commit();  
    }
   
   public static void displayIMDB(Connection connection) throws SQLException {
       String SelectQuery = "select top 100* from user_details ";
       PreparedStatement selectPreparedStatement = null;
       selectPreparedStatement = connection.prepareStatement(SelectQuery);
       ResultSet rs = selectPreparedStatement.executeQuery();
       System.out.println("H2 In-Memory Database inserted through PreparedStatement");
       System.out.println("UserId	" +  "UserName	" + "FirstName	" + "LastName	" + "Gender   " + "Password   " + "Status   "); 
       while (rs.next()) {
       System.out.println(rs.getInt(1) + "  "+ rs.getString(2) + "  "+ rs.getString(3) + "  " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getShort(7));
       }
       
       connection.commit();
       selectPreparedStatement.close();
   }
    
    public static long insertIntoIMDB(Connection connection, String [] userDetails, int userId, int status) throws SQLException, FileNotFoundException, ClassNotFoundException {
  
       String InsertQuery = "INSERT INTO user_details (user_id, user_name, first_name, last_name, gender, password, status) values (?,?,?,?,?,?,?)";
    
        try {
        			long startTime = System.nanoTime();
        			connection.setAutoCommit(false);
  
        			PreparedStatement insertPreparedStatement = null; 
 
        			insertPreparedStatement = connection.prepareStatement(InsertQuery);
        			insertPreparedStatement.setInt(1, userId);
        			insertPreparedStatement.setString(2, userDetails[0]);
        			insertPreparedStatement.setString(3, userDetails[1]);
        			insertPreparedStatement.setString(4, userDetails[2]);
        			insertPreparedStatement.setString(5, userDetails[3]);
        			insertPreparedStatement.setString(6, userDetails[4]);
        			insertPreparedStatement.setInt(7, status);
        			insertPreparedStatement.executeUpdate();
        			System.out.println("Inserted");
        			insertPreparedStatement.close();
        			long endTime = System.nanoTime();
            
        			String SelectQuery = "SELECT TOP 1 * FROM user_details ORDER BY user_id DESC ";
        			PreparedStatement selectPreparedStatement = null;
         
        			selectPreparedStatement = connection.prepareStatement(SelectQuery);
        			ResultSet rs = selectPreparedStatement.executeQuery();
        			System.out.println("H2 In-Memory Database inserted through PreparedStatement");
        			System.out.println("UserId	" +  "UserName	" + "FirstName	" + "LastName	" + "Gender   " + "Password   " + "Status   "); 
        			
        			while (rs.next()) {  
        				System.out.println(rs.getInt(1) + "  "+ rs.getString(2) + "  "+ rs.getString(3) + "  " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getInt(7));
        			}
        			selectPreparedStatement.close();
         
        			connection.commit();
        			System.out.println("Execution Time: " + (endTime-startTime) + " ns");
        			return (endTime-startTime); 
        } catch (SQLException e) {
        		JOptionPane.showMessageDialog(null, e.getLocalizedMessage());
        		System.out.println("Exception Message " + e.getLocalizedMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    		return 0l; 
        
    }

   
     public static String[] search(Connection connection, int user_id) throws SQLException {

    	  	//start 
 	    long startTime = System.nanoTime();

         PreparedStatement searchPreparedStatement = null;
         String SearchQuery = "select * from user_details where user_id = ? ";
     	 searchPreparedStatement = connection.prepareStatement(SearchQuery);
     	 searchPreparedStatement.setInt(1, user_id);
     	 
         ResultSet rs = searchPreparedStatement.executeQuery();
		 rs.next();
		 
		 String record[] = new String[9];
		 record[8] = "not found";
		 
         try {
				 if(rs.getInt("user_id") == user_id) {
					 // Here capacity of array is 9 (first 7 columns for details of record, 8th column for time elapsed and 9th column is to store status of search)
			         record[0] = rs.getString(1);
			         record[1] = rs.getString(2);
			         record[2] = rs.getString(3);
			         record[3] = rs.getString(4);
			         record[4] = rs.getString(5);
			         record[5] = rs.getString(6);
			         record[6] = rs.getString(7);
			         record[8] = "found";
			        	 System.out.println(rs.getInt(1) + "  "+ rs.getString(2) + "  "+ rs.getString(3) + "  " + rs.getString(4) + " " + rs.getString(5) + " " + rs.getString(6) + " " + rs.getInt(7));
			 	}
				 long endTime = System.nanoTime();
		         
			 	    //time elapsed
			 	    searchTime = endTime - startTime;
			 	    // contains converted time in µs
			 	    record[7] = Long.toString(searchTime/1000);
			 	    System.out.println("Elapsed time in µs: " + (searchTime/1000));
			 	   
			        searchPreparedStatement.close();
			        return (record);
		} catch (Exception e) {
			System.out.println("No data found");
			//			e.printStackTrace();
		}
        
         return record;
    }
   
}
