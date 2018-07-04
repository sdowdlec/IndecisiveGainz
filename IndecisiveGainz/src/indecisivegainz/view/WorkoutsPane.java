package indecisivegainz.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class WorkoutsPane {
	@FXML
	private BorderPane workouts;
	@FXML
	private Button backButton;
	@FXML
	private ListView workoutsLV;

	// Event Listener on Button[#backButton].onAction
	@FXML
	public void loadMuscleGroupPane() 
	{
		ViewNavigator.loadPane("MuscleGroupsPane.fxml");
	}
	
	@FXML
	public void loadTrackWorkoutPane()
	{
		ViewNavigator.loadPane("TrackWorkoutPane.fxml");
	}
}
