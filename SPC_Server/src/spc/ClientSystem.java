package spc;

import server.mysqlConnection;

public class ClientSystem {

	public String addInAdvanceParkingToDB(String id, String carId, String arrivalDate, String arrivalHour,
			String depDate, String depHour, String parkingLot, String email) {
		try {
			double cost = calculatePriceInAdvance(arrivalDate, arrivalHour, depDate, depHour);
			return mysqlConnection.addInAdvanceOrder(id, carId, arrivalDate, arrivalHour, depDate, depHour, parkingLot, email, cost);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}

	}

	private double calculatePriceInAdvance(String arrivalDate, String arrivalHour, String depDate, String depHour) {
		//Read inAdvance price from DB
		System.out.println(arrivalDate+" "+arrivalHour+" "+depDate+" "+depHour+" ");
		return 10;
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

	public String cancelOrder(String id, String carId) {
		// TODO Auto-generated method stub
		return null;
	}

	public String complaint(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
