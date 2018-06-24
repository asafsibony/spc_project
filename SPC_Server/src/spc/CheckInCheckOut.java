package spc;

import server.mysqlConnection;

/**
 * Server side checkInCheckOut
 * @author scadaadmin
 *
 */
public class CheckInCheckOut {

	/**
	 * check in car to data base
	 * @param id
	 * @param carId
	 * @param dep
	 * @param type
	 * @param parkingLot
	 * @param depDate
	 * @return
	 */
	public String CheckInCarToDB(String id, String carId, String dep, String type, String parkingLot, String depDate)
	{
		try {
			if(type.equals("Order"))
				if(!mysqlConnection.checkIfUserGotOrder(id, carId))
					return "Sorry, we didn't find your order.";
			if(type.equals("Subscription"))
				if(!mysqlConnection.checkIfUserGotSubscription(id, carId))
					return "Sorry, we didn't find your subscription.";
			if(mysqlConnection.checkIfParkingLotIsFull(parkingLot))
				return "The parking lot is full. Alternative available lots: " + mysqlConnection.getNotFullParkingLots();
			mysqlConnection.addToCarsCheckedInTable(id, carId, dep, type, parkingLot, depDate);
			mysqlConnection.countUpUsedSpots(parkingLot);
			mysqlConnection.RobotCheckIn(carId, dep, parkingLot, depDate);
			return "true";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	/**
	 * submit check out
	 * @param id
	 * @param carId
	 * @return
	 */
	public String submitCheckOut(String id, String carId) 
	{
		try {
			String type="Casual";
			boolean lateToDep = false;
			String parkingLotName = mysqlConnection.getParkingLot(id, carId);
			if(mysqlConnection.checkIfUserGotSubscription(id, carId))
				type = "Subscription";
			else if(mysqlConnection.checkIfUserGotOrder(id, carId)) {
				type = "Order";
				lateToDep = mysqlConnection.checkIfLateToDep(id, carId);
			}
			double price = mysqlConnection.calculateCheckOutPrice(id, carId, type, lateToDep);
			mysqlConnection.countDownUsedSpots(parkingLotName);
			return mysqlConnection.checkOutCar(id, carId, parkingLotName, price);
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}

}
