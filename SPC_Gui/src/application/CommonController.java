package application;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	
	public boolean validateFieldIsANumber(String... args)
	{
		for (String arg : args)
		{
			try
			{
				Integer.parseInt(arg);
			}
			catch(Exception e)
			{
				new Alert(Alert.AlertType.ERROR, "Wrong input. Try again.").showAndWait();
				return false;
			}
		}
		return true;
	}
	
	public boolean validateHoursFormatCorrect(String... args)
	{
		String format = "HH:mm";
		
		for (String arg : args)
		{
			Date date = null;
			try {
	            SimpleDateFormat sdf = new SimpleDateFormat(format);
	            date = sdf.parse(arg);
	            if (!arg.equals(sdf.format(date))) {
	                date = null;
	            }
	        } catch (ParseException ex) {
	            ex.printStackTrace();
				new Alert(Alert.AlertType.ERROR, "Wrong input. Try again.").showAndWait();	     
	            return false;
	        }
		}
		return true;
	}
	
}
