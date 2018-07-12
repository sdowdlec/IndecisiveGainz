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
import javafx.event.ActionEvent;

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
	
	private ComboBox<String>[] comboBoxes = (ComboBox<String>[]) new ComboBox[5];
	private TextField[] textFields = new TextField[5];
	private Button[] buttons = new Button[5];
	
	private int numMenuItems = 0;

	// Event Listener on Button[#generateRoutineButton].onAction
	@FXML
	public void generateRoutineButton() 
	{
		boolean isSuccess = true;
		for(int i = 0; i < numMenuItems; i++)
		{
			if(comboBoxes[i].getSelectionModel().getSelectedIndex() != -1 && !textFields[i].getText().equals(""))
			{
				statusMessage.setText("Routine Generated Successfully");
				statusMessage.setTextFill(Color.GREEN);
			}
			else
			{
				isSuccess = false;
				i = numMenuItems;
			}
		}
		if(isSuccess)
		{
			statusMessage.setText("Routine Generated Successfully");
			statusMessage.setTextFill(Color.GREEN);
			
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
		// TODO ViewNavigator.loadPane("");
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
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
