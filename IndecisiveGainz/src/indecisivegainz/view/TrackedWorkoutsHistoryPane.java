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

public class TrackedWorkoutsHistoryPane implements Initializable
{
	private static Controller controller = Controller.getInstance();
	
	@FXML
	private BorderPane workouts;
	@FXML
	private Label workoutNameLabel;
	@FXML
	private ListView<TrackedWorkout> historyLV;
	@FXML
	private Button backButton;
	@FXML
	private Button deleteButton;
	
	private TrackedWorkout selectedItem;

	// Event Listener on Button[#backButton].onAction
	@FXML
	public void back() 
	{
		ViewNavigator.loadPane("WorkoutsPane.fxml");
	}
	// Event Listener on Button[#deleteButton].onAction
	
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
	
	@FXML
	public boolean deleteFromHistory() 
	{
		deleteButton.setDisable(true);
		return controller.deleteTrackedWorkoutFromHistory(selectedItem);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		workoutNameLabel.setText(WorkoutsPane.getSelectedWorkout());
		controller.initializeTrackedWorkoutsHistoryList(WorkoutsPane.getSelectedWorkout());
		historyLV.setItems(controller.getTrackedHistoryList());
		deleteButton.setDisable(true);
	}
}
