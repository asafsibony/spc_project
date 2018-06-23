package application;
	
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import client.ChatClient;
import client.ClientConsole;
import client.ConnectToServer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Main extends Application {


	static public ConnectToServer cts;
	static public String userName="";
	
	public static void main(String[] args) 
	{
		cts = new ConnectToServer(args);
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws IOException 
	{
		// constructing our scene
		URL url = getClass().getResource("/application/CheckInCheckOutScene.fxml");
		AnchorPane pane = FXMLLoader.load(url);
		Scene scene = new Scene( pane );
		// setting the stage
		primaryStage.setScene( scene );
		primaryStage.setTitle( "Main Menu" );
		primaryStage.show();
		
		primaryStage.setOnHiding(new EventHandler<WindowEvent>() {
	         @Override
	         public void handle(WindowEvent event) {
	             Platform.runLater(new Runnable() {

	                 @Override
	                 public void run() {
	                     System.out.println("Application Closed by clicking the Close Button(X)");
	                     if(!userName.equals("")) {
	                 		Main.cts.send("logout " + Main.userName + " client");
	                 	}
	                     System.exit(0);
	                 }
	             });
	         }
	     });
	}	
}
