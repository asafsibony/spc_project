package spc;

import server.mysqlConnection;

/**
 * common method: get parking lots names
 * @author scadaadmin
 *
 */
public class CommonMethods {

	public String getParkingLotsNames() {
		return mysqlConnection.getParkingLotsNames();
	}

}
