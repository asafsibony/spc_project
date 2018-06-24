package spc;

import server.mysqlConnection;

/**
 * server side sign up
 * @author scadaadmin
 *
 */
public class SignUp {

	public SignUp() {
		//System.out.println(user+" "+pass);
	}
	
	/**
	 * sign up
	 * @param user
	 * @param pass
	 * @return
	 */
	public String signUpToDB(String user, String pass){
		return mysqlConnection.addUser(user, pass);
	}
}
