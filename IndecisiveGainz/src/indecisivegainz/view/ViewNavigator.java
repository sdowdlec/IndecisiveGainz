package indecisivegainz.view;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * 
 * @author Sean Dowdle
 *
 */
public class ViewNavigator 
{
	public static HBox menuContainer = new HBox();
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
			{
				mainStage.setWidth(800);
				mainStage.setHeight(500);
				mainStage.setResizable(true);
			}
			
			mainStage.show();
		}
		catch(IOException e)
		{
			System.err.println("Error loading: " + sceneFXML + "\n" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static HBox getMenuContainer()
	{
		return menuContainer;
	}
	
	/**
	 * Sets up the main menu with a newly initialized HBox
	 * to hold the panes. By default, the mainScene will
	 * have the menu bar on the left.
	 */
	public static void loadMainSceneDefault()
	{
		mainStage.setResizable(true);
		mainStage.setWidth(800);
		mainStage.setHeight(500);
		loadPane("MainMenuBar.fxml");
		Scene scene = new Scene(menuContainer);
		mainStage.setTitle("Main Menu");
		mainStage.setScene(scene);
		mainStage.show();
	}
	
	/**
	 * Loads the fxml file along with its associated container and adds it to the main scene.
	 * If there are already 2 panes (Menu Bar + something else) it will remove the non-menu bar
	 * so we can add a new one.
	 * @param paneFXML A string containing the fxml file-name to be loaded from.
	 */
	public static void loadPane(String paneFXML)
	{
		try
		{
			BorderPane newPane = FXMLLoader.load(ViewNavigator.class.getResource(paneFXML));
			if(menuContainer.getChildren().size() > 1)
				menuContainer.getChildren().remove(1);
			menuContainer.getChildren().add(newPane);
		}
		catch(IOException e)
		{
			System.err.println("Error loading: " + paneFXML + "\n" + e.getMessage());
			e.printStackTrace();
		}
	}
}
