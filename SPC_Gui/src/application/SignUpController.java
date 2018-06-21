package application;

import java.io.IOException;

import client.ConnectToServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    void PreviousAction(ActionEvent event) throws IOException 
    {
		super.openScene("LoginScene.fxml", event);
    }
    
    @FXML
    void signUpAction(ActionEvent event) {
    	String pass = passText.getText();
    	String user = userText.getText();
    	if(!pass.equals("") && !user.equals(""))
    	{
    		Main.cts.send("signUp "+user+" "+pass);
    	}
    }
}
