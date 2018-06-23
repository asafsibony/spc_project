package server;

import java.io.IOException;

import ocsf.server.ConnectionToClient;
import spc.AdminSystem;
import spc.CheckInCheckOut;
import spc.ClientSystem;
import spc.CommonMethods;
import spc.Login;
import spc.SignUp;

public class ServerMessageParser
 {
	public ServerMessageParser() {}

	public void parse(String msg, ConnectionToClient client) throws IOException
	{
		String[] args =  msg.split("\\s+");
		
		if(args[0].equals("signUp")) 	//Done
		{
			String user= args[1];
			String pass= args[2];
			SignUp signUp = new SignUp();
			client.sendToClient("signUp "+signUp.signUpToDB(user, pass));
		}

		else if(args[0].equals("SignIn")) 	//Done
		{
			String user= args[1];
			String pass= args[2];
			Login login = new Login();
			client.sendToClient("SignIn " + login.SignIn(user, pass));
		}

		else if(args[0].equals("SignInAsAdmin")) 	//Done
		{
			String user= args[1];
			String pass= args[2];
			Login login = new Login();
			client.sendToClient("SignInAsAdmin " + login.SignInAsAdmin(user, pass));
		}

		else if(args[0].equals("submitCheckIn"))
		{
			String id = args[1];
			String carId = args[2];
			String dep = args[3];
			//String email = args[4];
			String type = args[4];

			CheckInCheckOut checkInCheckOut = new CheckInCheckOut();
			client.sendToClient("submitCheckIn " + checkInCheckOut.CheckInCarToDB(id, carId, dep, type));
		}
		
		else if(args[0].equals("submitCheckOutAction"))
		{
			String id = args[1];
			String carId = args[2];

			CheckInCheckOut checkInCheckOut = new CheckInCheckOut();
			client.sendToClient("submitCheckOutAction " + checkInCheckOut.submitCheckOut(id, carId));
		}
		
//		else if(args[0].equals("payCheckOut"))
//		{
//			String id = args[1];
//			String cost = args[2];
//
//			CheckInCheckOut checkInCheckOut = new CheckInCheckOut();
//			client.sendToClient("payCheckOut " + checkInCheckOut.payCheckOut(id, cost));
//		}

		else if(args[0].equals("leaveParkingLot"))
		{
			String id = args[1];
			String carId = args[2];

			CheckInCheckOut checkInCheckOut = new CheckInCheckOut();
			client.sendToClient("leaveParkingLot " + checkInCheckOut.removeCarFromDB(id, carId));
		}

		/* Client System */
		else if(args[0].equals("submitInAdvanceParking"))	//Done
		{
			String id = args[1];
			String carId = args[2];
			String arrivalDate = args[3];
			String arrivalHour = args[4];
			String depDate = args[5];
			String depHour = args[6];
			String parkingLot = args[7];
			String email = args[8];
			
			ClientSystem clientSystem = new ClientSystem();
			client.sendToClient("submitInAdvanceParking " + clientSystem.addInAdvanceParkingToDB(id, carId, arrivalDate, arrivalHour, depDate, depHour, parkingLot, email));
			
		}

		else if(args[0].equals("submitSubscription"))
		{
			String id = args[1];
			String carsId = args[2]; //need to parse cars id if there is more than one car (business)
			String date = args[3];		
			String regOrbuis = args[4];	

			String[] cars = carsId.split(" ");
			int numberOfCars = cars.length;			

			ClientSystem clientSystem = new ClientSystem();
			if(clientSystem.addSubscriptionToDB(id, carsId, date, regOrbuis).equals("succeeded"))
			{
				int cost = numberOfCars*74;
				client.sendToClient("submitSubscription " + String.valueOf(cost));						
			}
		}

		else if(args[0].equals("cancelOrder"))
		{
			String id = args[1];
			String carId = args[2];
			String date = args[3];
			String hour = args[4];

			ClientSystem clientSystem = new ClientSystem();
			client.sendToClient("cancelOrder " + clientSystem.cancelOrder(id, carId, date, hour));
		}

		else if(args[0].equals("viewOrder"))
		{
			String id = args[1];
			String carId = args[2];

			ClientSystem clientSystem = new ClientSystem();
			client.sendToClient("viewOrder " + clientSystem.cancelOrder(id, carId));
		}

		else if(args[0].equals("complaint"))
		{
			String id = args[1];

			ClientSystem clientSystem = new ClientSystem();
			client.sendToClient("complaint " + clientSystem.complaint(id));
		}

		/* Admin System */
		else if(args[0].equals("registerDefectSpot"))	//Done
		{
			String parkingLot = args[1];
			String floor = args[2];
			String row = args[3];
			AdminSystem adminSystem = new AdminSystem();
			client.sendToClient("registerDefectSpot " + adminSystem.registerDefectSpot(parkingLot, floor, row));
		}

		else if(args[0].equals("preserveSpot"))		//Done
		{
			String parkingLot = args[1];
			String floor = args[2];
			String row = args[3];
			
			AdminSystem adminSystem = new AdminSystem();
			client.sendToClient("preserveSpot " + adminSystem.preserveSpot(parkingLot, floor, row));
		}

		else if(args[0].equals("submitUpdatePrices"))	//Done
		{
			String priceType = args[1];
			String newPrice = args[2];

			AdminSystem adminSystem = new AdminSystem();
			client.sendToClient("submitUpdatePrices " + adminSystem.updatePrices(priceType, newPrice));
		}

		else if(args[0].equals("produceSnapShot"))
		{
			String parkingLot = args[1];
			String spot = args[2];

			AdminSystem adminSystem = new AdminSystem();
			client.sendToClient("produceSnapShot " + adminSystem.produceSnapShot(parkingLot, spot));
		}

		else if(args[0].equals("producePerformanceReport"))
		{
			AdminSystem adminSystem = new AdminSystem();
			client.sendToClient("producePerformanceReport " + adminSystem.producePerformanceReport());
		}

		else if(args[0].equals("addNewParkingLot"))	//Done
		{
			String name = args[1];
			String floors = args[2];
			String spaces = args[3];
			String availableSpots = args[4];
			String SpotsInUse = args[5];
			
			AdminSystem adminSystem = new AdminSystem();
			client.sendToClient("addNewParkingLot " + adminSystem.addNewParkingLotToDB(name, floors, spaces, availableSpots, SpotsInUse));
		}
		else if(args[0].equals("logout")) 	//Done
		{
			String name = args[1];
			String type = args[2];
			Login login = new Login();
			client.sendToClient("logout " + login.Logout(name, type));
		}
		else if(args[0].equals("getParkingLots")) 	//Done
		{
			CommonMethods common = new CommonMethods();
			client.sendToClient("getParkingLots " + common.getParkingLotsNames());
		}
		
		else
		{
			System.out.println("Command sent from client not found: "+msg);
		}
	}
}

