package spc;

import server.mysqlConnection;

/**
 * Server side login
 * @author scadaadmin
 *
 */
public class Login {

	/**
	 * sign in 
	 * @param user
	 * @param pass
	 * @return
	 */
	public String SignIn(String user, String pass) 
	{
		return mysqlConnection.checkIfUserExists(user, pass, "client");
	}

	/**
	 * sign in as admin
	 * @param user
	 * @param pass
	 * @return
	 */
	public String SignInAsAdmin(String user, String pass) {
		return mysqlConnection.checkIfUserExists(user, pass, "admin");
	}

	/**
	 * logout
	 * @param user
	 * @param Type
	 * @return
	 */
	public String Logout(String user, String Type) {
		// TODO Auto-generated method stub
		return mysqlConnection.logout(user, Type);
	}

}
