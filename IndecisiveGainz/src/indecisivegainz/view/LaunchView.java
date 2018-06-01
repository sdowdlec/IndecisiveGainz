package indecisivegainz.view;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 * 
 * @author Sean Dowdle
 *
 */
public class LaunchView extends Application
{
	/**
	 * 
	 */
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		ViewNavigator.setStage(primaryStage);
		//ViewNavigator.loadScene("", ViewNavigator.);
	}
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		Application.launch(args);
	}
}
