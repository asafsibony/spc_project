package application;

import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class LoginController
{
	@FXML // fx:id="SignInButton"
	private Button SignInButton; // Value injected by FXMLLoader
	@FXML
	private Button SignInAsAdminButton;

	@FXML
	private Button SignUpButton;

	@FXML
	private Button backButton;

	@FXML
	void SignInAction(ActionEvent event) throws IOException
	{
		Parent parent = FXMLLoader.load(getClass().getResource("ClientsSystemScene.fxml"));
		Scene child = new Scene(parent);

		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setScene(child);
		window.show();
	}	

	@FXML
	void SignInAsAdminAction(ActionEvent event) throws IOException
	{
		Parent parent = FXMLLoader.load(getClass().getResource("AdminSystemScene.fxml"));
		Scene child = new Scene(parent);

		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setScene(child);
		window.show();
	}

	@FXML
	void SignUpAction(ActionEvent event) throws IOException 
	{
		Parent parent = FXMLLoader.load(getClass().getResource("SignUpScene.fxml"));
		Scene child = new Scene(parent);

		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setScene(child);
		window.show();
	}

	@FXML
	void backAction(ActionEvent event) throws IOException 
	{
		Parent parent = FXMLLoader.load(getClass().getResource("CheckInCheckOutScene.fxml"));
		Scene child = new Scene(parent);

		Stage window = (Stage) (((Node) event.getSource()).getScene().getWindow());
		window.setScene(child);
		window.show();
	}
}


