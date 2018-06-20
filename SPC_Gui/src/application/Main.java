package application;
	
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class Main extends Application {

	public static void main(String[] args) 
	{
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
	}	
}
