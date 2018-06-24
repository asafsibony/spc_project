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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.management.Query;

import javafx.util.Pair;

/**
 * Class for data base actions
 * @author scadaadmin
 *
 */
public class mysqlConnection {
	public static Connection conn;

	/**
	 * connect 
	 */
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

	/**
	 * add user to data base
	 * @param user
	 * @param password
	 * @return
	 */
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

	/**
	 * check if user exists in data base
	 * @param userName
	 * @param password
	 * @param type
	 * @return
	 */
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

	/**
	 * update logout column in data base table
	 * @param user
	 * @param type
	 * @return
	 */
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

	/**
	 * add car to carCheckedIn data base table
	 * @param id
	 * @param carId
	 * @param depHour
	 * @param type
	 * @param parkingLot
	 * @param depDate
	 * @throws SQLException
	 */
	public static void addToCarsCheckedInTable(String id, String carId, String depHour, String type, String parkingLot, String depDate) throws SQLException
	{
		Statement stmt;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
		Date now = new Date();
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet uprs = stmt.executeQuery("SELECT * FROM carsCheckedIn;");
		uprs.moveToInsertRow();
		uprs.updateString("CarID", carId);
		uprs.updateString("ID", id);
		uprs.updateString("ParkingType", type);
		uprs.updateString("EnterTime", format.format(now));
		uprs.updateString("DepartureAproxTime", depDate + ", " + depHour);
		uprs.updateString("ParkingLot", parkingLot);
		uprs.updateString("Email", "email@");
		uprs.insertRow();
		uprs.moveToCurrentRow();  
	}

	/**
	 * check out car and update data base
	 * @param id
	 * @param carId
	 * @param parkingLotName
	 * @param price
	 * @return
	 * @throws Exception
	 */
	public static String checkOutCar(String id, String carId, String parkingLotName, double price) throws Exception
	{
		conn.createStatement();
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM carsCheckedIn WHERE CarID = \"" + carId + "\" AND ID = \"" + id + "\"";
		ResultSet uprs = stmt.executeQuery(query);
		if (!uprs.next() ) {
			throw new Exception("Could not find the car.");
		} 
		else {
			uprs.deleteRow();
		}
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		query = "SELECT * FROM " + parkingLotName + " WHERE Availability = \"" + carId + "\"";
		uprs = stmt.executeQuery(query);
		while (uprs.next()) {
			uprs.updateString("Availability", "free");
			uprs.updateString("Deperture", "");
			uprs.updateRow();
		}
		return "true "+price;
	}

	/**
	 * get parking lot names from data base
	 * @return
	 */
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

	/**
	 * add parking lot info to data base
	 * @param name
	 * @param floors
	 * @param spaces
	 * @param availableSpots
	 * @param spotsInUse
	 * @throws NumberFormatException
	 * @throws SQLException
	 */
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

	/**
	 * construct parking lot in data base
	 * @param name
	 * @param floors
	 * @param spaces
	 * @throws SQLException
	 */
	public static void constructParkingLot(String name, String floors, String spaces) throws SQLException {
		System.out.println(name);
		String query = "CREATE TABLE " + name + " (Floor INTEGER not NULL, Row INTEGER not NULL, Availability VARCHAR(30) default \"free\", Deperture VARCHAR(30) default \"\", PRIMARY KEY (Floor,Row))";
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

	/**
	 * update spot as defect in data base
	 * @param parkingLot
	 * @param floor
	 * @param row
	 * @throws SQLException
	 */
	public static void registerDefectSpot(String parkingLot, String floor, String row) throws SQLException {
		conn.createStatement();
		String query="UPDATE " + parkingLot + " SET Availability = ? WHERE Floor = ? AND row = ?;";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, "defect");
		ps.setInt(2, Integer.parseInt(floor));
		ps.setInt(3, Integer.parseInt(row));
		ps.executeUpdate();
	}

	/**
	 * count avaliable spots
	 * @param parkingLot
	 * @throws SQLException
	 */
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

	/**
	 * check if spot is defect or not exists
	 * @param parkingLot
	 * @param floor
	 * @param row
	 * @return
	 * @throws SQLException
	 */
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

	/**
	 * check if spot is preserved or not exists
	 * @param parkingLot
	 * @param floor
	 * @param row
	 * @return
	 * @throws SQLException
	 */
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

