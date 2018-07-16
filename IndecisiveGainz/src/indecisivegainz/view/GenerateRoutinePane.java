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
	
	public static int getNumMenuItems()
	{
		return numMenuItems;
	}
	public static String[] getMuscleGroups()
	{
		return muscleGroups;
	}
	public static int[] getNumUniqueWorkouts()
	{
		return numUniqueWorkouts;
	}
	// Event Listener on Button[#generateRoutineButton].onAction
	@FXML
	public void generateRoutineButton() 
	{
		// TODO add something to keep track in case the user wanted to generate more workouts than there are in the list
		// We could make a message show up at the bottom of the generated routines pane
		boolean isGeneratable = true;
		muscleGroups = new String[numMenuItems];
		numUniqueWorkouts = new int[numMenuItems];
		
		for(int i = 0; i < numMenuItems; i++)
		{
			// TODO Crashes when if user enters the same muscle group twice.
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
	// Event Listener on Button[#clearButton].onAction
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
	
	public TextField createNumRepsTF()
	{
		TextField tf = new TextField();
		tf.setPrefWidth(40);
		tf.setPrefHeight(40);
		tf.setMaxWidth(40);
		tf.setMaxHeight(40);
		
		return tf;
	}
	
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
	
	@FXML
	public void loadViewGeneratedRoutinePane()
	{
		ViewNavigator.loadPane("ViewGeneratedRoutinePane.fxml");
	}
	
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
