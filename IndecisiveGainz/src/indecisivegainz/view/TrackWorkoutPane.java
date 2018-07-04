package indecisivegainz.view;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.scene.layout.BorderPane;

public class TrackWorkoutPane {
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
}
