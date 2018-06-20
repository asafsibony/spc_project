package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


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

	public static void addUser(String user, String password)
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
		}catch(SQLException e) { /* ignored */}
	}
	
	public static String showUsers()
	{
		Statement stmt;
		String str = "";
		try 
		{
			stmt = conn.createStatement();
			PreparedStatement  ps = conn.prepareStatement("SELECT * FROM users;");
			ResultSet rs = ps.executeQuery();
	 		while(rs.next())
	 		{
	 			str+=(rs.getString(1)+'\n');
	 		} 
	 	    if (rs != null) {
	 	        try {
	 	            rs.close();
	 	        } catch (SQLException e) { /* ignored */}
	 	    }
	 	    if (stmt != null) {
	 	        try {
	 	            stmt.close();
	 	        } catch (SQLException e) { /* ignored */}
	 	    }
		} catch (SQLException e) {e.printStackTrace();}
		return str;
	}
}
