package indecisivegainz.view;

import java.net.URL;
import java.util.ResourceBundle;
import indecisivegainz.controller.Controller;
import indecisivegainz.model.TrackedWorkout;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
/**
 * This pane is responsible for showing the user all of workouts they 
 * have tracked for a specific workout.
 * @author Sean Dowdle
 *
 */
public class TrackedWorkoutsHistoryPane implements Initializable
{
	private static Controller controller = Controller.getInstance();
	
	@FXML
	private BorderPane workouts;
	@FXML
	private Label workoutNameLabel;
	@FXML
	private Label prLabel;
	@FXML
	private ListView<TrackedWorkout> historyLV;
	@FXML
	private Button backButton;
	@FXML
	private Button deleteButton;
	
	private TrackedWorkout selectedItem;

	/**
	 *  Event Listener on Button[#backButton].onAction
	 *  
	 *  Loads the WorkoutsPane
	 */
	@FXML
	public void back() 
	{
		ViewNavigator.loadPane("WorkoutsPane.fxml");
	}
	
	/**
	 *  Event Listener on Button[#deleteButton].onAction
	 *  
	 *  Gets the selected workout from the ListView and enables/disables
	 *  the delete button based on if we have a valid selection on the ListView.
	 */
	@FXML
	public void selectTrackedWorkout()
	{
		if(historyLV.getSelectionModel().getSelectedIndex() != -1)
		{
			deleteButton.setDisable(false);
			selectedItem = historyLV.getSelectionModel().getSelectedItem();
		}
		else
		{
			deleteButton.setDisable(true);
		}
	}
	
	/**
	 * Returns a boolean based on if we could delete the TrackedWorkout from the ListView
	 * @return A boolean based on if we could delete the TrackedWorkout from the ListView
	 */
	@FXML
	public boolean deleteFromHistory() 
	{
		deleteButton.setDisable(true);
		boolean isDeleted = controller.deleteTrackedWorkoutFromHistory(selectedItem);
		prLabel.setText(controller.getPersonalRecordFromHistory());
		return isDeleted;
	}
	
	/**
	 * Initializes the pane.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		workoutNameLabel.setText(WorkoutsPane.getSelectedWorkout());
		controller.initializeTrackedWorkoutsHistoryList(WorkoutsPane.getSelectedWorkout());
		historyLV.setItems(controller.getTrackedHistoryList());
		deleteButton.setDisable(true);
		prLabel.setText(controller.getPersonalRecordFromHistory());
	}
}