	/**
	 * update spot as preserved
	 * @param parkingLot
	 * @param floor
	 * @param row
	 * @throws SQLException
	 */
	public static void registerPresrveSpot(String parkingLot, String floor, String row) throws SQLException {
		conn.createStatement();
		String query="UPDATE " + parkingLot + " SET Availability = ? WHERE Floor = ? AND row = ?;";
		PreparedStatement ps = conn.prepareStatement(query);
		ps.setString(1, "preserve");
		ps.setInt(2, Integer.parseInt(floor));
		ps.setInt(3, Integer.parseInt(row));
		ps.executeUpdate();
	}

	/**
	 * update price in data base
	 * @param priceType
	 * @param newPrice
	 * @throws SQLException
	 */
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

	/**
	 * add in advance order to data base
	 * @param id
	 * @param carId
	 * @param arrivalDate
	 * @param arrivalHour
	 * @param depDate
	 * @param depHour
	 * @param parkingLot
	 * @param email
	 * @param cost
	 * @return
	 * @throws SQLException
	 */
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

	/**
	 * add subscription order to data base
	 * @param id
	 * @param carId
	 * @param startDate
	 * @param regOrbuis
	 * @param cost
	 * @return
	 * @throws SQLException
	 */
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

	/**
	 * get subscription from data base
	 * @return
	 * @throws Exception
	 */
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

	/**
	 * cancel order
	 * @param id
	 * @param carId
	 * @param date
	 * @param hour
	 * @return
	 * @throws Exception
	 */
	public static String cancelOrder(String id, String carId, String date, String hour) throws Exception {
		conn.createStatement();
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM orderInAdvance WHERE CarID = \"" + carId + "\" AND ID = \"" + id + "\" AND ArrivalDate = \"" + date + "\" AND ArrivalHour = \"" + hour + "\"";
		ResultSet uprs = stmt.executeQuery(query);
		if (!uprs.next() ) {
			throw new Exception("Could not find the order. check your details.");
		} 
		else {
			double cost = uprs.getDouble("Cost");
			double refund = 0;
			String arrivalTime = date + ", " + hour;
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
			Date dateArr;
			dateArr = format.parse(arrivalTime);
			long diffInMillies = dateArr.getTime() - new Date().getTime();
			double numberOfminuets = TimeUnit.MINUTES.convert(diffInMillies,TimeUnit.MILLISECONDS);
			System.out.println(numberOfminuets);
			if(numberOfminuets>180)
				refund = cost - (cost * 0.1);
			else if((numberOfminuets<=180 && numberOfminuets>60))
				refund = cost - (cost * 0.5);
			else if((numberOfminuets<=60 && numberOfminuets>0))
				refund = 0;
			uprs.deleteRow();
			return "true " + Double.toString(refund);
		}
	}

	/**
	 * get in advance price from data base
	 * @return
	 * @throws Exception
	 */
	public static double getInAdvancePriceFromDB() throws Exception 
	{
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM prices WHERE Type = \"Order\"";
		ResultSet uprs = stmt.executeQuery(query);
		if (!uprs.next()) {
			throw new Exception("Could not get price from data base.");
		} 
		else 
		{
			String cost = uprs.getString("Price");
			return Double.parseDouble(cost);
		}
	}
	
	/**
	 * get casual price from data base
	 * @return
	 * @throws Exception
	 */
	public static double getCasualPriceFromDB() throws Exception 
	{
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM prices WHERE Type = \"Casual\"";
		ResultSet uprs = stmt.executeQuery(query);
		if (!uprs.next()) {
			throw new Exception("Could not get price from data base.");
		} 
		else 
		{
			String cost = uprs.getString("Price");
			return Double.parseDouble(cost);
		}
	}

