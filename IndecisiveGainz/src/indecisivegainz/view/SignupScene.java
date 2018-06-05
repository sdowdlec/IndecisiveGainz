package indecisivegainz.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
/**
 * 
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

	// Event Listener on Button[#signUpButton].onAction
	@FXML
	public void signUp() 
	{
		// TODO
	}
	// Event Listener on Button[#backButton].onAction
	@FXML
	public void loadSigninScene() 
	{
		// TODO
	}
}
