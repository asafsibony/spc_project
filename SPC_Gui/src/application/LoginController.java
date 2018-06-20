package application;

import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	
//	String[] parkingLotsNames; //put in combobox after parser calls getParkingLotsNamesFromServer

	public LoginController(){
		//Main.cts.send("getAllParkingLots");
	}
	@FXML
	void SignInAction(ActionEvent event) throws IOException
	{
		super.openScene("ClientsSystemScene.fxml", event);
	}	

	@FXML
	void SignInAsAdminAction(ActionEvent event) throws IOException
	{
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