	/**
	 * get client's orders from data base
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public static String viewOrders(String id) throws SQLException {
		String str = "";
		ResultSet rs;
		conn.createStatement();
		PreparedStatement  ps = conn.prepareStatement("SELECT * FROM orderInAdvance WHERE ID = \""+ id +"\";");
		rs = ps.executeQuery();
		str+="Orders In Advance:\n";
		str+=String.format("|%20s|", "CarID") + String.format("|%20s|", "ParkingLot") + String.format("|%20s|", "ArrivalDate") + String.format("|%20s|", "ArrivalHour")
		+ String.format("|%20s|", "DepartureAproxDate") + String.format("|%20s|", "DepartureAproxHour") + String.format("|%20s|", "Email")
		+ String.format("|%20s|", "Cost") + "\n";

		while(rs.next())
		{			
			str+=String.format("|%20s|", rs.getString("CarID")) + String.format("|%20s|", rs.getString("ParkingLot")) + String.format("|%20s|", rs.getString("ArrivalDate")) + String.format("|%20s|",  rs.getString("ArrivalHour"))
			+ String.format("|%20s|", rs.getString("DepartureAproxDate")) + String.format("|%20s|", rs.getString("DepartureAproxHour")) + String.format("|%20s|", rs.getString("Email"))
			+ String.format("|%20s|", rs.getString("Cost")) + "\n";
		} 
		str+="\n";
		ps = conn.prepareStatement("SELECT * FROM orderRegularSubscription WHERE ID = \""+ id +"\";");
		rs = ps.executeQuery();
		str+="Regular subscriptionts:\n";
		str+=String.format("|%20s|", "CarID") + String.format("|%20s|", "StartDate") + String.format("|%20s|", "EndDate") + String.format("|%20s|", "Cost") + "\n";		
		while(rs.next())
		{
			str+=String.format("|%20s|", rs.getString("CarID")) + String.format("|%20s|", rs.getString("StartDate")) + 
					String.format("|%20s|", rs.getString("EndDate")) + String.format("|%20s|",  rs.getString("Cost"))+ "\n";
		} 
		str+="\n";
		ps = conn.prepareStatement("SELECT * FROM orderBusinessSubscription WHERE ID = \""+ id +"\";");
		rs = ps.executeQuery();
		str+="Buisness subscriptiont:\n";
		str+=String.format("|%20s|", "CarID") + String.format("|%20s|", "StartDate") + String.format("|%20s|", "EndDate") + String.format("|%20s|", "Cost") + "\n";		
		while(rs.next())
		{
			str+=String.format("|%20s|", rs.getString("CarID")) + String.format("|%20s|", rs.getString("StartDate")) + 
					String.format("|%20s|", rs.getString("EndDate")) + String.format("|%20s|",  rs.getString("Cost"))+ "\n";
		} 

		System.out.println(str);
		return "true " + str;
	}

	/**
	 * add complaints to data base
	 * @param id
	 * @param complaint
	 * @return
	 * @throws SQLException
	 */
	public static String addComplaint(String id, String complaint) throws SQLException {
		conn.createStatement();
		String query = "INSERT INTO complains(ID, Content) VALUES(?,?)";
		PreparedStatement preparedStatement = conn.prepareStatement(query);
		preparedStatement.setString(1, id);
		preparedStatement.setString(2, complaint);
		preparedStatement .executeUpdate();
		return "true";
	}
	
	/**
	 * get parking lot table from data base
	 * @param parkingLot
	 * @return
	 * @throws SQLException
	 */
	public static String getParkingLotRowsFromDB(String parkingLot) throws SQLException 
	{
		String str = "";
		ResultSet rs;
		conn.createStatement();
		PreparedStatement  ps = conn.prepareStatement("SELECT * FROM " + parkingLot + ";");
		rs = ps.executeQuery();
		str+=String.format("|%20s|", "Floor") + String.format("|%20s|", "Row") + String.format("|%20s|", "Availability")+ "\n";			

		while(rs.next())
		{
			str+=String.format("|%20s|", rs.getString("Floor")) + String.format("|%20s|", rs.getString("Row")) + String.format("|%20s|", rs.getString("Availability")) + "\n";			
		} 
		return "true " + str;
	}

	/**
	 * get data for performance report
	 * @return
	 * @throws SQLException
	 */
	public static String getReport() throws SQLException 
	{
		ResultSet rs;
		conn.createStatement();
		PreparedStatement  ps = conn.prepareStatement("SELECT * FROM orderRegularSubscription;");
		rs = ps.executeQuery();
		rs.last();
		int regularSubsrows = rs.getRow();
		rs.beforeFirst();
		
		ResultSet rs2;
		conn.createStatement();
		PreparedStatement  ps2 = conn.prepareStatement("SELECT * FROM orderBusinessSubscription;");
		rs2 = ps2.executeQuery();
		rs2.last();
		int busSubsrows = rs2.getRow();
		rs2.beforeFirst();		
		int totalSubs = regularSubsrows + busSubsrows;
				
		ResultSet rs3;
		conn.createStatement();
		PreparedStatement  ps3 = conn.prepareStatement("SELECT ID FROM orderBusinessSubscription GROUP BY ID HAVING COUNT(*) > 1;");
		rs3 = ps3.executeQuery();
		
		rs3.last();
		int moreThanOnerows = rs3.getRow();
		rs3.beforeFirst();

		System.out.println("Total subscriptions = " + String.valueOf(totalSubs) + " and subscriptions with more than one car = " + String.valueOf(moreThanOnerows));
		return "true " + String.valueOf(totalSubs) + " " + String.valueOf(moreThanOnerows);	
	}
	
