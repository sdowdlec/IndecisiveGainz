package indecisivegainz.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import indecisivegainz.model.*;
/**
 * This scene is responsible for allowing the user to create a new account.
 * @author Sean Dowdle
 *
 */
public class SignupScene 
{
	@FXML
	private TextField usernameTF;
	@FXML
	private PasswordField passwordTF;
	@FXML
	private PasswordField confirmPasswordTF;
	@FXML
	private Label errorLabel;
	@FXML
	private Button signUpButton;
	@FXML
	private Button backButton;

	/**
	 *  Event Listener on Button[#signUpButton].onAction
	 *  
	 *  Gets the user entered credentials and attempts to create an account based on those
	 *  credentials. Then, display a success or failure message to alert the user
	 *  if the account could be created or not.
	 */
	@FXML
	public void signUp() 
	{
		String username = usernameTF.getText();
		String password = passwordTF.getText();
		String confirmedPassword = confirmPasswordTF.getText();
		
		boolean wasCreated = Authentication.createAccount(username, password, confirmedPassword);
		errorLabel.setVisible(true);
		if(wasCreated)
		{
			errorLabel.setTextFill(Color.GREEN);
			errorLabel.setText("Account created successfuly!");
		}
		else
		{
			errorLabel.setTextFill(Color.RED);
			errorLabel.setText("\t\tUnable to create account.\nUsername may be taken or passwords do not match.");
		}
	}
	/**
	 *  Event Listener on Button[#backButton].onAction
	 *  
	 *  Loads the login scene.
	 */
	@FXML
	public void loadSigninScene() 
	{
		ViewNavigator.loadScene("Login", ViewNavigator.LOGIN_SCENE);
	}
}
