package client;

import java.io.IOException;

import application.CommonController;
import application.SignUpController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class ClientMessageParser {

	CommonController cc;
	
	public ClientMessageParser() {
		cc = new CommonController();
	}

	public void parse(String msg)
	{
		String[] args =  msg.split("\\s+");
		
		if(args[0].equals("signUp")) //the return status from signUp method.
		{	
			handleSignUpParse(args);			
		}
		else 
		{
			System.out.println("Command sent from server not found: "+msg);
		}
	}
	
	private void handleSignUpParse(String[] args)
	{
		int argsLength = args.length;
		String statusFromServer = "";
		String space = " ";
		for(int i=1; i<argsLength; i++)
		{
			statusFromServer += space;
			statusFromServer += args[i];
		}
		
		System.out.println(statusFromServer);
		
		//cc.displayMessage(statusFromServer);
		//SignUpController suc = new SignUpController();
		//suc.displayMessage(statusFromServer);
		
		//Show dialog to user with 'statusFromServer' string.

	}
}
