package indecisivegainz.view;

import java.net.URL;
import java.util.ResourceBundle;

import indecisivegainz.controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

public class ViewGeneratedRoutinePane implements Initializable
{
	private static Controller controller = Controller.getInstance();
	
	@FXML
	private BorderPane workouts;
	@FXML
	private Button backButton;
	@FXML
	private Button trackWorkoutButton;
	@FXML
	private ListView<String> generatedRoutineLV;
	@FXML
	private Label messageTF;

	// Event Listener on Button[#backButton].onAction
	@FXML
	public void loadGenerateRoutinePane()
	{
		ViewNavigator.loadPane("GenerateRoutinePane.fxml");
	}
	// Event Listener on Button[#trackWorkoutButton].onAction
	@FXML
	public void loadTrackWorkoutPane()
	{
		WorkoutsPane.setSelectedWorkout(generatedRoutineLV.getSelectionModel().getSelectedItem());
		TrackWorkoutPane.setIsViewGeneratedPath(true);
		ViewNavigator.loadPane("TrackWorkoutPane.fxml");
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1)
	{
		generatedRoutineLV.setItems(controller.getGeneratedRoutine());
		messageTF.setVisible(false);
		messageTF.setText("One or more muscle groups contains fewer workouts than specified.\n"
						+ "The maximum amount of workouts for than muscle group has been generated instead.");
	}
}
