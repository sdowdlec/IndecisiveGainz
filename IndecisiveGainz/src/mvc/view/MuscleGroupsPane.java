package mvc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import mvc.controller.Controller;
/**
 * This pane displays all of the muscle groups the user has
 * workouts for.
 * @author Sean Dowdle
 *
 */
public class MuscleGroupsPane implements Initializable
{
	private static Controller controller = Controller.getInstance();
	
	public static String selectedMuscleGroup;
	
	@FXML
	private BorderPane workouts;
	@FXML
	private Button backButton;
	@FXML
	private ListView<String> muscleGroupsLV;

	/*
	 * On mouse clicked, load the workouts pane and pass data to it.
	 */
	@FXML
	public void loadWorkoutsPane() 
	{
		String selectedItem = muscleGroupsLV.getSelectionModel().getSelectedItem();
		if(selectedItem != null)
		{
			selectedMuscleGroup = selectedItem;
			ViewNavigator.loadPane(ViewNavigator.WORKOUTS_PANE);
		}
	}
	
	public static String getSelectedMuscleGroup() { return selectedMuscleGroup; }
	
	/**
	 * Initializes the pane.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		muscleGroupsLV.setItems(controller.getAllMuscleGroups());
	}
}
