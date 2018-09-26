package mvc.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import mvc.controller.Controller;
/**
 * Acts as a Controller for MainMenuBar.fxml
 * This pane will always be on the left side of the application
 * to act as a navigation tool. It will contain buttons to bring the
 * user to different panes so they can do different things on the application.
 * The selected panes will be loaded to the right of the main menu bar
 * @author Sean Dowdle
 *
 */
public class MainMenuBar
{
	private static Controller controller = Controller.getInstance();
	
	@FXML
	private BorderPane menu;
	@FXML
	private Button logoutButton;
	@FXML
	private Button createWorkoutButton;
	@FXML
	private Button generateRoutineButton;

	/**
	 * Event Listener on Button[#logoutButton].onAction
	 * 
	 * Logs the user out and brings them back into the login screen.
	 */
	@FXML
	public void logout() 
	{
		controller.setCurrentUser(0);
		ViewNavigator.loadScene("Sign In", ViewNavigator.LOGIN_SCENE);
	}
	
	/**
	 *  Loads the CreateWorkoutPane
	 */
	@FXML
	public void loadCreateWorkoutPane() 
	{
		ViewNavigator.loadPane("CreateWorkoutPane.fxml");
	}
	
	/**
	 *  Loads the MuscleGroupsPane
	 */
	@FXML
	public void loadMuscleGroupsPane() 
	{
		ViewNavigator.loadPane("MuscleGroupsPane.fxml");
	}
	
	/**
	 *  Event Listener on Button[#generateRoutineButton].onAction
	 *  
	 *  Loads the GenerateRoutinePane
	 */
	@FXML
	public void loadGenerateRoutineOptions() 
	{
		ViewNavigator.loadPane("GenerateRoutinePane.fxml");
	}
}
