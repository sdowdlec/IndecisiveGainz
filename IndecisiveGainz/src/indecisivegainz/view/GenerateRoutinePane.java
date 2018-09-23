package indecisivegainz.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ResourceBundle;
import indecisivegainz.controller.Controller;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
/**
 * Acts as the Controller for GenerateRoutinePane.fxml
 * This pane is responsible for allowing the user to have
 * a workout routine auto-generated for them based on selected
 * parameters.
 * @author Sean Dowdle
 *
 */
public class GenerateRoutinePane implements Initializable
{
	private static Controller controller = Controller.getInstance();
	
	@FXML
	private Button generateRoutineButton;
	@FXML
	private Button clearButton;
	@FXML
	private GridPane gridPane;
	@FXML
	private Label statusMessage;
	@FXML
	private Button forwardButton;
	
	private ComboBox<String>[] comboBoxes;
	private TextField[] textFields;
	private Button[] buttons;
	
	private static int numMenuItems;
	private static String[] muscleGroups;
	private static int[] numUniqueWorkouts;
	
	public static int getNumMenuItems() { return numMenuItems; }
	public static String[] getMuscleGroups() { return muscleGroups; }
	public static int[] getNumUniqueWorkouts() { return numUniqueWorkouts; }
	/**
	 *  Event Listener on Button[#generateRoutineButton].onAction
	 *  
	 *  Auto-generates a workout routine based on the users input.
	 */
	@FXML
	public void generateRoutineButton() 
	{
		boolean isGeneratable = true;
		muscleGroups = new String[numMenuItems];
		numUniqueWorkouts = new int[numMenuItems];
		
		for(int i = 0; i < numMenuItems; i++)
		{
			if(comboBoxes[i].getSelectionModel().getSelectedIndex() == -1 || textFields[i].getText().equals(""))
			{
				isGeneratable = false;
				break;
			}
			
			try
			{ 
				Integer.valueOf(textFields[i].getText()); 
				muscleGroups[i] = comboBoxes[i].getSelectionModel().getSelectedItem();
				numUniqueWorkouts[i] = Integer.valueOf(textFields[i].getText());
			}
			catch(NumberFormatException e) 
			{ 
				isGeneratable = false; 
				break;
			}
			
		}
		
		if(controller.containsDuplicates(muscleGroups))
		{
			statusMessage.setText("Error Generating Routine.\nCannot have duplicate muscle groups.");
			statusMessage.setTextFill(Color.RED);
			
			forwardButton.setDisable(true);
			forwardButton.setVisible(false);
		}
		else if(isGeneratable)
		{
			statusMessage.setText("Routine Generated Successfully");
			statusMessage.setTextFill(Color.GREEN);
			
			controller.generateRoutine(muscleGroups, numUniqueWorkouts);
			
			forwardButton.setDisable(false);
			forwardButton.setVisible(true);
		}
		else
		{
			statusMessage.setText("Error Generating Routine. Check Fields.");
			statusMessage.setTextFill(Color.RED);
			
			forwardButton.setDisable(true);
			forwardButton.setVisible(false);
		}
	}
	/**
	 *  Event Listener on Button[#clearButton].onAction
	 *  
	 *  Clears the input fields of any entered values.
	 */
	@FXML
	public void clearFields() 
	{
		comboBoxes[0].getSelectionModel().select(-1);
		textFields[0].clear();
		
		for(int i = 1; i < numMenuItems; i++)
		{
			gridPane.getChildren().remove(comboBoxes[i]);
			gridPane.getChildren().remove(textFields[i]);
			gridPane.getChildren().remove(buttons[i]);
			comboBoxes[i] = null;
			textFields[i] = null;
			buttons[i] = null;
		}
		
		numMenuItems = 1;
		if(!gridPane.getChildren().contains(buttons[0]))
			gridPane.add(buttons[0], 0, 2);
		
		statusMessage.setText("");
		forwardButton.setDisable(true);
		forwardButton.setVisible(false);
	}
	
	/**
	 * Initialize a new ComboBox<String> object and return it.
	 * Sets the size and populates it with the muscle groups.
	 * @return ComboBox<String> object
	 */
	public ComboBox<String> createMuscleGroupsCB()
	{
		ComboBox<String> cb = new ComboBox<String>();
		cb.setPrefHeight(40);
		cb.setPrefWidth(160);
		cb.setMaxHeight(40);
		cb.setMaxWidth(160);
		cb.setPromptText("Select Muscle Group");
		cb.setItems(controller.getAllMuscleGroups());
		
		return cb;
	}
	
	/**
	 * Initializes a new TextField object, sets the properties for it,
	 * and returns it.
	 * @return A TextField object
	 */
	public TextField createNumRepsTF()
	{
		TextField tf = new TextField();
		tf.setPrefWidth(40);
		tf.setPrefHeight(40);
		tf.setMaxWidth(40);
		tf.setMaxHeight(40);
		
		return tf;
	}
	
	/**
	 * Initializes a new Button object, sets the properties for it,
	 * and returns it.
	 * @return A Button object.
	 */
	public Button createAddItemButton()
	{
		Button button = new Button();
		button.setText("+");
		button.setPrefWidth(40);
		button.setPrefHeight(40);
		button.setMaxWidth(40);
		button.setMaxHeight(40);
		button.setOnAction(e -> addMenuItem());
		
		return button;
	}
	
	private void addMenuItem() 
	{
		if(numMenuItems < 5)
		{
			ComboBox<String> cb = createMuscleGroupsCB();
			comboBoxes[numMenuItems] = cb;
			gridPane.add(cb, 0, numMenuItems + 1);
			
			TextField tf = createNumRepsTF();
			textFields[numMenuItems] = tf;
			gridPane.add(tf, 1, numMenuItems + 1);
			
			gridPane.getChildren().remove(buttons[numMenuItems - 1]);
			
			numMenuItems++;
			
			if(numMenuItems < 5)
			{
				Button b = createAddItemButton();
				buttons[numMenuItems - 1] = b;
				gridPane.add(b, 0, numMenuItems + 1);
			}
		}
	}
	
	/**
	 * Loads the ViewGeneratedRoutinePane
	 */
	@FXML
	public void loadViewGeneratedRoutinePane() {
		ViewNavigator.loadPane("ViewGeneratedRoutinePane.fxml");
	}
	
	/**
	 * Initializes the pane.
	 * On initialization of the pane, we set up just one ComboBox and one TextField
	 * for the user to enter input. We also create a button below those so that the user
	 * can click it and have the pane dynamically generate more ComboBox and TextFields. This
	 * is done so the user can enter varying amounts of muscle groups and workouts.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		numMenuItems = 0;
		comboBoxes = (ComboBox<String>[]) new ComboBox[5];
		textFields = new TextField[5];
		buttons = new Button[5];
		// Column then Row
		ComboBox<String> c = createMuscleGroupsCB();
		comboBoxes[numMenuItems] = c;
		gridPane.add(c, 0, 1);
		
		TextField t = createNumRepsTF();
		textFields[numMenuItems] = t;
		gridPane.add(t, 1, 1);
		
		Button b = createAddItemButton();
		buttons[numMenuItems] = b;
		gridPane.add(b, 0, 2);
		numMenuItems++;
		
		forwardButton.setDisable(true);
		forwardButton.setVisible(false);
	}
}
