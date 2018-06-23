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
			return "SQL error";
		}

	}

	private double calculatePriceInAdvance(String arrivalDate, String arrivalHour, String depDate, String depHour) {
		System.out.println(arrivalDate+" "+arrivalHour+" "+depDate+" "+depHour+" ");
		return 10;
	}

	public Object addSubscriptionToDB(String id, String carsId, String date, String regOrbuis) {
		// TODO Auto-generated method stub
		return null;
	}

	public String cancelOrder(String id, String carId, String date, String hour) {
		// TODO Auto-generated method stub
		return null;
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
