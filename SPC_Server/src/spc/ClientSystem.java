package spc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import server.mysqlConnection;

public class ClientSystem {


	public String addInAdvanceParkingToDB(String id, String carId, String arrivalDate, String arrivalHour,
			String depDate, String depHour, String parkingLot, String email) {
		try {
			double cost = calculatePriceInAdvance(arrivalDate, arrivalHour, depDate, depHour);
			if(cost == -1) throw new Exception("Could not calculte in advance parking cost.");
			return mysqlConnection.addInAdvanceOrder(id, carId, arrivalDate, arrivalHour, depDate, depHour, parkingLot, email, cost);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "SQL error";
		}
	}

	private double calculatePriceInAdvance(String arrivalDate, String arrivalHour, String depDate, String depHour) throws Exception
	{
		double inAdvPrice = mysqlConnection.getInAdvancePriceFromDB();
		double cost;

		String arr = arrivalDate + ", " + arrivalHour;
		String dep = depDate + ", " + depHour;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd, HH:mm");
		Date dateArr;
		Date dateDep;
		try 
		{
			dateArr = format.parse(arr);
			dateDep = format.parse(dep);
			long diffInMillies = dateDep.getTime() - dateArr.getTime();
			double numberOfhours = TimeUnit.HOURS.convert(diffInMillies,TimeUnit.MILLISECONDS);
			cost = numberOfhours*inAdvPrice;
			return cost;

		} catch (ParseException e) 
		{
			e.printStackTrace();
			return -1;
		}
	}

	public String addSubscriptionToDB(String id, String carId, String date, String regOrbuis) {
		try {
			double cost = mysqlConnection.getSubscriptionPriceFromDB();
			return mysqlConnection.addSubscriptionOrder(id, carId, date, regOrbuis, cost);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	public String cancelOrder(String id, String carId, String date, String hour) {
		try {
			return mysqlConnection.cancelOrder(id, carId, date, hour);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	public String viewlOrders(String id) {
		try {
			return mysqlConnection.viewOrders(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}
	
	public String complaint(String id) {
		// TODO Auto-generated method stub
		return null;
	}


}
