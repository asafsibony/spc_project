package application;

import java.io.IOException;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.text.DateFormatter;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
	private RadioButton regularSubRadioButton;

	@FXML
	private TextField idComplaintText;

	@FXML
	private DatePicker parkingDateCancelOrderDate;

	@FXML
	private TextField carIdViewOrdersText;

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
	private ListView<?> yourOrdersTextArea;

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
	private RadioButton BusinessSubRadioButton;

	@FXML
	private TextField arrivalHourInAdvText;

	@FXML
	private TextField carIdInAdvText;

	@FXML
	private Button payInAdvButton;

	@FXML
	private TextField carIdCancelOrderText;

	/* non fxml variavles */
	private String costInAdvanceFromServer;
	private String costSubscriptionFromServer;
	private String costRefundForCancelOrderFromSerever;
	private boolean isSubscriptionRegular = true;
	String[] parkingLotsNames; //put in combobox after parser calls getParkingLotsNamesFromServer

	//private final double inAdvanceParkingCostPerMin = 4/60;



	/* Constructor */
	public ClientsSystemController() 
	{
		//Main.cts.send("getAllParkingLots");
	}

	/* In Advance Parking Functions */
	/********************************/
	@FXML
	void submitInAdvanceParkingAction(ActionEvent event) 
	{
		String id = idInAdvText.getText();
		String carId = carIdInAdvText.getText();
		String arrivalDate = arrivalDateInAdvDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String arrivalHour = arrivalHourInAdvText.getText(); // format: 15:00
		String depDate = departureDateInAdvDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String depHour = departureHourInAdvText.getText();
		String parkingLot = parkingLotsInAdvComboBox.getValue().toString();
		String email = emailInAdvText.getText();

		// Call submit function in server (no need for email)

		if(super.validateInputNotNull(new String[] {id, carId, arrivalDate, arrivalHour, depDate, depHour, parkingLot}))
		{
			Main.cts.send("submitInAdvanceParking " + id + " " + carId + " " + arrivalDate
					+ " " + arrivalHour + " " + depDate + " " + depHour + " " + parkingLot);

			// The answer from ClientMessageParser will contain the cost calculated and than set it in costInAdvanceFromServer?
		}

		else
		{
			super.displayNotAllFieldsFullError();
		}

		// after getting cost from server (or from calculating here)
		costInAdvText.setText(costInAdvanceFromServer);

		
		
		//    	Date arriv = Date.from(arrivalDateInAdvDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		//    	Date dep = Date.from(departureDateInAdvDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		//
		//    	Period p = new Period();
		//    	String[] arrivalHourSplit = arrivalHour.split(":");
		//    	String[] depHourSplit = depHour.split(":");
		//    	
		//    	double totalParkingTime = (Double.parseDouble(depHourSplit[0]) - Double.parseDouble(arrivalHourSplit[0])) + 
		//    			Double.parseDouble(depHourSplit[1])/60 - Double.parseDouble(arrivalHourSplit[1])/60; 
		//    	
		//    	//costInAdvText.setText(String.valueOf(totalParkingTime*inAdvanceParkingCostPerMin));
		//    	costInAdvText.setText(String.valueOf(totalParkingTime*inAdvanceParkingCostPerMin));

	}

	@FXML
	void payInAdvanceParkingAction(ActionEvent event) 
	{
		// The cost will set from server after submition
		String id = idInAdvText.getText();

		if(super.validateInputNotNull(new String[] {id, costInAdvanceFromServer}))
		{
			Main.cts.send("payInAdvanceParking" + id + " " + costInAdvanceFromServer);
		}

		else
		{
			super.displayNotAllFieldsFullError(); //Change to correct message
		}

	}
	/********************************/

	/* Subscription Functions       */
	/********************************/
	@FXML
	void checkBusinessSubAction(ActionEvent event) 
	{
		BusinessSubRadioButton.setSelected(true);
		regularSubRadioButton.setSelected(false);
		isSubscriptionRegular = true;
	}

	@FXML
	void checkRegularSubAction(ActionEvent event) 
	{
		BusinessSubRadioButton.setSelected(false);
		regularSubRadioButton.setSelected(true);
		isSubscriptionRegular = false;
	}

	@FXML
	void submitSubscriptionAction(ActionEvent event) 
	{
		String id = idSubText.getText();
		String carsId = carIdSubText.getText(); // if there are more than one car, the cars id will be: carI1 CarId2 and so on
		String date = startDateSubDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String regOrBuis;

		if(isSubscriptionRegular) regOrBuis = "regular"; else regOrBuis = "business";
		if(super.validateInputNotNull(new String[] {id, carsId, date}))
		{
			Main.cts.send("submitSubscription" + id + " " + carsId + " " + date + " " + regOrBuis);
		}

		else
		{
			super.displayNotAllFieldsFullError();
		}

		// after getting cost from serverr
		costSubText.setText(costSubscriptionFromServer);
	}

	@FXML
	void paySubscriptionAction(ActionEvent event) 
	{
		// The cost will set from server after submition
		String id = idInAdvText.getText();

		if(super.validateInputNotNull(new String[] {id, costSubscriptionFromServer}))
		{
			Main.cts.send("paySubscription" + id + " " + costSubscriptionFromServer);
		}

		else
		{
			super.displayNotAllFieldsFullError(); //change to correct message
		}
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
			Main.cts.send("cancelOrder" + id + " " + carId + " " + date + " " + hour);
		}

		else
		{
			super.displayNotAllFieldsFullError();
		}

		// after getting the refund cost from server
		refundCostCancelOrderText.setText(costRefundForCancelOrderFromSerever);
	}

	/********************************/

	/* View Order Functions         */
	/********************************/
	@FXML
	void submitViewOrderAction(ActionEvent event)
	{
		String id = idViewOrdersText.getText();
		String carId = carIdViewOrdersText.getText();

		if(super.validateInputNotNull(new String[] {id, carId}))
		{
			Main.cts.send("viewOrder" + id + " " + carId);
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
		String id = idViewOrdersText.getText();

		if(super.validateInputNotNull(new String[] {id}))
		{
			Main.cts.send("complaint" + id);
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

	public void getParkingLotsNamesFromServer(String[] names) 
	{
		parkingLotsNames = names;
		for(String name:names)
		{
			parkingLotsInAdvComboBox.setValue(name);
		}
	}
}
