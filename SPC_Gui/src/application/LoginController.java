package application;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

public class LoginController extends CommonController
{
	@FXML 
	private Button SignInButton; 
	@FXML
	private Button SignInAsAdminButton;
	@FXML
	private Button SignUpButton;
	@FXML
	private Button backButton;
	@FXML
    private PasswordField passwordText;
	@FXML
    private TextField userNameText;

	public static BooleanProperty clientLoginVerified;
	public static BooleanProperty adminLoginVerified;
	private ActionEvent lastEvent = null;
	
	@FXML
	public void initialize() 
	{
		clientLoginVerified= new SimpleBooleanProperty(false);
		adminLoginVerified= new SimpleBooleanProperty(false);
		clientLoginVerified.addListener(new ChangeListener<Object>(){
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
            		if(clientLoginVerified.getValue() == true)
            		{
            			clientLoginVerified.setValue(false);          		
            			clientSignIn();
            		}
            }
        });
		adminLoginVerified.addListener(new ChangeListener<Object>(){
            @Override
            public void changed(ObservableValue<?> observable, Object oldValue, Object newValue) {
            		if(adminLoginVerified.getValue() == true)
            		{
            			adminLoginVerified.setValue(false);          		
            			adminSignIn();
            		}
            }
        });
	}
	
	private void clientSignIn() {
		Platform.runLater(() -> {
			try {
				super.openScene("ClientsSystemScene.fxml", lastEvent);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	private void adminSignIn() {
		Platform.runLater(() -> {
			try {
				super.openScene("AdminSystemScene.fxml", lastEvent);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
	
	@FXML
	void SignInAction(ActionEvent event) throws IOException
	{
		String user = userNameText.getText();
		String pass = passwordText.getText();
		
		if(super.validateInputNotNull(new String[] {user, pass}))
		{
			Main.userName = user;
			String cmd = "SignIn " + user + " " + pass;
			Main.cts.send(cmd);
		}
		
		else
		{
			super.displayNotAllFieldsFullError();
			return;
		}
		lastEvent = event;
		//super.openScene("ClientsSystemScene.fxml", event);
	}	

	@FXML
	void SignInAsAdminAction(ActionEvent event) throws IOException
	{
		String user = userNameText.getText();
		String pass = passwordText.getText();
		
		if(super.validateInputNotNull(new String[] {user, pass}))
		{
			Main.userName = user;
			String cmd = "SignInAsAdmin " + user + " " + pass;
			Main.cts.send(cmd);
		}
		
		else
		{
			super.displayNotAllFieldsFullError();
			return;
		}
		lastEvent = event;
		//super.openScene("AdminSystemScene.fxml", event);
	}

	@FXML
	void SignUpAction(ActionEvent event) throws IOException 
	{
		super.openScene("SignUpScene.fxml", event);
	}

	@FXML
	void backAction(ActionEvent event) throws IOException 
	{
		super.openScene("CheckInCheckOutScene.fxml", event);
	}
	
}


