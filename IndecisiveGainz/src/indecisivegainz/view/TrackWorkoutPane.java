package indecisivegainz.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.layout.BorderPane;

public class TrackWorkoutPane implements Initializable
{
	@FXML
	private Label workoutNameLabel;
	@FXML
	private BorderPane trackWorkoutPane;
	@FXML
	private TextField weightTF;
	@FXML
	private TextField repsTF;
	@FXML
	private Button trackWorkoutButton;
	@FXML
	private Button clearButton;

	// Event Listener on Button[#trackWorkoutButton].onAction
	@FXML
	public void trackWorkout() 
	{
		// TODO 
	}
	// Event Listener on Button[#clearButton].onAction
	@FXML
	public void clearFields() 
	{
		weightTF.clear();
		repsTF.clear();
	}
	
	@FXML
	public void loadWorkoutsPane()
	{
		ViewNavigator.loadPane("WorkoutsPane.fxml");
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		workoutNameLabel.setText(WorkoutsPane.getSelectedWorkout());
	}
}
