package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
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
    private TextField newLocalPriceText;

    @FXML
    private TextField spacesNewParkingLotText;

    @FXML
    private Button preserveButton;

    @FXML
    private TextField newInAdvPriceText;

    @FXML
    private TextField parkingSpotIndexPreserveText;

    @FXML
    private ComboBox<?> parkingLotPreserveComboBox;

    @FXML
    private TextField parkingSpotIndexDefectText;

    @FXML
    private Button reportDefectButton;

    @FXML
    private Button permissionPriceButton;

    @FXML
    private ComboBox<?> parkingLotSnapshotComboBox;

    @FXML
    private Button performanceReportButton;

    @FXML
    private Tab nameNewParkingLotText;

    @FXML
    private ComboBox<?> parkingLotDefectComboBox;

    @FXML
    private TextField newSubPriceText;
    
    private boolean permissionGiven = false;

    @FXML
    void reportAsDefectAction(ActionEvent event)
    {
    	String parkingLot = parkingLotDefectComboBox.getValue().toString();
    	String spot = parkingSpotIndexDefectText.getText();
    	
		if(super.validateInputNotNull(new String[] {parkingLot, spot}))
		{
			Main.cts.send("registerDefectSpot" + parkingLot + " " + spot);
		}

		else
		{
			super.displayNotAllFieldsFullError();
		}
    }

    @FXML
    void presereveSpotAction(ActionEvent event) 
    {
    	String parkingLot = parkingLotPreserveComboBox.getValue().toString();
    	String spot = parkingSpotIndexPreserveText.getText();
    	
		if(super.validateInputNotNull(new String[] {parkingLot, spot}))
		{
			Main.cts.send("preserveSpot" + parkingLot + " " + spot);
		}

		else
		{
			super.displayNotAllFieldsFullError();
		}
    }

    @FXML
    void submitUpdatePricesAction(ActionEvent event)
    {
    	String local = newLocalPriceText.getText();
    	String inAdv = newInAdvPriceText.getText();
    	String sub = newSubPriceText.getText();
    	
    	// only of permission given
		if(super.validateInputNotNull(new String[] {local, inAdv, sub})&& permissionGiven)
		{
			Main.cts.send("submitUpdatePrices" + local + " " + inAdv + " " + sub);
		}

		else
		{
			super.displayNotAllFieldsFullError();
		}
    }

    @FXML
    void getPermissionPricesAction(ActionEvent event) 
    {
    	String local = newLocalPriceText.getText();
    	String inAdv = newInAdvPriceText.getText();
    	String sub = newSubPriceText.getText();
    	
		if(super.validateInputNotNull(new String[] {local, inAdv, sub}))
		{
			Main.cts.send("getPermissionForUpdatePrices" + local + " " + inAdv + " " + sub);
		}

		else
		{
			super.displayNotAllFieldsFullError();
		}
    }

    @FXML
    void produceSnapshotAction(ActionEvent event)
    {
    	String parkingLot = parkingLotSnapshotComboBox.getValue().toString();

		if(super.validateInputNotNull(new String[] {parkingLot}))
		{
			Main.cts.send("produceSnapShot" + parkingLot);
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
    void addNewParkingLotAction(ActionEvent event)
    {
    	String name = nameNewParkingLotText.getText();
    	String floors = floorsNewParkingLotText.getText();
    	String spaces = spacesNewParkingLotText.getText();
    	
		if(super.validateInputNotNull(new String[] {name, floors, spaces}))
		{
			Main.cts.send("addNewParkingLot" + name + " " + floors + " " + spaces);
		}

		else
		{
			super.displayNotAllFieldsFullError();
		}    	
    }

    @FXML
    void LogOutAction(ActionEvent event) throws IOException 
    {
    	super.openScene("LoginScene.fxml", event);
    }
}
