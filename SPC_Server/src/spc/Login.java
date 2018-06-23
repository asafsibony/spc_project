package spc;

import server.mysqlConnection;

public class Login {

	public String SignIn(String user, String pass) 
	{
		return mysqlConnection.checkIfUserExists(user, pass, "client");
	}

	public String SignInAsAdmin(String user, String pass) {
		return mysqlConnection.checkIfUserExists(user, pass, "admin");
	}

	public String Logout(String user, String Type) {
		// TODO Auto-generated method stub
		return mysqlConnection.logout(user, Type);
	}

}
