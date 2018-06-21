package server;

public class Main {
	static mysqlConnection m;
	final public static int DEFAULT_PORT = 5555;

	public static void main(String[] args) {
		int port = 0;
		m = new mysqlConnection();
		try
		{
			port = Integer.parseInt(args[0]); //Get port from command line
		}
		catch(Throwable t)
		{
			port = DEFAULT_PORT; //Set port to 5555
		}
		Server sv = new Server(port);
		try 
		{
			sv.listen(); //Start listening for connections
		} 
		catch (Exception ex) 
		{
			System.out.println("ERROR - Could not listen for clients!");
		}
	}

}
