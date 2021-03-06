package mvc.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import mvc.controller.Controller;
/**
 * This pane is responsible for displaying all of the available
 * workouts for the selected muscle group.
 * @author Sean Dowdle
 *
 */
public class WorkoutsPane implements Initializable
{
	private static Controller controller = Controller.getInstance();
	
	public static String selectedWorkout;
	
	@FXML
	private BorderPane workouts;
	@FXML
	private Button backButton;
	@FXML
	private ListView<String> workoutsLV;

	/**
	 *  Event Listener on Button[#backButton].onAction
	 *  
	 *  Loads the MuscleGroupsPane
	 */
	@FXML
	public void loadMuscleGroupPane() 
	{
		ViewNavigator.loadPane(ViewNavigator.MUSCLE_GROUPS_PANE);
	}
	
	/**
	 * Loads the correct pane based on which button was originally pressed from the main menu.
	 * If the user selected Track Workouts, it will load the TrackWorkoutPane after WorkoutsPane.
	 * If the user selected View Workouts, it will load the . . . after WorkoutsPane.
	 * Both button use the same path to load MuscleGroups -> WorkoutsPane but then fork after.
	 */
	@FXML
	public void loadNextPane()
	{
		String selectedItem = workoutsLV.getSelectionModel().getSelectedItem();
		if(selectedItem != null)
		{
			selectedWorkout = selectedItem;
			ViewNavigator.loadPane(ViewNavigator.TRACK_WORKOUT_PANE);
		}
	}
	
	// Static methods for passing info between panes.
	public static String getSelectedWorkout() { return selectedWorkout; }
	public static void setSelectedWorkout(String workout) { selectedWorkout = workout; }

	/**
	 * Initializes the pane.
	 * Initializes the ListView with all of the workouts for that muscle group.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		switch(MuscleGroupsPane.getSelectedMuscleGroup())
		{
			case "Shoulders":
				workoutsLV.setItems(controller.getAllShoulderWorkoutsList());
				break;
			case "Chest":
				workoutsLV.setItems(controller.getAllChestWorkoutsList());
				break;
			case "Abs":
				workoutsLV.setItems(controller.getAllAbWorkoutsList());
				break;
			case "Back":
				workoutsLV.setItems(controller.getAllBackWorkoutsList());
				break;
			case "Biceps":
				workoutsLV.setItems(controller.getAllBicepWorkoutsList());
				break;
			case "Triceps":
				workoutsLV.setItems(controller.getAllTricepWorkoutsList());
				break;
			case "Legs":
				workoutsLV.setItems(controller.getAllLegWorkoutsList());
				break;
		}
	}
}
