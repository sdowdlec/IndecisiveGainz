package mvc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import mvc.controller.Controller;

import java.util.Date;
/**
 * This pane is responsible for allowing the user
 * to track the weight and reps of a specific workout
 * they did.
 * @author Sean Dowdle
 *
 */
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
	
	private static boolean isViewGeneratedPath = false;

	/**
	 *  Event Listener on Button[#trackWorkoutButton].onAction
	 *  
	 *  Gets the user entered input and attempts to track their workout. Then
	 *  we display a success or failure message based on if the workout could be 
	 *  tracked.
	 */
	@FXML
	public void trackWorkout() 
	{
		String workoutName = WorkoutsPane.getSelectedWorkout();
		String muscleGroup = MuscleGroupsPane.getSelectedMuscleGroup();
		String reps = repsTF.getText();
		String weight = weightTF.getText();
		String dateRecorded = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
		
		boolean isAdded = controller.trackNewWorkout(workoutName, muscleGroup, weight, reps, dateRecorded);
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
	
	/**
	 *  Event Listener on Button[#clearButton].onAction
	 *  
	 *  Clears the input fields of any entered values.
	 */
	@FXML
	public void clearFields() 
	{
		weightTF.clear();
		repsTF.clear();
		statusMessage.setVisible(false);
	}
	
	@FXML
	public void loadHistoryPane()
	{
		ViewNavigator.loadPane(ViewNavigator.TRACKED_HISTORY_PANE);
	}
	
	/**
	 * 
	 * @param isViewPath
	 */
	public static void setIsViewGeneratedPath(boolean isViewPath)
	{
		isViewGeneratedPath = isViewPath;
	}
	
	/**
	 * Loads the previous pane the user was at.
	 */
	@FXML
	public void back()
	{
		if(isViewGeneratedPath)
			ViewNavigator.loadPane(ViewNavigator.VIEW_GENERATED_PANE);
		else
			ViewNavigator.loadPane(ViewNavigator.WORKOUTS_PANE);
		
		isViewGeneratedPath = false;
	}
	
	/**
	 * Initializes the pane.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) 
	{
		workoutNameLabel.setText(WorkoutsPane.getSelectedWorkout());
		statusMessage.setVisible(false);
	}
}
