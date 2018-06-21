package client;

import java.io.IOException;
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
		if(args[0].equals("SignIn"))
		{
			handleSignInParse(args);
		}
		
		if(args[0].equals("SignInAsAdmin"))
		{
			handleSignInAsAdminParse(args);
		}
		
		/* Check In and Check Out */
		if(args[0].equals("submitCheckIn"))
		{
			handleSubmitCheckInParse(args);
		}
		
		if(args[0].equals("payCheckOut"))
		{
			handlePayCheckOutParse(args);
		}
		
		if(args[0].equals("leaveParkingLot"))
		{
			handleLeaveParkingLotParse(args);
		}
		
		/* Client System */
		if(args[0].equals("submitInAdvanceParking"))
		{
			handleSubmitInAdvanceParkingParse(args);
		}
		
		if(args[0].equals("payInAdvanceParking"))
		{
			handlePayInAdvanceParkingParse(args);
		}
		
		if(args[0].equals("submitSubscription"))
		{
			handleSubmitSubscriptionParse(args);
		}
		
		if(args[0].equals("paySubscription"))
		{
			handlePaySubscriptionParse(args);
		}
		
		if(args[0].equals("cancelOrder"))
		{
			handleCancelOrderParse(args);
		}
		
		if(args[0].equals("viewOrder"))
		{
			handleViewOrderParse(args);
		}
		
		if(args[0].equals("complaint"))
		{
			handleComplaintParse(args);
		}
		
		/* Admin System */
		if(args[0].equals("registerDefectSpot"))
		{
			handleRegisterDefectSpotParse(args);
		}
		
		if(args[0].equals("preserveSpot"))
		{
			handlePreserveSpotParse(args);
		}
		
		if(args[0].equals("getPermissionForUpdatePrices"))
		{
			handleGetPermissionForUpdatePricesParse(args);
		}
		
		if(args[0].equals("produceSnapShot"))
		{
			handleProduceSnapShotParse(args);
		}
		
		if(args[0].equals("producePerformanceReport"))
		{
			handleProducePerformanceReportParse(args);
		}
		
		if(args[0].equals("addNewParkingLot "))
		{
			handleAddNewParkingLotParse(args);
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
		
	}

	private void handleProducePerformanceReportParse(String[] args)
	{
		String statusFromServer = parseMessage(args);

	}

	private void handleProduceSnapShotParse(String[] args)
	{
		String statusFromServer = parseMessage(args);

	}

	private void handleGetPermissionForUpdatePricesParse(String[] args)
	{
		String statusFromServer = parseMessage(args);

	}

	private void handlePreserveSpotParse(String[] args)
	{
		String statusFromServer = parseMessage(args);

	}

	private void handleRegisterDefectSpotParse(String[] args) 
	{
		String statusFromServer = parseMessage(args);

	}

	private void handleComplaintParse(String[] args) 
	{
		String statusFromServer = parseMessage(args);

	}

	private void handleViewOrderParse(String[] args)
	{
		String statusFromServer = parseMessage(args);

	}

	private void handleCancelOrderParse(String[] args)
	{
		String statusFromServer = parseMessage(args);

	}

	private void handlePaySubscriptionParse(String[] args)
	{
		String statusFromServer = parseMessage(args);

	}

	private void handleSubmitSubscriptionParse(String[] args)
	{
		String statusFromServer = parseMessage(args);

	}

	private void handleSubmitInAdvanceParkingParse(String[] args) 
	{
		String statusFromServer = parseMessage(args);

	}

	private void handlePayInAdvanceParkingParse(String[] args) 
	{
		String statusFromServer = parseMessage(args);

	}

	private void handleLeaveParkingLotParse(String[] args) 
	{
		String statusFromServer = parseMessage(args);

	}

	private void handlePayCheckOutParse(String[] args)
	{
		String statusFromServer = parseMessage(args);

	}

	private void handleSubmitCheckInParse(String[] args)
	{
		String statusFromServer = parseMessage(args);

	}

	private void handleSignInAsAdminParse(String[] args)
	{
		String statusFromServer = parseMessage(args);

	}

	private void handleSignInParse(String[] args)
	{
		String statusFromServer = parseMessage(args);

	}

	private void handleSignUpParse(String[] args)
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
