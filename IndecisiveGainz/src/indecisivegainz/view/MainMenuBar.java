package indecisivegainz.view;

import indecisivegainz.controller.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

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

	// Event Listener on Button[#logoutButton].onAction
	@FXML
	public void logout() 
	{
		controller.setCurrentUser(0);
		ViewNavigator.loadScene("Sign In", ViewNavigator.LOGIN_SCENE);
	}
	// Event Listener on Button[#createWorkoutButton].onAction
	@FXML
	public void loadCreateWorkoutPane() 
	{
		ViewNavigator.loadPane("CreateWorkoutPane.fxml");
	}
	// Event Listener on Button[#trackWorkoutButton].onAction
	@FXML
	public void loadMuscleGroupsPaneTrack() 
	{
		ViewNavigator.loadPane("MuscleGroupsPane.fxml");
		MuscleGroupsPane.setPath("track");
	}
	@FXML
	public void loadMuscleGroupsPaneView() 
	{
		ViewNavigator.loadPane("MuscleGroupsPane.fxml");
		MuscleGroupsPane.setPath("view");
	}
	// Event Listener on Button[#generateRoutineButton].onAction
	@FXML
	public void loadGenerateRoutineOptions() 
	{
		ViewNavigator.loadPane("GenerateRoutinePane.fxml");
	}
}
