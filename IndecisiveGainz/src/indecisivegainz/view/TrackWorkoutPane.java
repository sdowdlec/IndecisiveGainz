package indecisivegainz.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import indecisivegainz.controller.Controller;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.Date;

public class TrackWorkoutPane implements Initializable
{
	private static Controller controller = Controller.getInstance();
	
	@FXML
	private Label statusMessage;
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
		String workoutName = WorkoutsPane.getSelectedWorkout();
		String muscleGroup = MuscleGroupsPane.getSelectedMuscleGroup();
		String reps = repsTF.getText();
		String weight = weightTF.getText();
		String dateRecorded = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		boolean isAdded = controller.trackNewWorkout(workoutName, muscleGroup, reps, weight, dateRecorded);
		if(isAdded)
		{
			statusMessage.setTextFill(Color.GREEN);
			statusMessage.setText("Workout successfully tracked");
		}
		else
		{
			statusMessage.setTextFill(Color.RED);
			statusMessage.setText("Workout was unable to be tracked\nOne or more fields may be invalid");
		}
		
		statusMessage.setVisible(true);
	}
	// Event Listener on Button[#clearButton].onAction
	@FXML
	public void clearFields() 
	{
		weightTF.clear();
		repsTF.clear();
		statusMessage.setVisible(false);
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
		statusMessage.setVisible(false);
	}
}
