package application;

import java.io.IOException;


import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
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

	static public StringProperty parkingLotsNames;
	
	@FXML
	void SignInAction(ActionEvent event) throws IOException
	{
		super.openScene("LoginScene.fxml", event);
	}
	
	
	@FXML
	public void initialize() 
	{
		parkingLotsNames = new SimpleStringProperty("");
		CheckInTypeComboBox.getItems().add("Casual");
		CheckInTypeComboBox.getItems().add("Order");
		CheckInTypeComboBox.getItems().add("Subscription");
		getParkingLotsNamesFromServer();
		parkingLotsNames.addListener(new ChangeListener<Object>(){
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
            		//System.out.println("Property changed");
            		String[] lots = parkingLotsNames.getValue().toString().split("\\s+");;
            		parkingLotComboBox.getItems().setAll(lots);            		
            }
        });
	}

	
	private void getParkingLotsNamesFromServer() {
		Main.cts.send("getParkingLots");
	}

	@FXML
	void submitCheckInAction(ActionEvent event) 
	{
		if(CheckInTypeComboBox.getValue() == null) {
    		super.displayNotAllFieldsFullError();
    		return;
    	}
		if(parkingLotsNames.getValue() == null) {
    		super.displayNotAllFieldsFullError();
    		return;
    	}
		String id = checkInIdText.getText();
		String carId = carIdCheckInText.getText();
		String dep = depCheckInText.getText();
		String type = CheckInTypeComboBox.getValue();
		String parkingLot = parkingLotsNames.getValue();
		if(!type.equals("Casual"))
		{
			dep = "None";
		}
		if(super.validateInputNotNull(new String[] {id, carId, type, dep}))
		{			
			if(super.validateHoursFormatCorrect(dep))
			{
				String cmd = "submitCheckIn " + id + " " + carId + " " + dep + " " + type + " " + parkingLot;
				Main.cts.send(cmd);					
			}
		}
		else
		{
			super.displayNotAllFieldsFullError();
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
