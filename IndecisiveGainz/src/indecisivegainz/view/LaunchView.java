package indecisivegainz.view;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 * This serves as the launching point of the GUI application.
 * We will launch the application to the login screen and the user
 * can go from there.
 * @author Sean Dowdle
 *
 */
public class LaunchView extends Application
{
	/**
	 * Sets up the primary stage for the GUI application's to
	 * be the login screen.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		ViewNavigator.setStage(primaryStage);
		ViewNavigator.loadScene("Login", ViewNavigator.LOGIN_SCENE);
	}
	
	/**
	 * The applications main method which launches the GUI application.
	 * @param args Main method arguments
	 */
	public static void main(String[] args)
	{
		Application.launch(args);
	}
}
