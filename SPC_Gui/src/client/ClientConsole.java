// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
package client;
import java.io.*;
import client.*;


/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole implements ChatIF 
{
	//Class variables *************************************************

	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;

	//Instance variables **********************************************

	/**
	 * The instance of the client that created this ConsoleChat.
	 */
	ChatClient client;


	//Constructors ****************************************************

	/**
	 * Constructs an instance of the ClientConsole UI.
	 *
	 * @param host The host to connect to.
	 * @param port The port to connect on.
	 */
	public ClientConsole(String host, int port) 
	{  
		try 
		{
			client= new ChatClient(host, port, this);
		} 
		catch(IOException exception) 
		{
			System.out.println("Error: Can't setup connection!"
					+ " Terminating client.");
			System.exit(1);
		}

		/*TODO: add fields as needed in class*/
		/*TODO: extend the constructor to receive the needed info (user name, user id)*/
		/*TODO:check out AbstractClient API(NOTE ChatClient extends AbstractClient), to find out how to get the address of the client*/
	}


	//Instance methods ************************************************

	/**
	 * This method waits for input from the console.  Once it is 
	 * received, it sends it to the client's message handler.
	 */
	public void accept(String cmd) 
	{
		BufferedReader fromConsole = 
				new BufferedReader(new InputStreamReader(System.in));
		String message;
//		System.out.println("Please enter your command number:");
//		System.out.println("(1) Add new user");
//		System.out.println("(2) Get all users");
//		while(true) {
			try {
//				message = fromConsole.readLine();
//				int getOrder = Integer.parseInt(message);
//				if(getOrder < 1 || getOrder > 2) {
//					System.out.println("bad command, try again..");
//					continue;
//				}
//				if(getOrder==1) {
//					System.out.println("Enter user name:");
//					message = "1 "+fromConsole.readLine();
//					System.out.println("Enter password:");
//					message += " "+fromConsole.readLine();
//					client.handleMessageFromClientUI(message);
//				}
//				if(getOrder==2) {
//					client.handleMessageFromClientUI("2");
//				}
//				//break;
				client.handleMessageFromClientUI(cmd);
			}
			catch (Exception ex) 
			{
				System.out.println
				("Unexpected error while reading from console!");
			}
		}
//	}

	/**
	 * This method overrides the method in the ChatIF interface.  It
	 * displays a message onto the screen.
	 *
	 * @param message The string to be displayed.
	 */
	public void display(String message) 
	{
		System.out.println("> " + message);
	}


	//Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the Client UI.
	 *
	 * @param args[0] The host to connect to.
	 */
//	public static void main(String[] args) 
//	{
//		String host = "";
//		int port = 0;  //The port number
//		try
//		{
//			host = args[0];
//		}
//		catch(ArrayIndexOutOfBoundsException e)
//		{
//			host = "localhost";
//		}
//		/*TODO: add code to get user info(user name, user id) and pass it, NOTE: no need to check if info is valid */
//		ClientConsole chat= new ClientConsole(host, DEFAULT_PORT);
//		//chat.accept();  //Wait for console data			
//	}
}
//End of ConsoleChat class
