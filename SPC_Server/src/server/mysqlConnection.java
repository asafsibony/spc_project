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
	
	public static String checkIfUserExists(String userName, String password, String type)
	{
		try 
		{
			String query="";
			conn.createStatement();
			if(type.equals("client"))
				query = "SELECT * FROM users WHERE User = ? AND Password = ?;";
			else if (type.equals("admin"))
				query = "SELECT * FROM admins WHERE User = ? AND Password = ?;";
			PreparedStatement  ps = conn.prepareStatement(query);
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
					if(type.equals("client"))
						query = "UPDATE users SET Logged = ? WHERE User = ? AND Password = ?;";
					else if (type.equals("admin"))
						query = "UPDATE admins SET Logged = ? WHERE User = ? AND Password = ?;";
					PreparedStatement  ps2 = conn.prepareStatement(query);
					ps2.setBoolean(1, true);
					ps2.setString(2, userName);
					ps2.setString(3, password);
					ps2.executeUpdate();
					return "true";
				}
			}
		}
		catch(SQLException e) {
			System.out.println(e.getMessage());
			return "SQL failure.";}
	}

	public static String logout(String user, String type) {
		try {
			conn.createStatement();
			String query="";
			if(type.equals("client"))
				query = "UPDATE users SET Logged = ? WHERE User = ?;";
			else if (type.equals("admin"))
				query = "UPDATE admins SET Logged = ? WHERE User = ?;";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setBoolean(1, false);
			ps.setString(2, user);
			int res = ps.executeUpdate();
			if (res==0)
				return "Logout: the user: '" + user + "' not found";
			else
				return "true";
		} catch (SQLException e) {
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
