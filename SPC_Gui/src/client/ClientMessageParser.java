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

/**
 * Parse messages from server
 * @author scadaadmin
 *
 */
public class ClientMessageParser {

	CommonController cc;	
	/**
	 * Constructor
	 */
	public ClientMessageParser() {
		cc = new CommonController();
	}

	/**
	 * Parse method for all messages
	 * @param msg
	 */
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

		else if(args[0].equals("submitCheckOutAction"))
		{
			handleSubmitCheckOutParse(args);
		}

		/* Client System */
		else if(args[0].equals("submitInAdvanceParking"))
		{
			handleSubmitInAdvanceParkingParse(args);
		}

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
			handleViewOrderParse(args, msg);
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
			handleProduceSnapShotParse(args, msg);
		}

		else if(args[0].equals("producePerformanceReport"))
		{
			handleProducePerformanceReportParse(args, msg);
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

	/**
	 * submit check out parse handle 
	 * @param args
	 */
	private void handleSubmitCheckOutParse(String[] args) 
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
			CheckInCheckOutController.checkOutCost.set(args[2]);
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.INFORMATION, "Almost done, please Pay.").showAndWait();
			});
		}
	}

	/**
	 * add new parking lot parse handle
	 * @param args
	 */
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

	/**
	 * produce performance report parse handle
	 * @param args
	 * @param msg
	 */
	private void handleProducePerformanceReportParse(String[] args, String msg)
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
			AdminSystemController.totalSubs.set(args[2]);
			AdminSystemController.subsWithMoreThanOneCar.set(args[3]);
		}
	}

	/**
	 * produce snapshot parse handle
	 * @param args
	 * @param msg
	 */
	private void handleProduceSnapShotParse(String[] args, String msg)
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
			msg = msg.replace("produceSnapShot true ","");
			AdminSystemController.snapshot.set(msg);
		}
	}

	/**
	 * update prices parse handle
	 * @param args
	 */
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

	/**
	 * preserve spot parse handle
	 * @param args
	 */
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

	/**
	 * register defect spot parse handle
	 * @param args
	 */
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

	/**
	 * complaint parse handle
	 * @param args
	 */
	private void handleComplaintParse(String[] args) 
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
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.INFORMATION, "Your complaint has been submitted.").showAndWait();
			});
		}
	}

	/**
	 * view order parse handle
	 * @param args
	 * @param msg
	 */
	private void handleViewOrderParse(String[] args, String msg)
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
			msg = msg.replace("viewOrder true ","");
			ClientsSystemController.orderListByID.set(msg);
		}
	}

	/**
	 * cancel order parse handle
	 * @param args
	 */
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

	/**
	 * pay subscription 
	 * @param args
	 */
	private void handlePaySubscriptionParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
	}

	/**
	 * submit subscription parse handle
	 * @param args
	 */
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

	/**
	 * submit in advance parse handle
	 * @param args
	 */
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

	/**
	 * 
	 * @param args
	 */
	private void handlePayCheckOutParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
	}

	/**
	 * submit check in 
	 * @param args
	 */
	private void handleSubmitCheckInParse(String[] args)
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
			Platform.runLater(() -> {
				new Alert(Alert.AlertType.INFORMATION, "Congrats! Your car just Checked-in.").showAndWait();
			});
		}
	}

	/**
	 * sign in as admin parse handle
	 * @param args
	 */
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

	/**
	 * sign in parse handle
	 * @param args
	 */
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

	/**
	 * sign up parse handle
	 * @param args
	 */
	private void handleSignUpParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
		Platform.runLater(() -> {
			new Alert(Alert.AlertType.INFORMATION, statusFromServer).showAndWait();
		});
	}

	/**
	 * log out parse handle
	 * @param args
	 */
	
	private void handleLogout(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);

	}
	
	/**
	 * get parking lots 
	 * @param args
	 */
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
	
	/**
	 * parse message
	 * @param args
	 * @return
	 */
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
