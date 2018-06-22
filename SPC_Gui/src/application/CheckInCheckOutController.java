package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
	private Button leaveCheckOutButton;

	@FXML
	private Button submitCheckInButton;

	@FXML
	private TextField checkInIdText;

	@FXML
	private TextField emailCheckInText;

	@FXML
	private Button submitCheckOutButton;

	@FXML
	private TextField idCheckOutText;

	@FXML
	private TextField depCheckInText;

	@FXML
	private TextField carIdCheckOutText;

	@FXML
	private Button payCheckOutButton;

	@FXML
	private TextField costCheckOutText;

	@FXML
	private ComboBox<String> parkingLotComboBox;

	@FXML
	private ComboBox<String> CheckInTypeComboBox;

	@FXML
	void SignInAction(ActionEvent event) throws IOException
	{
		super.openScene("LoginScene.fxml", event);
	}

	@FXML
	public void initialize() 
	{
		CheckInTypeComboBox.getItems().add("Casual");
		CheckInTypeComboBox.getItems().add("Order");
		CheckInTypeComboBox.getItems().add("Subscription");

		CheckInTypeComboBox.getSelectionModel().select("Casual");
	}

	@FXML
	void submitCheckInAction(ActionEvent event) 
	{
		String id = checkInIdText.getText();
		String carId = carIdCheckInText.getText();
		String dep = depCheckInText.getText();
		//String email = emailCheckInText.getText();
		String type = CheckInTypeComboBox.getValue();

		if(!type.equals("Casual"))
		{
			dep = "None";
			if(super.validateInputNotNull(new String[] {id, carId, type}))
			{			
				String cmd = "submitCheckIn " + id + " " + carId + " " + dep + " " + type ;
				Main.cts.send(cmd);
			}

			else
			{
				super.displayNotAllFieldsFullError();
			}
		}
		else if(type.equals("Casual"))
		{
			if(super.validateInputNotNull(new String[] {id, carId, type, dep}))
			{			
				String cmd = "submitCheckIn " + id + " " + carId + " " + dep + " " + type;
				Main.cts.send(cmd);
			}

			else
			{
				super.displayNotAllFieldsFullError();
			}
		}    	
	}

	@FXML
	void submitCheckOutAction(ActionEvent event) 
	{
		String id = idCheckOutText.getText();
		String carId = carIdCheckOutText.getText();

		if(super.validateInputNotNull(new String[] {id, carId}))
		{			
			String cmd = "submitCheckOutAction " + id + " " + carId;
			Main.cts.send(cmd);
		}

		else
		{
			super.displayNotAllFieldsFullError();
		}
	}

	@FXML
	void payAction(ActionEvent event) 
	{
		new Alert(Alert.AlertType.CONFIRMATION, "Payment ended succsesfully").showAndWait();
	}

	@FXML
	void leaveAction(ActionEvent event) 
	{
		String id = checkInIdText.getText();
		String carId = carIdCheckInText.getText();

		if(super.validateInputNotNull(new String[] {id, carId}))
		{			
			String cmd = "leaveParkingLot " + id + " " + carId;
			Main.cts.send(cmd);
		}

		else
		{
			super.displayNotAllFieldsFullError();
		}
	}

}
