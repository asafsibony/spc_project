package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class ClientsSystemController
{
    @FXML
    private Button LogOutButton;

    @FXML
    void LogOutAction(ActionEvent event) throws IOException
    {
    	Parent parent = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
		Scene child = new Scene(parent);

		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setScene(child);
		window.show();
    }
}
