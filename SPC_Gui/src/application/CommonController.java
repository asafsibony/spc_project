package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
}
