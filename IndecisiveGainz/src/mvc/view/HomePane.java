package mvc.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import mvc.controller.Controller;
/**
 * This class acts as the controller for HomePane.fxml
 * @author Sean Dowdle
 *
 */
public class HomePane implements Initializable
{
	private static Controller controller = Controller.getInstance();
	// I really hate the amount of variables here, hopefully there is a better way to do this.
	@FXML
	private Label welcomeLabel;
	@FXML
	private Label absWeightLabel;
	@FXML
	private Label backWeightLabel;
	@FXML
	private Label bicepsWeightLabel;
	@FXML
	private Label absPRDateLabel;
	@FXML
	private Label backPRDateLabel;
	@FXML
	private Label bicepsPRDateLabel;
	@FXML
	private Label chestWeightLabel;
	@FXML
	private Label chestPRDateLabel;
	@FXML
	private Label legsWeightLabel;
	@FXML
	private Label legsPRDateLabel;
	@FXML
	private Label shouldersWeightLabel;
	@FXML
	private Label shouldersPRDateLabel;
	@FXML
	private Label tricepsWeightLabel;
	@FXML
	private Label tricepsPRDateLabel;
	@FXML
	private Label absPercentLabel;
	@FXML
	private Label backPercentLabel;
	@FXML
	private Label bicepsPercentLabel;
	@FXML
	private Label chestPercentLabel;
	@FXML
	private Label legsPercentLabel;
	@FXML
	private Label shouldersPercentLabel;
	@FXML
	private Label tricepsPercentLabel;
	@FXML
	private Label AbsRepsLabel;
	@FXML
	private Label backRepsLabel;
	@FXML
	private Label bicepRepsLabel;
	@FXML
	private Label chestRepsLabel;
	@FXML
	private Label legRepsLabel;
	@FXML
	private Label shoulderRepsLabel;
	@FXML
	private Label tricepRepsLabel;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		// TODO Initialize HomePane
		
		// welcomeLabel.setText("Welcome " + controller.getCurrentUsername()); // THE ERROR FOR SQLITE BUSY IS HERE
		String[] allWeightPR = controller.getAllMaxWeight();
		absWeightLabel.setText(allWeightPR[0]);
		backWeightLabel.setText(allWeightPR[1]);
		bicepsWeightLabel.setText(allWeightPR[2]);
		chestWeightLabel.setText(allWeightPR[3]);
		legsWeightLabel.setText(allWeightPR[4]);
		shouldersWeightLabel.setText(allWeightPR[5]);
		tricepsWeightLabel.setText(allWeightPR[6]);
		
		String[] allPRDates = controller.getPRDatesFromList(allWeightPR);
		absPRDateLabel.setText(allPRDates[0]);
		backPRDateLabel.setText(allPRDates[1]);
		bicepsPRDateLabel.setText(allPRDates[2]);
		chestPRDateLabel.setText(allPRDates[3]);
		legsPRDateLabel.setText(allPRDates[4]);
		shouldersPRDateLabel.setText(allPRDates[5]);
		tricepsPRDateLabel.setText(allPRDates[6]);
	}

}
