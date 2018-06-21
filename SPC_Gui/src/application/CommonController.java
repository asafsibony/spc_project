package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class CommonController 
{
	public void openScene(String sceneName, ActionEvent event) throws IOException
	{
		Parent parent = FXMLLoader.load(getClass().getResource(sceneName));
		Scene child = new Scene(parent);

		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setScene(child);
		window.show();
	}
	
	public void displayMessage(String message)
	{
		new Alert(Alert.AlertType.CONFIRMATION, message).showAndWait();
	}
	
	public boolean validateInputNotNull(String... args)
	{
		for(String arg : args)
		{
			if(arg.equals(""))
			{
				return false;
			}
		}
		return true;
		
	}
	
	public void displayNotAllFieldsFullError()
	{
		new Alert(Alert.AlertType.ERROR, "Please fill out all fields").showAndWait();
	}
}
