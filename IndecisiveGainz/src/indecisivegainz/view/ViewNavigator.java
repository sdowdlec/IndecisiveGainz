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
			mainStage.setScene(scene);
			mainStage.show();
		}
		catch(IOException e)
		{
			System.err.println("Error loading: " + sceneFXML + "\n" + e.getMessage());
			e.printStackTrace();
		}
	}
}
