package application;

import java.io.IOException;
import javafx.scene.control.Tab;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AdminSystemController extends CommonController
{
    @FXML
    private Button produceSnapshotButton;

    @FXML
    private Button addNewParkingLotButton;

    @FXML
    private Button LogOutButton;

    @FXML
    private TextField floorsNewParkingLotText;

    @FXML
    private Button submitUpdatePricesButton;
    
    @FXML
	private TextArea snapshotTextArea;
        
    @FXML
    private TextField spacesNewParkingLotText;
    
    @FXML
    private TextField moreThanOneCarSubsText;
    
    @FXML
    private TextField totalSubsText;
    
    @FXML
    private Button preserveButton;

    @FXML
    private TextField rowIndexPreserveText;
    
    @FXML
    private TextField floorIndexPreserveText;

    @FXML
    private ComboBox<String> parkingLotPreserveComboBox;

    @FXML
    private TextField floorIndexDefectText;

    @FXML
    private TextField rowIndexDefectText;

    @FXML
    private Button reportDefectButton;

    @FXML
    private Button permissionPriceButton;

    @FXML
    private ComboBox<String> parkingLotSnapshotComboBox;

    @FXML
    private Button performanceReportButton;

    @FXML
    private TextField nameNewParkingLotText;

    @FXML
    private ComboBox<String> parkingLotDefectComboBox;
    
    @FXML
    private TextField priceToUpdateText;
    
    @FXML
    private ComboBox<String> priceTypeComboBox;
    
    
    private static boolean permissionGiven = false;
    static public StringProperty parkingLotsNames;
    static public StringProperty snapshot;
    static public StringProperty totalSubs;
    static public StringProperty subsWithMoreThanOneCar;


    
    @FXML
	public void initialize() 
	{
    	priceTypeComboBox.getItems().add("Casual");
    	priceTypeComboBox.getItems().add("Order");
    	priceTypeComboBox.getItems().add("Subscription");
    	parkingLotsNames = new SimpleStringProperty("");
    	snapshot = new SimpleStringProperty("");
    	totalSubs = new SimpleStringProperty("");
    	subsWithMoreThanOneCar = new SimpleStringProperty("");


    	getParkingLotsNamesFromServer();
		parkingLotsNames.addListener(new ChangeListener<Object>(){
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
            		//System.out.println("Property changed");
            		String[] lots = parkingLotsNames.getValue().toString().split("\\s+");;
            		parkingLotDefectComboBox.getItems().setAll(lots);  
            		parkingLotSnapshotComboBox.getItems().setAll(lots); 
            		parkingLotPreserveComboBox.getItems().setAll(lots); 
            }
        });
		snapshot.addListener(new ChangeListener<Object>(){
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				snapshotTextArea.setText(snapshot.getValue().toString()); 
            }
        });
		totalSubs.addListener(new ChangeListener<Object>(){
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				totalSubsText.setText(totalSubs.getValue().toString()); 
            }
        });
		subsWithMoreThanOneCar.addListener(new ChangeListener<Object>(){
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
				moreThanOneCarSubsText.setText(subsWithMoreThanOneCar.getValue().toString()); 
            }
        });
	}
    
	private void getParkingLotsNamesFromServer() {
		Main.cts.send("getParkingLots");
	}
	
    @FXML
    void reportAsDefectAction(ActionEvent event)
    {
    	if(parkingLotDefectComboBox.getValue() == null) {
    		super.displayNotAllFieldsFullError();
    		return;
    	}
    	String parkingLot = parkingLotDefectComboBox.getValue().toString();
    	String floorIndex = floorIndexDefectText.getText();
    	String rowIndex = rowIndexDefectText.getText();
    	
		if(super.validateInputNotNull(new String[] {rowIndex, floorIndex}))
		{
			if(super.validateFieldIsANumber(floorIndex, rowIndex))
			{
				Main.cts.send("registerDefectSpot " + parkingLot + " " + floorIndex + " " + rowIndex);
			}
		}
		else
		{
			super.displayNotAllFieldsFullError();
		}
    }

    @FXML
    void presereveSpotAction(ActionEvent event) 
    {
    	if(parkingLotPreserveComboBox.getValue() == null) {
    		super.displayNotAllFieldsFullError();
    		return;
    	}
    	String parkingLot = parkingLotPreserveComboBox.getValue().toString();
    	String floorIndex = floorIndexPreserveText.getText();
    	String rowIndex = rowIndexPreserveText.getText();
    	
		if(super.validateInputNotNull(new String[] {floorIndex, rowIndex}))
		{
			if(super.validateFieldIsANumber(floorIndex, rowIndex))
			{
				Main.cts.send("preserveSpot " + parkingLot + " " + floorIndex + " " + rowIndex);
			}
		}
		else
		{
			super.displayNotAllFieldsFullError();
		}
    }

    @FXML
    void submitUpdatePricesAction(ActionEvent event)
    {
    	if (!permissionGiven) {
    		new Alert(Alert.AlertType.ERROR, "You have to ask for permission first.").showAndWait();
    		return;
    	}
    	permissionGiven = false;
    	if(priceTypeComboBox.getValue() == null) {
    		super.displayNotAllFieldsFullError();
    		return;
    	}
    	String priceType = priceTypeComboBox.getValue().toString();
    	String priceToUpdat = priceToUpdateText.getText();
    	
    	// only of permission given
		if(super.validateInputNotNull(new String[] {priceToUpdat}))
		{
			if(super.validateFieldIsANumber(priceToUpdat))
			{				
				Main.cts.send("submitUpdatePrices " + priceType + " " + priceToUpdat);
			}
		}

		else
		{
			super.displayNotAllFieldsFullError();
		}
    }

    @FXML
    void getPermissionPricesAction(ActionEvent event) 
    {
    	permissionGiven = true;
    	new Alert(Alert.AlertType.INFORMATION, "Congrats, you got permissions to update the prices").showAndWait();
    }

    @FXML
    void produceSnapshotAction(ActionEvent event)
    {
    	String parkingLot = parkingLotSnapshotComboBox.getValue().toString();

		if(super.validateInputNotNull(new String[] {parkingLot}))
		{
			Main.cts.send("produceSnapShot " + parkingLot);
		}

		else
		{
			super.displayNotAllFieldsFullError();
		}
    }

    @FXML
    void performanceReportAction(ActionEvent event) 
    {
    	Main.cts.send("producePerformanceReport");
    }

    @FXML
    void addNewParkingLotAction(ActionEvent event)		//Done, except input check for integers
    {
    	String name = nameNewParkingLotText.getText().replace(" ", "_");
    	String floors = floorsNewParkingLotText.getText();
    	String spaces = spacesNewParkingLotText.getText();
    	String SpotsInUse = "0";
		if(super.validateInputNotNull(new String[] {name, floors, spaces}))
		{
			if(super.validateFieldIsANumber(new String[] {floors, spaces}))
			{
		    	if(super.validateFieldIsANumber(floors, spaces))
		    	{		    		
			    	String availableSpots = Integer.toString(Integer.parseInt(floors) * Integer.parseInt(spaces));
		    		Main.cts.send("addNewParkingLot " + name + " " + floors + " " + spaces + " "+ availableSpots + " " + SpotsInUse);
		    	}
			}
		}
		else
		{
			super.displayNotAllFieldsFullError();
		}    	
    }

    @FXML
    void LogOutAction(ActionEvent event) throws IOException 
    {
    	if(!Main.userName.equals("")) {
    		Main.cts.send("logout " + Main.userName + " admin");
    	}
    	super.openScene("LoginScene.fxml", event);
    }
}

