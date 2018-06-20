package server;

import java.io.IOException;

import ocsf.server.ConnectionToClient;
import spc.SignUp;

public class ClientMessageParser {

	public ClientMessageParser() {}
	
	public void parse(String msg, ConnectionToClient client) {
		String[] args =  msg.split("\\s+");
		if(args[0].equals("signUp")) {
			String user= args[1];
			String pass= args[2];
			SignUp signUp = new SignUp(user, pass);
		}
		
		
		
		
		else {
			try {
				client.sendToClient("Command not found.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
