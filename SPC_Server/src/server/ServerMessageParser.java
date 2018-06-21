package server;

import java.io.IOException;

import ocsf.server.ConnectionToClient;
import spc.SignUp;

public class ServerMessageParser {

	public ServerMessageParser() {}
	
	public void parse(String msg, ConnectionToClient client) throws IOException {
		String[] args =  msg.split("\\s+");
		if(args[0].equals("signUp")) {
			String user= args[1];
			String pass= args[2];
			SignUp signUp = new SignUp();
			client.sendToClient("signUp "+signUp.signUpToDB(user, pass));
		}
		
		
		
		
		else {
			System.out.println("Command sent from client not found: "+msg);
		}
	}
}
