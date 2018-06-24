package spc;

import java.sql.SQLException;

import server.mysqlConnection;

/**
 * Server side admin system
 * @author scadaadmin
 *
 */
public class AdminSystem {

	/**
	 * register spot as defect 
	 * @param parkingLot
	 * @param floor
	 * @param row
	 * @return
	 */
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

	/**
	 * preserve spot 
	 * @param parkingLot
	 * @param floor
	 * @param row
	 * @return
	 */
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

	/**
	 * update prices
	 * @param priceType
	 * @param newPrice
	 * @return
	 */
	public String updatePrices(String priceType, String newPrice) {
		try {
			mysqlConnection.updatePrice(priceType, newPrice);
			return "true";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	/**
	 * produce snapshot
	 * @param parkingLot
	 * @return
	 * @throws SQLException
	 */
	public String produceSnapShot(String parkingLot) throws SQLException 
	{
		try
		{
			String res = mysqlConnection.getParkingLotRowsFromDB(parkingLot);
			System.out.println("result = " + res);
			return res;
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	/**
	 * produce performance report
	 * @return
	 */
	public String producePerformanceReport() 
	{
		try
		{
			String res = mysqlConnection.getReport();
			System.out.println(res);
			return res;
		}
		catch (Exception e) 
		{
			System.out.println(e.getMessage());
			return e.getMessage();
		}
	}

	/**
	 * add new parking lot to data base
	 * @param name
	 * @param floors
	 * @param spaces
	 * @param availableSpots
	 * @param SpotsInUse
	 * @return
	 */
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
