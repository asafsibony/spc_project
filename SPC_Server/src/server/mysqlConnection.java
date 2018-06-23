package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

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

	public static String CheckInCar(String id, String carId, String dep, String type)
	{
		Statement stmt;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime now = LocalTime.now();
			String f = formatter.format(now);
		
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			ResultSet uprs = stmt.executeQuery("SELECT * FROM carsCheckedIn;");

			uprs.moveToInsertRow();
			uprs.updateString("CarID", carId);
			uprs.updateString("ID", id);
			uprs.updateString("ParkingType", type);
			uprs.updateString("EnterTime", f);
			uprs.updateString("DepartureAproxTime", dep);  // לפי הסיפור לקוח מנוי יכול לחנות עד 14 שעות ברצף. בנוסף את זמן העזיבה המשוער עבור לקוח שהזמין מראש צריך לקחת מההזמנה שלו. 
			uprs.updateString("ParkingLot", "parkingLot");
			uprs.updateString("Email", "email@");

			uprs.insertRow();
			uprs.moveToCurrentRow();  
			return "Car checked in Successfuly";

		}catch(SQLException e)
		{ 
			System.out.println(e.getMessage());
			return "Failure, could not check in car.";
		}
	}

	public static String getParkingCostCheckOut(String id, String carId)
	{
		Statement stmt;
		try {
			double cost;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			LocalTime now = LocalTime.now();
			String f = formatter.format(now);
			String query="";
			
			conn.createStatement();
			query = "SELECT * FROM carsCheckedIn WHERE ID = ? AND CarID = ?;";
			PreparedStatement  ps = conn.prepareStatement(query);
			ps.setString(1, id);
			ps.setString(2, carId);

			ResultSet rs = ps.executeQuery();
			if(!rs.next())
			{
				return "Car not found";
			}

			String enterTime = rs.getString("EnterTime");
			String type = rs.getString("ParkingType");

			LocalTime enterTimeObj = LocalTime.parse(enterTime);
			double numberOfHours = ChronoUnit.HOURS.between(enterTimeObj, now);
			System.out.println("total hours = " + numberOfHours);

			if(type.equals("Casual"))
			{
				cost = 5*numberOfHours;
			}
			else if(type.equals("Order"))
			{
				cost = 4*numberOfHours;
			}
			else
			{
				cost = 0;
			}
			
			String costStr = String.valueOf(cost);
			return costStr + " NIS";

		}catch(SQLException e)
		{ 
			System.out.println(e.getMessage());
			return "Failure, calculate check out cost.";
		}
	}

	public static String getParkingLotsNames() {
		Statement stmt;
		String str = "";
		try 
		{
			stmt = conn.createStatement();
			PreparedStatement  ps = conn.prepareStatement("SELECT * FROM parkingLots;");
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				str+=(rs.getString(1).replace(" ", "_")+" ");
			} 
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			return "SQL Error at getParkingLotsNames()";
			}
		return str;
	}

	public static void addParkingLotInfo(String name, String floors, String spaces, String availableSpots,
			String spotsInUse) throws NumberFormatException, SQLException {
		conn.createStatement();
		String query = "INSERT INTO parkingLots"
				+ "(Name, Floors, Rows, AvailableSpots, SpotsInUse) VALUES"
				+ "(?,?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, name);
		preparedStatement.setInt(2, Integer.parseInt(floors));
		preparedStatement.setInt(3, Integer.parseInt(spaces));
		preparedStatement.setInt(4, Integer.parseInt(availableSpots));
		preparedStatement.setInt(5, Integer.parseInt(spotsInUse));
		preparedStatement .executeUpdate();
	}

	public static void constructParkingLot(String name, String floors, String spaces) throws SQLException {
		System.out.println(name);
	    String query = "CREATE TABLE " + name + " (Floor INTEGER not NULL, Row INTEGER not NULL, Availability VARCHAR(30) default \"free\", PRIMARY KEY (Floor,Row))";
	    PreparedStatement preparedStatement = conn.prepareStatement(query);
	    preparedStatement.executeUpdate();

	    for(int i=0; i<Integer.parseInt(floors); i++) {
	    	for(int j=0; j<Integer.parseInt(spaces); j++) {
	    		query = "INSERT INTO " + name + "(Floor, Row) VALUES(?,?)";
	    		preparedStatement = conn.prepareStatement(query);
	    		preparedStatement.setInt(1, i+1);
	    		preparedStatement.setInt(2, j+1);
	    		preparedStatement .executeUpdate();
	    	}
	    }

		
	}

	public static void registerDefectSpot(String parkingLot, String floor, String row) throws SQLException {
		conn.createStatement();
		String query="UPDATE " + parkingLot + " SET Availability = ? WHERE Floor = ? AND row = ?;";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, "defect");
		ps.setInt(2, Integer.parseInt(floor));
		ps.setInt(3, Integer.parseInt(row));
		ps.executeUpdate();
	}

	public static void countDownAvailableSpots(String parkingLot) throws SQLException {
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM parkingLots WHERE Name = \"" + parkingLot + "\"";
        ResultSet uprs = stmt.executeQuery(query);
        while (uprs.next()) {
            int available = uprs.getInt("AvailableSpots");
            uprs.updateInt( "AvailableSpots", available-1);
            uprs.updateRow();
        }
	}

	public static boolean checkIfAlreadyDefectOrNotExist(String parkingLot, String floor, String row) throws SQLException {
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM " + parkingLot + " WHERE Floor = " + floor + " AND Row = " + row;
		ResultSet uprs = stmt.executeQuery(query);
		if (!uprs.next() ) {
		    return true;
		} 
		else {
			String availablilty = uprs.getString("Availability");
			if (availablilty.equals("defect"))
				return true;
			else
				return false;
		}
	}

	public static boolean checkIfAlreadyPreserveOrNotExist(String parkingLot, String floor, String row) throws SQLException {
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM " + parkingLot + " WHERE Floor = " + floor + " AND Row = " + row;
		ResultSet uprs = stmt.executeQuery(query);
		if (!uprs.next() ) {
		    return true;
		} 
		else {
			String availablilty = uprs.getString("Availability");
			if (availablilty.equals("preserve"))
				return true;
			else
				return false;
		}
	}

	public static void registerPresrveSpot(String parkingLot, String floor, String row) throws SQLException {
		conn.createStatement();
		String query="UPDATE " + parkingLot + " SET Availability = ? WHERE Floor = ? AND row = ?;";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, "preserve");
		ps.setInt(2, Integer.parseInt(floor));
		ps.setInt(3, Integer.parseInt(row));
		ps.executeUpdate();
	}

	public static void updatePrice(String priceType, String newPrice) throws SQLException {
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM prices WHERE Type = \"" + priceType + "\"";
        ResultSet uprs = stmt.executeQuery(query);
        while (uprs.next()) {
            double available = uprs.getDouble("Price");
            uprs.updateDouble( "Price", Double.parseDouble(newPrice));
            uprs.updateRow();
        }
	}

	public static String addInAdvanceOrder(String id, String carId, String arrivalDate, String arrivalHour,
			String depDate, String depHour, String parkingLot, String email, double cost) throws SQLException {
		conn.createStatement();
		String query = "INSERT INTO orderInAdvance(CarID, ID, ParkingLot, ArrivalDate, ArrivalHour, DepartureAproxDate, DepartureAproxHour, Email, Cost) VALUES(?,?,?,?,?,?,?,?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, carId);
		preparedStatement.setString(2, id);
		preparedStatement.setString(3, parkingLot);
		preparedStatement.setString(4, arrivalDate);
		preparedStatement.setString(5, arrivalHour);
		preparedStatement.setString(6, depDate);
		preparedStatement.setString(7, depHour);
		preparedStatement.setString(8, email);
		preparedStatement.setDouble(9, cost);
		preparedStatement .executeUpdate();
		return "true " + cost;
	}

	public static String addSubscriptionOrder(String id, String carId, String startDate, String regOrbuis, double cost) throws SQLException {
		LocalDate start = LocalDate.parse(startDate);
		String endDate = start.plusDays(28).toString();
		conn.createStatement();
		String query="";
		if (regOrbuis.equals("Regular")) {
			query = "INSERT INTO orderRegularSubscription(ID, CarID, StartDate, EndDate, Cost) VALUES(?,?,?,?,?)";
		} else if (regOrbuis.equals("Buisness")) {
			query = "INSERT INTO orderBusinessSubscription(ID, CarID, StartDate, EndDate, Cost) VALUES(?,?,?,?,?)";
		}
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, id);
		preparedStatement.setString(2, carId);
		preparedStatement.setString(3, startDate);
		preparedStatement.setString(4, endDate);
		preparedStatement.setDouble(5, cost);
		preparedStatement .executeUpdate();
		return "true " + cost;
	}

	public static double getSubscriptionPriceFromDB() throws Exception {
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM prices WHERE Type = \"Subscription\"";
		ResultSet uprs = stmt.executeQuery(query);
		if (!uprs.next()) {
		    throw new Exception("Could not get price from DB");
		} 
		else {
			String cost = uprs.getString("Price");
			return Double.parseDouble(cost);
		}
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
