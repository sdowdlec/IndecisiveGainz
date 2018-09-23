package indecisivegainz.view;

import indecisivegainz.controller.Controller;
import indecisivegainz.model.Authentication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.CheckBox;
/**
 * This scene is responsible for allowing the user to either login 
 * with their credentials or as a guest. Once logged in, the user
 * will be brought to the main menu.
 * This will be the first screen the user sees when launching the
 * application.
 * @author Sean Dowdle
 *
 */
public class LoginScene 
{
	private static Controller controller = Controller.getInstance();
	
	@FXML
	private Label continueAsGuestLabel;
	@FXML
	private TextField usernameTF;
	@FXML
	private PasswordField passwordTF;
	@FXML
	private Label signupLabel;
	@FXML
	private CheckBox rememberMeCB;
	@FXML
	private Label incorrectCredentialsLabel;
	@FXML
	private Button loginButton;

	/**
	 *  Event Listener on Label[#continueAsGuestLabel].onMouseClicked
	 *  
	 *  Allows the user to login as a guest, initialize guest data, and load
	 *  the main menu.
	 */
	@FXML
	public void continueAsGuest() 
	{
		controller.setCurrentUser(1);
		controller.initializeWorkoutLists();
		ViewNavigator.loadMainSceneDefault();
	}
	
	/**
	 *  Event Listener on Label[#signupLabel].onMouseClicked
	 *  
	 *  Brings the user to the sign up screen where the user
	 *  can create an account.
	 */
	@FXML
	public void loadSignupScene() 
	{
		ViewNavigator.loadScene("Sign Up", ViewNavigator.SIGNUP_SCENE);
	}
	
	/**
	 *  Event Listener on CheckBox[#rememberMeCB].onAction
	 *  
	 *  Saves the user's credentials so that they do not have to re-enter them.
	 */
	@FXML
	public void rememberCredentials() 
	{
		// TODO Research how to do this
	}
	
	/**
	 *  Event Listener on Button[#loginButton].onAction
	 *  
	 *  Gets the user entered credentials and verifies them to
	 *  either log them in or alert of an invalid login.
	 */
	@FXML
	public void login() 
	{
		String username = usernameTF.getText();
		String password = passwordTF.getText();
		
		if(!username.equals("") && !password.equals(""))
		{
			boolean isValidLogin = Authentication.authenticateLogin(username, password);
			if(isValidLogin)
			{
				controller.setCurrentUser(Authentication.getUserId(username));
				System.out.println(controller.getCurrentUser());
				controller.initializeWorkoutLists();
				ViewNavigator.loadMainSceneDefault();
			}
			else
			{
				incorrectCredentialsLabel.setVisible(true);
				incorrectCredentialsLabel.setText("Invalid login.");
			}
		}
		else
		{
			incorrectCredentialsLabel.setVisible(true);
			incorrectCredentialsLabel.setText("Please enter your credentials.");
		}
	}
}
