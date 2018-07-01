package indecisivegainz.controller;

import javafx.collections.ObservableList;
import indecisivegainz.model.*;

/**
 * 
 * @author Sean Dowdle
 *
 */
public class Controller
{
	private static Controller theInstance;
	
	private Controller() {}
	
	private ObservableList<Workout> mAllWorkoutsList;
	/*
	private ObservableList<TrackedWorkout> mAllTrackedWorkoutsList;
	
	Might be Inefficient to store every single tracked workout in an ObservableList.
	Maybe just add it to the database, and make a observable list containing only the tracked workouts
	of a particular workout when viewed
	*/
	private ObservableList<TrackedWorkout> mCurrentlyViewedTrackedWorkout;
	private ObservableList<String> mAllMuscleGroupsList;
	
	private ObservableList<String> mAllShoulderWorkoutsList;
	private ObservableList<String> mAllChestWorkoutsList;
	private ObservableList<String> mAllAbWorkoutsList;
	private ObservableList<String> mAllBicepWorkoutsList;
	private ObservableList<String> mAllTricepWorkoutsList;
	private ObservableList<String> mAllLegWorkoutsList;
	
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
