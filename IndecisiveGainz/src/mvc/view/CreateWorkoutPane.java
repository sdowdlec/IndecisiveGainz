package mvc.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import mvc.controller.Controller;
/**
 * Acts as the Controller for CreateWorkoutPane.fxml
 * This pane is responsible for allowing the user to create a
 * new Workout in order to track their progress for that Workout.
 * @author Sean Dowdle
 *
 */
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

	/**
	 *  Event Listener on Button[#createWorkoutButton].onAction
	 *  
	 *  Upon clicking the createWorkoutButton, the functions will get the
	 *  user entered values for the new Workout to create. It will call a
	 *  function from the controller to create the new Workout and will
	 *  display a success or failure message based on if the Workout could
	 *  be created.
	 */
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
	/**
	 * Event Listener on Button[#clearButton].onAction
	 * 
	 * Clears the input fields of any entered values.
	 */
	@FXML
	public void clearFields() 
	{
		workoutNameTF.clear();
		muscleGroupCB.getSelectionModel().select(-1);
		errorLabel.setVisible(false);
	}
	
	
	/**
	 * Initializes the pane.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		muscleGroupCB.setItems(controller.getAllMuscleGroups());
		errorLabel.setVisible(false);
	}
}
