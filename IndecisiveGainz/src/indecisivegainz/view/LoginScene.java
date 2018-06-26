package indecisivegainz.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.CheckBox;
/**
 * 
 * @author Sean Dowdle
 *
 */
public class LoginScene 
{
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

	// Event Listener on Label[#continueAsGuestLabel].onMouseClicked
	@FXML
	public void continueAsGuest() 
	{
		ViewNavigator.loadMainSceneDefault();
	}
	// Event Listener on Label[#signupLabel].onMouseClicked
	@FXML
	public void loadSignupScene() 
	{
		ViewNavigator.loadScene("Sign Up", ViewNavigator.SIGNUP_SCENE);
	}
	// Event Listener on CheckBox[#rememberMeCB].onAction
	@FXML
	public void rememberCredentials() 
	{
		// TODO
	}
	// Event Listener on Button[#loginButton].onAction
	@FXML
	public void login() 
	{
		// TODO
	}
}
