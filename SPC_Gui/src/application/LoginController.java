package application;

import javafx.event.ActionEvent;

import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;


public class LoginController extends CommonController
{
	@FXML 
	private Button SignInButton; 
	@FXML
	private Button SignInAsAdminButton;
	@FXML
	private Button SignUpButton;
	@FXML
	private Button backButton;
	@FXML
    private PasswordField passwordText;
	@FXML
    private TextField userNameText;
	
//	String[] parkingLotsNames; //put in combobox after parser calls getParkingLotsNamesFromServer

	public LoginController(){
		//Main.cts.send("getAllParkingLots");
	}
	@FXML
	void SignInAction(ActionEvent event) throws IOException
	{
		String user = userNameText.getText();
		String pass = passwordText.getText();
		
		if(super.validateInputNotNull(new String[] {user, pass}))
		{
			String cmd = "SignIn " + user + " " + pass;
			Main.cts.send(cmd);
		}
		
		else
		{
			super.displayNotAllFieldsFullError();
			return;
		}
		
		super.openScene("ClientsSystemScene.fxml", event);
	}	

	@FXML
	void SignInAsAdminAction(ActionEvent event) throws IOException
	{
		String user = userNameText.getText();
		String pass = passwordText.getText();
		
		if(super.validateInputNotNull(new String[] {user, pass}))
		{
			String cmd = "SignInAsAdmin " + user + " " + pass;
			Main.cts.send(cmd);
		}
		
		else
		{
			super.displayNotAllFieldsFullError();
			return;
		}
		
		super.openScene("AdminSystemScene.fxml", event);
	}

	@FXML
	void SignUpAction(ActionEvent event) throws IOException 
	{
		super.openScene("SignUpScene.fxml", event);
	}

	@FXML
	void backAction(ActionEvent event) throws IOException 
	{
		super.openScene("CheckInCheckOutScene.fxml", event);
	}
	
//	public void getParkingLotsNamesFromServer(String[] names) {
//		parkingLotsNames= names;
//	}
	
}


