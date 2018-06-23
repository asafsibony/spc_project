package spc;

import java.sql.SQLException;

import server.mysqlConnection;

public class AdminSystem {

	public String registerDefectSpot(String parkingLot, String floor, String row) {
		try {
			boolean res = mysqlConnection.checkIfAlreadyDefectOrNotExist(parkingLot, floor, row);
			if (res == true) {
				return "The parking spot already defiened as defected or not exist.";
			}
			mysqlConnection.registerDefectSpot(parkingLot, floor, row);
			mysqlConnection.countDownAvailableSpots(parkingLot);
			return "true";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	public String preserveSpot(String parkingLot, String floor, String row) {
		try {
			boolean res = mysqlConnection.checkIfAlreadyPreserveOrNotExist(parkingLot, floor, row);
			if (res == true) {
				return "The parking spot already defiened as Preserve or not exist.";
			}
			mysqlConnection.registerPresrveSpot(parkingLot, floor, row);
			mysqlConnection.countDownAvailableSpots(parkingLot);
			return "true";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	public String updatePrices(String priceType, String newPrice) {
		try {
			mysqlConnection.updatePrice(priceType, newPrice);
			return "true";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	public String produceSnapShot(String parkingLot, String spot) {
		// TODO Auto-generated method stub
		return null;
	}

	public String producePerformanceReport() {
		// TODO Auto-generated method stub
		return null;
	}

	public String addNewParkingLotToDB(String name, String floors, String spaces, String availableSpots, String SpotsInUse) {
		try {
			mysqlConnection.addParkingLotInfo(name, floors, spaces, availableSpots, SpotsInUse);
			mysqlConnection.constructParkingLot(name, floors, spaces);
			return "true";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

}
