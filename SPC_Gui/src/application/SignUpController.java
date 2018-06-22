package application;

import java.io.IOException;

import client.ClientMessageParser;
import client.ConnectToServer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController extends CommonController
{
	
    @FXML
    private Button PreviousButton;
   
    @FXML
    private TextField userText;

    @FXML
    private PasswordField passText;
    
    
	@FXML
	public void initialize() 
	{

	}
    
    @FXML
    void PreviousAction(ActionEvent event) throws IOException 
    {
		super.openScene("LoginScene.fxml", event);
    }
    
    @FXML
    void signUpAction(ActionEvent event) {
    	String user = userText.getText();
    	String pass = passText.getText();
    	
    	if(super.validateInputNotNull(new String[] {user, pass}))
    	{
    		Main.cts.send("signUp " + user + " " + pass);
    	}
    	
    	else
    	{
    		super.displayNotAllFieldsFullError();
    	}
    }
}
