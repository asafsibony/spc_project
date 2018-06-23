package spc;

import server.mysqlConnection;

public class CheckInCheckOut {

	public String CheckInCarToDB(String id, String carId, String dep, String type, String parkingLot)
	{
		try {
			if(mysqlConnection.checkIfParkingLotIsFull(parkingLot))
				return "The parking lot is full, this lots have empty spots: " + mysqlConnection.getNotFullParkingLots();
			return mysqlConnection.CheckInCar(id, carId, dep, type, parkingLot);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
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
