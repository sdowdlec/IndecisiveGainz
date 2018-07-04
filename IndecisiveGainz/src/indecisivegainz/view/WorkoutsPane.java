package indecisivegainz.view;

import java.net.URL;
import java.util.ResourceBundle;

import indecisivegainz.controller.Controller;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

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

	// Event Listener on Button[#backButton].onAction
	@FXML
	public void loadMuscleGroupPane() 
	{
		ViewNavigator.loadPane("MuscleGroupsPane.fxml");
	}
	
	@FXML
	public void loadNextPane()
	{
		String selectedItem = workoutsLV.getSelectionModel().getSelectedItem();
		if(selectedItem != null)
		{
			selectedWorkout = selectedItem;
			if(MuscleGroupsPane.getPath().equals("track"))
			{
				ViewNavigator.loadPane("TrackWorkoutPane.fxml");
			}
			else
			{
				// TODO Create a workout history pane and load it here
				//ViewNavigator.loadPane("");
			}
		}
	}
	
	public static String getSelectedWorkout()
	{
		return selectedWorkout;
	}

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
