package client;

public final class ConnectToServer {
	final public static int DEFAULT_PORT = 5555;
	public static ChatClient client;
	public static ClientConsole chat;
	public static String host = "";
	
	public ConnectToServer (String[] args) { // private constructor
		
		int port = 0;  //The port number
		try
		{
			host = args[0];
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			host = "localhost";
		}
		/*TODO: add code to get user info(user name, user id) and pass it, NOTE: no need to check if info is valid */
		//chat.accept();  //Wait for console data	
	}

	public void send(String cmd) {
		chat= new ClientConsole(host, DEFAULT_PORT);
		chat.accept(cmd);
	}
}
