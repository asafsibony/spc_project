package client;

import java.io.IOException;
import java.util.ArrayList;

import application.AdminSystemController;
import application.CheckInCheckOutController;
import application.ClientsSystemController;
import application.CommonController;
import application.LoginController;
import application.Main;
import application.SignUpController;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class ClientMessageParser {

	CommonController cc;
//	public static StringProperty parkingLots = new SimpleStringProperty("hmm");
	
	public ClientMessageParser() {
		cc = new CommonController();
	}

	public void parse(String msg)
	{
		String[] args =  msg.split("\\s+");

		/* Sign Up */
		if(args[0].equals("signUp")) //the return status from signUp method.
		{	
			handleSignUpParse(args);			
		}

		/* Sign In */
		else if(args[0].equals("SignIn"))
		{
			handleSignInParse(args);
		}

		else if(args[0].equals("SignInAsAdmin"))
		{
			handleSignInAsAdminParse(args);
		}

		/* Check In and Check Out */
		else if(args[0].equals("submitCheckIn"))
		{
			handleSubmitCheckInParse(args);
		}

		//		else if(args[0].equals("payCheckOut"))
		//		{
		//			handlePayCheckOutParse(args);
		//		}

		else if(args[0].equals("submitCheckOutAction"))
		{
			handleSubmitCheckOutParse(args);
		}

		else if(args[0].equals("leaveParkingLot"))
		{
			handleLeaveParkingLotParse(args);
		}

		/* Client System */
		else if(args[0].equals("submitInAdvanceParking"))
		{
			handleSubmitInAdvanceParkingParse(args);
		}

//		else if(args[0].equals("payInAdvanceParking"))
//		{
//			handlePayInAdvanceParkingParse(args);
//		}

		else if(args[0].equals("submitSubscription"))
		{
			handleSubmitSubscriptionParse(args);
		}

		else if(args[0].equals("paySubscription"))
		{
			handlePaySubscriptionParse(args);
		}

		else if(args[0].equals("cancelOrder"))
		{
			handleCancelOrderParse(args);
		}

		else if(args[0].equals("viewOrder"))
		{
			handleViewOrderParse(args);
		}

		else if(args[0].equals("complaint"))
		{
			handleComplaintParse(args);
		}

		/* Admin System */
		else if(args[0].equals("registerDefectSpot"))
		{
			handleRegisterDefectSpotParse(args);
		}

		else if(args[0].equals("preserveSpot"))
		{
			handlePreserveSpotParse(args);
		}

		else if(args[0].equals("submitUpdatePrices"))
		{
			handleUpdatePricesParse(args);
		}

		else if(args[0].equals("produceSnapShot"))
		{
			handleProduceSnapShotParse(args);
		}

		else if(args[0].equals("producePerformanceReport"))
		{
			handleProducePerformanceReportParse(args);
		}

		else if(args[0].equals("addNewParkingLot"))
		{
			handleAddNewParkingLotParse(args);
		}
		else if(args[0].equals("logout"))
		{
			handleLogout(args);
		}
		else if(args[0].equals("getParkingLots"))
		{
			handleGetParkingLots(args);
		}
		else 
		{
			System.out.println("Command sent from server not found: "+msg);
		}
	}


	/* Message From Server Handlres*/

	private void handleSubmitCheckOutParse(String[] args) 
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);	
	}

	private void handleAddNewParkingLotParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
		if(!statusFromServer.equals("true")) {
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.ERROR, statusFromServer).showAndWait();
			});
		}
		else
		{
			Main.cts.send("getParkingLots");
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();
			});
		}
	}

	private void handleProducePerformanceReportParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
	}

	private void handleProduceSnapShotParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
	}

	private void handleUpdatePricesParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
		if(!statusFromServer.equals("true")) {
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.ERROR, statusFromServer).showAndWait();
			});
		}
		else
		{
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();
			});
		}
	}

	private void handlePreserveSpotParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
		if(!statusFromServer.equals("true")) {
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.ERROR, statusFromServer).showAndWait();
			});
		}
		else
		{
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();
			});
		}
	}

	private void handleRegisterDefectSpotParse(String[] args) 
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
		if(!statusFromServer.equals("true")) {
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.ERROR, statusFromServer).showAndWait();
			});
		}
		else
		{
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.INFORMATION, "Success").showAndWait();
			});
		}
	}

	private void handleComplaintParse(String[] args) 
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
	}

	private void handleViewOrderParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
	}

	private void handleCancelOrderParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
		if(!args[1].equals("true")) {
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.ERROR, statusFromServer).showAndWait();
			});
		}
		else
		{
			ClientsSystemController.cancelOrderRefund.set(args[2]);
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.INFORMATION, "Your order has been canceled.").showAndWait();
			});
		}
	}

	private void handlePaySubscriptionParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
	}

	private void handleSubmitSubscriptionParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
		if(!args[1].equals("true")) {
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.ERROR, statusFromServer).showAndWait();
			});
		}
		else
		{
			ClientsSystemController.subscriptionOrderCost.set(args[2]);
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.INFORMATION, "Your order accepted.").showAndWait();
			});
		}
	}

	private void handleSubmitInAdvanceParkingParse(String[] args) 
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
		if(!args[1].equals("true")) {
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.ERROR, statusFromServer).showAndWait();
			});
		}
		else
		{
			ClientsSystemController.inAdvanceOrderCost.set(args[2]);
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.INFORMATION, "Your order accepted.").showAndWait();
			});
		}
	}

//	private void handlePayInAdvanceParkingParse(String[] args) 
//	{
//		String statusFromServer = parseMessage(args);
//		System.out.println(statusFromServer);
//	}

	private void handleLeaveParkingLotParse(String[] args) 
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
	}

	private void handlePayCheckOutParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
	}

	private void handleSubmitCheckInParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
		Platform.runLater(() -> {
			new Alert(Alert.AlertType.INFORMATION, statusFromServer).showAndWait();
		});
	}

	private void handleSignInAsAdminParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
		if(!statusFromServer.equals("true")) {
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.ERROR, statusFromServer).showAndWait();
			});
		}
		else
		{
			LoginController.adminLoginVerified.setValue(true);;
		}
	}

	private void handleSignInParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
		if(!statusFromServer.equals("true")) {
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.ERROR, statusFromServer).showAndWait();
			});
		}
		else
		{
			LoginController.clientLoginVerified.setValue(true);;
		}
	}

	private void handleSignUpParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
		Platform.runLater(() -> {
			new Alert(Alert.AlertType.INFORMATION, statusFromServer).showAndWait();
		});
	}

	private void handleLogout(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);

	}
	
	private void handleGetParkingLots(String[] args) {
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
		if(CheckInCheckOutController.parkingLotsNames != null)
			CheckInCheckOutController.parkingLotsNames.setValue(statusFromServer);
		if(AdminSystemController.parkingLotsNames != null)
			AdminSystemController.parkingLotsNames.setValue(statusFromServer);
		if(ClientsSystemController.parkingLotsNames != null)
			ClientsSystemController.parkingLotsNames.setValue(statusFromServer);
	}
	
	private String parseMessage(String[] args)
	{
		int argsLength = args.length;
		String statusFromServer = "";
		String space = " ";
		for(int i=1; i<argsLength-1; i++)
		{
			statusFromServer += args[i];
			statusFromServer += space;
		}
		statusFromServer += args[argsLength-1];
		return statusFromServer;
	}
}
