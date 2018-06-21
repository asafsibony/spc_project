package client;

import java.io.IOException;

import application.SignUpController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class ClientMessageParser {

	public ClientMessageParser() {}
	
	public void parse(String msg) {
		String[] args =  msg.split("\\s+");
		if(args[0].equals("signUp")) {				//the return status from signUp method.
			String statusFromServer= args[1];
			System.out.println(statusFromServer);
			//Show dialog to user with 'statusFromServer' string.
		}
		
		
		
		
		else {
			System.out.println("Command sent from server not found: "+msg);
		}
	}
}
