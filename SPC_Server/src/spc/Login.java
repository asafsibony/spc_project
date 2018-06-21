package spc;

import server.mysqlConnection;

public class Login {

	public String SignIn(String user, String pass) 
	{
		return mysqlConnection.checkIfUserExists(user, pass, "client");
	}

	public String SignInAsAdmin(String user, String pass) {
		// TODO Auto-generated method stub
		return null;
	}

}
