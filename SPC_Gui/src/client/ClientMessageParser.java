package client;

import java.io.IOException;

import application.CheckInCheckOutController;
import application.CommonController;
import application.SignUpController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;


public class ClientMessageParser {

	CommonController cc;
	
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
		
		else if(args[0].equals("payCheckOut"))
		{
			handlePayCheckOutParse(args);
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
		
		else if(args[0].equals("payInAdvanceParking"))
		{
			handlePayInAdvanceParkingParse(args);
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
		else 
		{
			System.out.println("Command sent from server not found: "+msg);
		}
	}
	
	
	/* Message From Server Handlres*/
	
	private void handleAddNewParkingLotParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
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
	}

	private void handlePreserveSpotParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
	}

	private void handleRegisterDefectSpotParse(String[] args) 
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
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
	}

	private void handleSubmitInAdvanceParkingParse(String[] args) 
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
	}

	private void handlePayInAdvanceParkingParse(String[] args) 
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
	}

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
	}

	private void handleSignInAsAdminParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
	}

	private void handleSignInParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);

	}

	private void handleSignUpParse(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
		SignUpController.SignupServerResponse(statusFromServer);
	}
	
	private void handleLogout(String[] args)
	{
		String statusFromServer = parseMessage(args);
		System.out.println(statusFromServer);
		
	}
	private String parseMessage(String[] args)
	{
		int argsLength = args.length;
		String statusFromServer = "";
		String space = " ";
		for(int i=1; i<argsLength; i++)
		{
			statusFromServer += space;
			statusFromServer += args[i];
		}
		
		return statusFromServer;
	}
}
