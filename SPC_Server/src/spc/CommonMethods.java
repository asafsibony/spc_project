package spc;

import server.mysqlConnection;

public class CommonMethods {

	public String getParkingLotsNames() {
		return mysqlConnection.getParkingLotsNames();
	}

}
