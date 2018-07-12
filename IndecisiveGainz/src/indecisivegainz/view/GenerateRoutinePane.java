package indecisivegainz.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

import indecisivegainz.controller.Controller;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;

public class GenerateRoutinePane implements Initializable
{
	private static Controller controller = Controller.getInstance();
	
	@FXML
	private Button generateRoutineButton;
	@FXML
	private Button clearButton;
	@FXML
	private GridPane gridPane;
	
	private ComboBox<String>[] comboBoxes = (ComboBox<String>[]) new ComboBox[5];
	private TextField[] textFields = new TextField[5];
	private Button[] buttons = new Button[5];
	
	private int numMenuItems = 0;

	// Event Listener on Button[#generateRoutineButton].onAction
	@FXML
	public void generateRoutineButton() 
	{
		
	}
	// Event Listener on Button[#clearButton].onAction
	@FXML
	public void clearFields() 
	{
		for(int i = 0; i < numMenuItems; i++)
		{
			gridPane.getChildren().remove(comboBoxes[i]);
			gridPane.getChildren().remove(textFields[i]);
			comboBoxes[i] = null;
			textFields[i] = null;
			buttons[i] = null;
		}
		numMenuItems = 1;
		Button b = createAddItemButton();
		buttons[numMenuItems - 1] = b;
		gridPane.add(b, 0, 2);
	}
	
	public ComboBox<String> createMuscleGroupsCB()
	{
		ComboBox<String> cb = new ComboBox<String>();
		cb.setPrefHeight(50);
		cb.setPrefWidth(160);
		cb.setMaxHeight(50);
		cb.setMaxWidth(160);
		cb.setPromptText("Select Muscle Group");
		cb.setItems(controller.getAllMuscleGroups());
		
		return cb;
	}
	
	public TextField createNumRepsTF()
	{
		TextField tf = new TextField();
		tf.setPrefWidth(50);
		tf.setPrefHeight(50);
		tf.setMaxWidth(50);
		tf.setMaxHeight(50);
		
		return tf;
	}
	
	public Button createAddItemButton()
	{
		Button button = new Button();
		button.setText("+");
		button.setPrefWidth(50);
		button.setPrefHeight(50);
		button.setMaxWidth(50);
		button.setMaxHeight(50);
		button.setOnAction(e -> addMenuItem());
		
		return button;
	}
	
	private void addMenuItem() 
	{
		if(numMenuItems < 5)
		{
			// TODO Delete the addItem button at the current index before adding new item.
			ComboBox<String> cb = createMuscleGroupsCB();
			comboBoxes[numMenuItems - 1] = cb;
			gridPane.add(cb, 0, numMenuItems + 1);
			
			TextField tf = createNumRepsTF();
			textFields[numMenuItems - 1] = tf;
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
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// Column then Row
		gridPane.add(createMuscleGroupsCB(), 0, 1);
		gridPane.add(createNumRepsTF(), 1, 1);
		Button b = createAddItemButton();
		buttons[numMenuItems] = b;
		gridPane.add(b, 0, 2);
		numMenuItems++;
	}
}