	/**
	 * check if parking lot is full
	 * @param parkingLot
	 * @return
	 * @throws Exception
	 */
	public static boolean checkIfParkingLotIsFull(String parkingLot) throws Exception {
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM parkingLots WHERE Name = \"" + parkingLot + "\"";
		ResultSet uprs = stmt.executeQuery(query);
		if (!uprs.next()) {
			throw new Exception("Parking lot not found.");
		} 
		else 
		{
			int availableSpots = uprs.getInt("AvailableSpots");
			int spotsInUse = uprs.getInt("SpotsInUse");
			if(availableSpots > spotsInUse)
				return false;
			else
				return true;
		}
	}

	/**
	 * get not full parking lots
	 * @return
	 * @throws SQLException
	 */
	public static String getNotFullParkingLots() throws SQLException {
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM parkingLots";
		ResultSet uprs = stmt.executeQuery(query);
		String alternatives = "";
		while (uprs.next()) {
			int available = uprs.getInt("AvailableSpots");
			int inUse = uprs.getInt("SpotsInUse");
			String name = uprs.getString("Name");
			if(available > inUse)
				alternatives+= (name + " ");
		}
		if (alternatives.equals(""))
			return ("All parking lot are full");
		return alternatives;
	}

	/**
	 * count used spots
	 * @param parkingLot
	 * @throws SQLException
	 */
	public static void countDownUsedSpots(String parkingLot) throws SQLException {
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM parkingLots WHERE Name = \"" + parkingLot + "\"";
		ResultSet uprs = stmt.executeQuery(query);
		while (uprs.next()) {
			int inUse = uprs.getInt("SpotsInUse");
			uprs.updateInt( "SpotsInUse", inUse-1);
			uprs.updateRow();
		}
	}

	/**
	 * check in using robot
	 * @param carId
	 * @param depHour
	 * @param parkingLot
	 * @param depDate
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static void RobotCheckIn(String carId, String depHour, String parkingLot, String depDate) throws SQLException, ParseException {
		//Transfer all spots info from DB to ArrayList
		ArrayList<Pair<String, Date>> spots=new ArrayList<Pair<String, Date>>();  
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM " + parkingLot;
		ResultSet uprs = stmt.executeQuery(query);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
		while (uprs.next()) {
			String status = uprs.getString("Availability");
			String deperture = uprs.getString("Deperture"); 
			if(!deperture.equals("")) {
				Date depertureDate = format.parse(deperture);
				spots.add(new Pair<String, Date>(status, depertureDate));
			} else {
				spots.add(new Pair<String, Date>(status, new Date()));
			}
		}
		//find first free space index
		int firstFreeSpot = 0;
		for (int i = 0; i < spots.size(); i++) {
			if (spots.get(i).getKey().equals("free")) {
				firstFreeSpot = i;
				break;
			}
		}		
		//Remove every thing after firstFreeSpot
		for (int j = firstFreeSpot; j < spots.size(); j++) {
			spots.remove(j);
			j--;
		}
		//Remove preserve and defects spots
		for (int j = 0; j < spots.size(); j++) {
			if (spots.get(j).getKey().equals("defect") || spots.get(j).getKey().equals("preserve")) {
				spots.remove(j);
				j--;
			}
		}
		// Insert New car
		String newDepTime = depDate + ", " + depHour;
		Date newDepTimeDate = format.parse(newDepTime);
		spots.add(new Pair<String, Date>(carId,newDepTimeDate));		
		// Sort
		Collections.sort(spots, new Comparator<Pair<String, Date>>() {
			public int compare(Pair<String, Date> o1, Pair<String, Date> o2) {
				return o1.getValue().compareTo(o2.getValue());
			}
		});			
		// Return Cars
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		query = "SELECT * FROM " + parkingLot;
		uprs = stmt.executeQuery(query);
		int i=0;
		while (uprs.next() && i!=spots.size()) {
			String status = uprs.getString("Availability");
			if(!status.equals("preserve") && !status.equals("defect")) {
				uprs.updateString("Availability", spots.get(i).getKey());
				uprs.updateString("Deperture", format.format(spots.get(i).getValue()));
				uprs.updateRow();
				i++;
			}
		}
	}

	/**
	 * 
	 * @param parkingLot
	 * @throws SQLException
	 */
	public static void countUpUsedSpots(String parkingLot) throws SQLException {
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM parkingLots WHERE Name = \"" + parkingLot + "\"";
		ResultSet uprs = stmt.executeQuery(query);
		while (uprs.next()) {
			int inUse = uprs.getInt("SpotsInUse");
			uprs.updateInt( "SpotsInUse", inUse+1);
			uprs.updateRow();
		}
	}

