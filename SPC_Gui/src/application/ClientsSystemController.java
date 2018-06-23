package application;

import java.io.IOException;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.text.DateFormatter;

import client.ClientMessageParser;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ClientsSystemController extends CommonController
{
	@FXML
	private TextField idInAdvText;

	@FXML
	private ComboBox<String> parkingLotsInAdvComboBox;

	@FXML
	private Button LogOutButton;

	@FXML
	private Button submitSubButton;

	@FXML
	private TextField idCancelOrderText;

	@FXML
	private Button submitInAdvButton;

	@FXML
	private DatePicker departureDateInAdvDate;

	@FXML
	private TextField idComplaintText;

	@FXML
	private DatePicker parkingDateCancelOrderDate;

	@FXML
	private Button paySubButton;

	@FXML
	private TextField idViewOrdersText;

	@FXML
	private Button submitComplaintButton;

	@FXML
	private TextField carIdSubText;

	@FXML
	private Button submitViewOrderButton;

	@FXML
	private TextField departureHourInAdvText;

	@FXML
	private Button submitCancelOrderButton;

	@FXML
	private TextField refundCostCancelOrderText;
	
    @FXML
    private TextArea yourOrdersTextArea;

	@FXML
	private TextField costSubText;

	@FXML
	private TextArea complaintText;

	@FXML
	private TextField parkingHourCancelOrderText;

	@FXML
	private TextField idSubText;

	@FXML
	private DatePicker arrivalDateInAdvDate;

	@FXML
	private DatePicker startDateSubDate;

	@FXML
	private TextField costInAdvText;

	@FXML
	private TextField emailInAdvText;

	@FXML
	private TextField arrivalHourInAdvText;

	@FXML
	private TextField carIdInAdvText;

	@FXML
	private Button payInAdvButton;

	@FXML
	private TextField carIdCancelOrderText;

    @FXML
    private ComboBox<String> subscriptionTypeComboBox;
    
	/* non fxml variavles */
	//private String costInAdvanceFromServer;
	static public StringProperty parkingLotsNames;
	static public StringProperty inAdvanceOrderCost;
	public static StringProperty subscriptionOrderCost;
	public static StringProperty cancelOrderRefund;
	public static StringProperty orderListByID;


	@FXML
	public void initialize() 
	{
		subscriptionTypeComboBox.getItems().add("Regular");
		subscriptionTypeComboBox.getItems().add("Buisness");
		parkingLotsNames = new SimpleStringProperty("");
		inAdvanceOrderCost = new SimpleStringProperty("");
		subscriptionOrderCost = new SimpleStringProperty("");
		cancelOrderRefund = new SimpleStringProperty("");
		orderListByID = new SimpleStringProperty("");

		getParkingLotsNamesFromServer();
		parkingLotsNames.addListener(new ChangeListener<Object>(){
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				//System.out.println("Property changed");
				String[] lots = parkingLotsNames.getValue().toString().split("\\s+");;
				parkingLotsInAdvComboBox.getItems().setAll(lots);  
			}
		});
		inAdvanceOrderCost.addListener(new ChangeListener<Object>(){
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				costInAdvText.setText(inAdvanceOrderCost.getValue().toString());  
			}
		});
		subscriptionOrderCost.addListener(new ChangeListener<Object>(){
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				costSubText.setText(subscriptionOrderCost.getValue().toString());  
			}
		});
		cancelOrderRefund.addListener(new ChangeListener<Object>(){
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				refundCostCancelOrderText.setText(cancelOrderRefund.getValue().toString());  
			}
		});
		orderListByID.addListener(new ChangeListener<Object>(){
			@Override
			public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				yourOrdersTextArea.setText(orderListByID.getValue().toString()); 
				//yourOrdersTextArea.setDisable(true);
			}
		});		
	}
	
	
	private void getParkingLotsNamesFromServer() {
		Main.cts.send("getParkingLots");
	}
	
	/* In Advance Parking Functions */
	/********************************/
	@FXML
	void submitInAdvanceParkingAction(ActionEvent event) 
	{
		if(parkingLotsInAdvComboBox.getValue() == null) {
    		super.displayNotAllFieldsFullError();
    		return;
    	}
		String id = idInAdvText.getText();
		String carId = carIdInAdvText.getText();
		String arrivalDate = arrivalDateInAdvDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String arrivalHour = arrivalHourInAdvText.getText(); // format: 15:00
		String depDate = departureDateInAdvDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String depHour = departureHourInAdvText.getText(); // format: 15:00
		String parkingLot = parkingLotsInAdvComboBox.getValue().toString();
		String email = emailInAdvText.getText();
		if(super.validateInputNotNull(new String[] {id, carId, arrivalDate, arrivalHour, depDate, depHour})				)
		{
			if(super.validateHoursFormatCorrect(arrivalHour, depHour))
			{				
				Main.cts.send("submitInAdvanceParking " + id + " " + carId + " " + arrivalDate
						+ " " + arrivalHour + " " + depDate + " " + depHour + " " + parkingLot + " " + email);
			}
		}
		else
		{
			super.displayNotAllFieldsFullError();
		}
	}

	@FXML
	void payInAdvanceParkingAction(ActionEvent event) 
	{
		inAdvanceOrderCost.set("");
		new Alert(Alert.AlertType.INFORMATION, "Thanks, we love your money.").showAndWait();
	}
	
	/********************************/

	/* Subscription Functions       */
	/********************************/

	@FXML
	void submitSubscriptionAction(ActionEvent event) 
	{
		if(subscriptionTypeComboBox.getValue() == null) {
    		super.displayNotAllFieldsFullError();
    		return;
    	}
		String id = idSubText.getText();
		String carId = carIdSubText.getText();
		String date = startDateSubDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String regOrBuis = subscriptionTypeComboBox.getValue().toString();
		if(super.validateInputNotNull(new String[] {id, carId, date}))
		{
			Main.cts.send("submitSubscription " + id + " " + carId + " " + date + " " + regOrBuis);
		}
		else
		{
			super.displayNotAllFieldsFullError();
		}
	}

	@FXML
	void paySubscriptionAction(ActionEvent event) 
	{
		subscriptionOrderCost.set("");
		new Alert(Alert.AlertType.INFORMATION, "Thanks, we love your money.").showAndWait();
	}

	/********************************/

	/* Cancel Order Functions       */
	/********************************/
	@FXML
	void cancelOrderAction(ActionEvent event)
	{
		String id = idCancelOrderText.getText();
		String carId = carIdCancelOrderText.getText();
		String date = parkingDateCancelOrderDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String hour = parkingHourCancelOrderText.getText();
		if(super.validateInputNotNull(new String[] {id, carId, date, hour})) 
		{
			if(super.validateHoursFormatCorrect(hour))
			{				
				Main.cts.send("cancelOrder " + id + " " + carId + " " + date + " " + hour);
			}
		}
		else
		{
			super.displayNotAllFieldsFullError();
		}
	}

	/********************************/

	/* View Order Functions         */
	/********************************/
	@FXML
	void submitViewOrderAction(ActionEvent event)
	{
		String id = idViewOrdersText.getText();

		if(super.validateInputNotNull(new String[] {id}))
		{
			Main.cts.send("viewOrder " + id);
		}
		else
		{
			super.displayNotAllFieldsFullError();
		}
	}
	
	/********************************/

	/* Complaint Function           */
	@FXML
	void submitComplaintAction(ActionEvent event) 
	{
		String id = idComplaintText.getText();
		String complaint = complaintText.getText();
		if(super.validateInputNotNull(id, complaint))
		{
			Main.cts.send("complaint " + id + " " + complaint);
		}
		else
		{
			super.displayNotAllFieldsFullError();
		}
	}

	/* LogOut Function              */
	@FXML
	void LogOutAction(ActionEvent event) throws IOException
	{
		if(!Main.userName.equals("")) {
    		Main.cts.send("logout " + Main.userName + " client");
    	}
		super.openScene("LoginScene.fxml", event);
	}

}
