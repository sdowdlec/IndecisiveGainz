package indecisivegainz.view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 
 * @author Sean Dowdle
 *
 */
public class ViewNavigator 
{
	public static Stage mainStage;
	
	public static final String LOGIN_SCENE = "LoginScene.fxml";
	public static final String SIGNUP_SCENE = "SignupScene.fxml";
	public static final String MAIN_SCENE = "MainScene.fxml";
	
	/**
	 * 
	 * @param stage
	 */
	public static void setStage(Stage stage)
	{
		mainStage = stage;
	}
	
	/**
	 * 
	 * @param title
	 * @param sceneFXML
	 */
	public static void loadScene(String title, String sceneFXML)
	{
		try
		{	
			Scene scene = new Scene(FXMLLoader.load(ViewNavigator.class.getResource(sceneFXML)));
			mainStage.setTitle(title);
			mainStage.setScene(scene);
			
			if(sceneFXML.equals(LOGIN_SCENE) || sceneFXML.equals(SIGNUP_SCENE))
			{
				mainStage.setWidth(400);
				mainStage.setHeight(529);
				mainStage.setResizable(false);
			}
			else
				mainStage.setResizable(true);
			
			mainStage.show();
		}
		catch(IOException e)
		{
			System.err.println("Error loading: " + sceneFXML + "\n" + e.getMessage());
			e.printStackTrace();
		}
	}
}
