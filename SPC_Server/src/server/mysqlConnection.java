package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.management.Query;


public class mysqlConnection {
	public static Connection conn;
	
	public mysqlConnection() {
		super();
		try 
		{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {/* handle the error*/}
        
        try 
        {
        	String user = "cs302958467";
        	String password = "d9e57569";
        	String url = "jdbc:mysql://cs.telhai.ac.il/Group_8";
        	conn = DriverManager.getConnection(url,user,password);
            System.out.println("SQL connection succeed");
     	} catch (SQLException ex) 
     	    {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
     	    }
	}

	public static String addUser(String user, String password)
	{
		Statement stmt;
		try {
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet uprs = stmt.executeQuery("SELECT * FROM users;");
			uprs.moveToInsertRow();
			uprs.updateString("User", user);
			uprs.updateString("Password", password);
			uprs.insertRow();
			uprs.moveToCurrentRow();  
			return "User added Successfuly";
		}catch(SQLException e) { return "Failure, try other user name.";}
	}
	
	public static String checkIfUserExists(String userName, String password, String clientOrAdmin)
	{
		Statement stmt;
		Statement stmt2;
		String str = "";
		try 
		{
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			PreparedStatement  ps = conn.prepareStatement("SELECT * FROM users WHERE User = ? AND Password = ?;");
			ps.setString(1, userName);
			ps.setString(2, password);
			
			ResultSet rs = ps.executeQuery();
			if(!rs.next())
			{
				return "User not found";
			}
			else
			{
				if(rs.getBoolean("Logged") == true) {
					return "User already logged in";
				}
				else
				{
					stmt2 = conn.createStatement();
					PreparedStatement  ps2 = conn.prepareStatement("UPDATE users SET Logged = ? WHERE WHERE User = ? AND Password = ?;");
					ps2.setBoolean(1, true);
					ps2.setString(2, userName);
					ps2.setString(3, password);
					ps2.executeUpdate();
//					rs.last();
//					rs.updateBoolean("Logged", true);
//					rs.updateRow();
					return "true";
				}
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			return "SQL failure.";}
	}
	
//	public static String showUsers()
//	{
//		Statement stmt;
//		String str = "";
//		try 
//		{
//			stmt = conn.createStatement();
//			PreparedStatement  ps = conn.prepareStatement("SELECT * FROM users;");
//			ResultSet rs = ps.executeQuery();
//	 		while(rs.next())
//	 		{
//	 			str+=(rs.getString(1)+'\n');
//	 		} 
//	 	    if (rs != null) {
//	 	        try {
//	 	            rs.close();
//	 	        } catch (SQLException e) { /* ignored */}
//	 	    }
//	 	    if (stmt != null) {
//	 	        try {
//	 	            stmt.close();
//	 	        } catch (SQLException e) { /* ignored */}
//	 	    }
//		} catch (SQLException e) {e.printStackTrace();}
//		return str;
//	}
}
