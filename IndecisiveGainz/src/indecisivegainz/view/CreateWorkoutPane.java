package indecisivegainz.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import indecisivegainz.controller.Controller;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class CreateWorkoutPane implements Initializable
{
	private static Controller controller = Controller.getInstance();
	
	@FXML
	private Label errorLabel;
	@FXML
	private BorderPane createWorkoutPane;
	@FXML
	private TextField workoutNameTF;
	@FXML
	private ComboBox<String> muscleGroupCB;
	@FXML
	private Button createWorkoutButton;
	@FXML
	private Button clearButton;

	// Event Listener on Button[#createWorkoutButton].onAction
	@FXML
	public void createNewWorkout() 
	{
		String workoutName = workoutNameTF.getText();
		String muscleGroup = muscleGroupCB.getSelectionModel().getSelectedItem();
		
		boolean isAdded = controller.addNewWorkout(workoutName, muscleGroup);
		if(isAdded)
		{
			errorLabel.setText("Workout was added successfully");
			errorLabel.setTextFill(Color.GREEN);
		}
		else
		{
			errorLabel.setText("\t\tWorkout was unable to be added\nYou may have missed a field or it may already exist");
			errorLabel.setTextFill(Color.RED);
		}
		errorLabel.setVisible(true);
	}
	// Event Listener on Button[#clearButton].onAction
	@FXML
	public void clearFields(ActionEvent event) 
	{
		workoutNameTF.clear();
		muscleGroupCB.getSelectionModel().select(-1);
		errorLabel.setVisible(false);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		muscleGroupCB.setItems(controller.getAllMuscleGroups());
		errorLabel.setVisible(false);
	}
}
