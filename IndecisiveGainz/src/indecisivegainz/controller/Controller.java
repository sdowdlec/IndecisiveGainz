package indecisivegainz.controller;
/**
 * 
 * @author Sean Dowdle
 *
 */
public class Controller
{
	private static Controller theInstance;
	
	private Controller() {}
	
	/** 
	 * 
	 * @return 
	 */
	public static Controller getInstance()
	{
		if(theInstance == null)
		{
			theInstance = new Controller();
		}
		return theInstance;
	}
}
