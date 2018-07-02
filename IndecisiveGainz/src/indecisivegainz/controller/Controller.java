package indecisivegainz.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

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
	
	private DBModel mWorkoutsDB;
	private DBModel mTrackedWorkoutsDB;
	
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
	
	private static final String DB_NAME = "indecisive_gainz.db";
	// Workouts database constants
	private static final String WORKOUTS_TABLE_NAME = "workouts";
	private static final String[] WORKOUTS_FIELD_NAMES =  { "id", "muscle_group", "workout_name" };
	private static final String[] WORKOUTS_FIELD_TYPES = { "INTEGER PRIMARY KEY", "TEXT", "TEXT" };
	private static final String WORKOUTS_DATA_FILE = "Default Workouts.csv";
	// TrackedWorkouts database constants
	private static final String TRACKED_WORKOUTS_TABLE_NAME = "tracked_workouts";
	private static final String[] TRACKED_WORKOUTS_FIELD_NAMES =  { "id", "muscle_group", "workout_name", "weight", "reps" };
	private static final String[] TRACKED_WORKOUTS_FIELD_TYPES = { "INTEGER PRIMARY KEY", "TEXT", "TEXT", "REAL", "INTEGER" };
	
	/** 
	 * 
	 * @return 
	 */
	public static Controller getInstance()
	{
		if(theInstance == null)
		{
			theInstance = new Controller();
			theInstance.mCurrentlyViewedTrackedWorkout = FXCollections.observableArrayList();
			theInstance.mAllMuscleGroupsList = FXCollections.observableArrayList();
			theInstance.mAllShoulderWorkoutsList = FXCollections.observableArrayList();
			theInstance.mAllChestWorkoutsList = FXCollections.observableArrayList();
			theInstance.mAllAbWorkoutsList = FXCollections.observableArrayList();
			theInstance.mAllBicepWorkoutsList = FXCollections.observableArrayList();
			theInstance.mAllTricepWorkoutsList = FXCollections.observableArrayList();
			theInstance.mAllLegWorkoutsList = FXCollections.observableArrayList();
			
			try
			{
				theInstance.mWorkoutsDB = new DBModel(DB_NAME, WORKOUTS_TABLE_NAME, WORKOUTS_FIELD_NAMES, WORKOUTS_FIELD_TYPES);
				
				theInstance.mTrackedWorkoutsDB = new DBModel(DB_NAME, TRACKED_WORKOUTS_TABLE_NAME, TRACKED_WORKOUTS_FIELD_NAMES, TRACKED_WORKOUTS_FIELD_TYPES);
				
			}
			catch(SQLException e)
			{
				e.printStackTrace();
			}
			
		}
		return theInstance;
	}
	
	private int initializeWorkoutsDBFromFile() throws SQLException
	{
		int recordsCreated = 0;
		
		if(theInstance.mWorkoutsDB.getRecordCount() > 0)
			return recordsCreated;
		
		try
		{
			Scanner fileScanner = new Scanner(new File(WORKOUTS_DATA_FILE));
			fileScanner.nextLine();
			
			while(fileScanner.hasNextLine())
			{
				
			}
			
			fileScanner.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		return recordsCreated;
	}
}
