package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import client.*;
public class CheckInCheckOutController extends CommonController
{
	@FXML
	private Button SignInButton;

	@FXML
	private TextField carIdCheckInText;

	@FXML
	private Button submitCheckInButton;

	@FXML
	private TextField checkInIdText;

	@FXML
	private TextField emailCheckInText;

	@FXML
	private TextField depCheckInText;

	@FXML
	private Button leaveCheckOutButton;

	@FXML
	private Button payCheckOutButton;
	
	@FXML
	void SignInAction(ActionEvent event) throws IOException
	{
		super.openScene("LoginScene.fxml", event);
	}

	@FXML
	void submitCheckInAction(ActionEvent event) 
	{
//		String id = checkInIdText.getText();
//		
//		String cmd="submitCheckIn "+id;
//		Main.cts.send(cmd);
	}

}
