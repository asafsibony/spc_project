package spc;

import server.mysqlConnection;

public class CheckInCheckOut {

	public String CheckInCarToDB(String id, String carId, String dep, String type)
	{

		return mysqlConnection.CheckInCar(id, carId, dep, type);
	}

	public String submitCheckOut(String id, String carId) 
	{
		return mysqlConnection.getParkingCostCheckOut(id, carId);
	}

	public String removeCarFromDB(String id, String carId) {
		// TODO Auto-generated method stub
		return null;
	}

}
