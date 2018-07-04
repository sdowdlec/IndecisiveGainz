package indecisivegainz.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import java.net.URL;
import java.util.ResourceBundle;
import indecisivegainz.controller.Controller;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class MuscleGroupsPane implements Initializable
{
	private static Controller controller = Controller.getInstance();
	
	public static String selectedMuscleGroup;
	
	public static String path;
	
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
			ViewNavigator.loadPane("WorkoutsPane.fxml");
		}
	}
	
	public static String getPath()
	{
		return path;
	}
	
	public static void setPath(String buttonPath)
	{
		path = buttonPath;
	}
	
	public static String getSelectedMuscleGroup()
	{
		return selectedMuscleGroup;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// TODO Auto-generated method stub
		muscleGroupsLV.setItems(controller.getAllMuscleGroups());
	}
}
