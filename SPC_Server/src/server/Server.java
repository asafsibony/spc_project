// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package server;
import java.io.*;

import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

public class Server extends AbstractServer 
{
	ServerMessageParser parser;
	
	public Server(int port) 
	{
		super(port);
		parser = new ServerMessageParser();
	}

	public void handleMessageFromClient
	(Object msg, ConnectionToClient client)
	{
		System.out.println("Message received: " + msg + " from " + client);
		try {
			parser.parse(msg.toString(), client);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO check choice
//		String[] str =  msg.toString().split("\\s+");
//		int id = Integer.parseInt(str[0]);
//		try {
//			if(id==1) {
//				if(str.length != 3)
//				{
//					System.out.println("wrong arguments eneterd by client");
//					return;
//				}
//				mysqlConnection.addUser(str[1], str[2]);
//				client.sendToClient("User "+str[1]+" added.");
//			}
//			if(id==2) {
//			client.sendToClient(mysqlConnection.showUsers());
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	protected void serverStarted()
	{
		System.out.println
		("Server listening for connections on port " + getPort());
	}


	protected void serverStopped()
	{
		System.out.println
		("Server has stopped listening for connections.");
	}
}
