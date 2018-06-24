package application;

import java.io.IOException;

import java.time.format.DateTimeFormatter;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import client.*;

/**
 * The controller for CheckInCheckOutScene
 * @author scadaadmin
 *
 */
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
    private DatePicker depCheckInDatePicker;
    
	static public StringProperty parkingLotsNames;
	static public StringProperty checkOutCost;
	
	/**
	 * Sign in button clicked event handler
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void SignInAction(ActionEvent event) throws IOException
	{
		super.openScene("LoginScene.fxml", event);
	}
	
	/**
	 * Actions wher initializeng the scene
	 */
	@FXML
	public void initialize() 
	{
		parkingLotsNames = new SimpleStringProperty("");
		checkOutCost = new SimpleStringProperty("");
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
		checkOutCost.addListener(new ChangeListener<Object>(){
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				costCheckOutText.setText(checkOutCost.getValue().toString());  
			}
		});
	}

	/**
	 * Get parking lots names from server
	 */
	private void getParkingLotsNamesFromServer() {
		Main.cts.send("getParkingLots");
	}

	/**
	 * Submit check in button clicked event handler
	 * @param event
	 */
	@FXML
	void submitCheckInAction(ActionEvent event) 
	{
		if(CheckInTypeComboBox.getValue() == null) {
    		super.displayNotAllFieldsFullError();
    		return;
    	}
		if(parkingLotComboBox.getValue() == null) {
    		super.displayNotAllFieldsFullError();
    		return;
    	}
		String id = checkInIdText.getText();
		String carId = carIdCheckInText.getText();
		String depTime = depCheckInText.getText();
		String type = CheckInTypeComboBox.getValue();
		String parkingLot = parkingLotComboBox.getValue();
		String depDate = depCheckInDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		if(super.validateInputNotNull(new String[] {id, carId, type, depTime, depDate}))
		{			
			if(super.validateHoursFormatCorrect(depTime))
			{
				String cmd = "submitCheckIn " + id + " " + carId + " " + depTime + " " + type + " " + parkingLot + " " + depDate;
				Main.cts.send(cmd);					
			}
		}
		else
		{
			super.displayNotAllFieldsFullError();
		}
		 	
	}

	/**
	 * submit check out button clicked event handler
	 * @param event
	 */
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

	/**
	 * Pay button clicked event handler
	 * @param event
	 */
	@FXML
	void payAction(ActionEvent event) 
	{
		checkOutCost.setValue("");
		new Alert(Alert.AlertType.CONFIRMATION, "Payment ended succsesfully, you are good to go!").showAndWait();
	}

}
