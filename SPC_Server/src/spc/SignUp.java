package spc;

import server.mysqlConnection;

public class SignUp {

	public SignUp() {
		//System.out.println(user+" "+pass);
	}
	
	public String signUpToDB(String user, String pass){
		return mysqlConnection.addUser(user, pass);
	}
}
