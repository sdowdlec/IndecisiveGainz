package indecisivegainz.view;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.event.ActionEvent;

import javafx.scene.control.ListView;

import javafx.scene.input.MouseEvent;

import javafx.scene.layout.BorderPane;

public class MuscleGroupsPane {
	@FXML
	private BorderPane workouts;
	@FXML
	private Button backButton;
	@FXML
	private ListView muscleGroupsLV;

	// Event Listener on ListView[#muscleGroupsLV].onMouseClicked
	@FXML
	public void loadWorkoutsPane() 
	{
		ViewNavigator.loadPane("WorkoutsPane.fxml");
	}
}