	/**
	 * get parking lot from data base
	 * @param id
	 * @param carId
	 * @return
	 * @throws Exception
	 */
	public static String getParkingLot(String id, String carId) throws Exception {
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM carsCheckedIn WHERE CarID = \"" + carId + "\" AND ID = \"" + id + "\"";
		ResultSet uprs = stmt.executeQuery(query);
		if (!uprs.next() ) {
			throw new Exception("Car was not found. check your details.");
		} else {
			String name = uprs.getString("ParkingLot");
			return name;
		}
	}

	/**
	 * check if user got subscription
	 * @param id
	 * @param carId
	 * @return
	 * @throws Exception
	 */
	public static boolean checkIfUserGotSubscription(String id, String carId) throws Exception {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String query = "SELECT * FROM orderBusinessSubscription WHERE CarID = \"" + carId + "\" AND ID = \"" + id + "\"";
			ResultSet uprs = stmt.executeQuery(query);
			if (!uprs.next() ) {
				return false;
			} else {
				String Start = uprs.getString("StartDate");
				String End = uprs.getString("EndDate");
				Date StartDate = format.parse(Start);
				Date EndDate = format.parse(End);
				Date now = new Date();
				if (now.getDate() >= StartDate.getDate() &&  now.getDate()<=EndDate.getDate())
					return true;
			}
			query = "SELECT * FROM orderRegularSubscription WHERE CarID = \"" + carId + "\" AND ID = \"" + id + "\"";
			uprs = stmt.executeQuery(query);
			if (!uprs.next() ) {
				return false;
			} else {
				String Start = uprs.getString("StartDate");
				String End = uprs.getString("EndDate");
				Date StartDate = format.parse(Start);
				Date EndDate = format.parse(End);
				Date now = new Date();
				if (now.getDate() >= StartDate.getDate() &&  now.getDate()<=EndDate.getDate())
					return true;
			}
			return false;
	}

	/**
	 * check if user got order
	 * @param id
	 * @param carId
	 * @return
	 * @throws Exception
	 */
	public static boolean checkIfUserGotOrder(String id, String carId) throws Exception {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM orderInAdvance WHERE CarID = \"" + carId + "\" AND ID = \"" + id + "\"";
		ResultSet uprs = stmt.executeQuery(query);
		if (!uprs.next() ) {
			return false;
		} else {
			String Start = uprs.getString("ArrivalDate");
			Date StartDate = format.parse(Start);
			Date now = new Date();
			if (now.getDay() == StartDate.getDay())
				return true;
		}
		return false;
	}

	/**
	 * check if user is late for deparure
	 * @param id
	 * @param carId
	 * @return
	 * @throws SQLException
	 * @throws ParseException
	 */
	public static boolean checkIfLateToDep(String id, String carId) throws SQLException, ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM orderInAdvance WHERE CarID = \"" + carId + "\" AND ID = \"" + id + "\"";
		ResultSet uprs = stmt.executeQuery(query);
		if (!uprs.next() ) {
			return false;
		} else {
			String depDay = uprs.getString("DepartureAproxDate");
			String depHour = uprs.getString("DepartureAproxHour");
			Date depDate = format.parse(depDay + ", " + depHour);
			Date now = new Date();
			if (now.getTime() > depDate.getTime())
				return true;
		}
		return false;
	}

	/**
	 * calculate check out price
	 * @param id
	 * @param carId
	 * @param type
	 * @param lateToDep
	 * @return
	 * @throws Exception
	 */
	public static double calculateCheckOutPrice(String id, String carId, String type, boolean lateToDep) throws Exception {
		
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		String query = "SELECT * FROM carsCheckedIn WHERE CarID = \"" + carId + "\" AND ID = \"" + id + "\"";
		ResultSet uprs = stmt.executeQuery(query);
		uprs.next();
		String EnterTime = uprs.getString("EnterTime");
		Date EnterTimeDate = format.parse(EnterTime);
		Date now = new Date();
		long minuets = (now.getTime() - EnterTimeDate.getTime()) / 60000;
		double hoursIn =  Math.ceil(minuets/60.0);
		if (type == "Subscription")
			return 0;		//Subscription paid in advance
		if (type == "Order") 
		{
			if(lateToDep)
			{
				double price = getInAdvancePriceFromDB();
				return (price*0.2)*hoursIn;		//financial penalty for being late
			}
			else
			{
				return 0;	//Orders paid in advance
			}
		}
		if (type == "Casual") {
			double price = getCasualPriceFromDB();
			return price*hoursIn;
		}
		return 0;
	}
}

