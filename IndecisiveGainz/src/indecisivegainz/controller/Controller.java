package indecisivegainz.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import indecisivegainz.model.*;

/**
 * 
 * @author Sean Dowdle
 *
 */
public class Controller implements AutoCloseable
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
	private ObservableList<TrackedWorkout> mCurrentlyViewedTrackedWorkoutList;
	private ObservableList<String> mAllMuscleGroupsList;
	
	private ObservableList<String> mAllShoulderWorkoutsList;
	private ObservableList<String> mAllChestWorkoutsList;
	private ObservableList<String> mAllAbWorkoutsList;
	private ObservableList<String> mAllBicepWorkoutsList;
	private ObservableList<String> mAllBackWorkoutsList;
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
			theInstance.mCurrentlyViewedTrackedWorkoutList = FXCollections.observableArrayList();
			theInstance.mAllWorkoutsList = FXCollections.observableArrayList();
			theInstance.mAllMuscleGroupsList = FXCollections.observableArrayList();
			theInstance.mAllShoulderWorkoutsList = FXCollections.observableArrayList();
			theInstance.mAllChestWorkoutsList = FXCollections.observableArrayList();
			theInstance.mAllAbWorkoutsList = FXCollections.observableArrayList();
			theInstance.mAllBicepWorkoutsList = FXCollections.observableArrayList();
			theInstance.mAllBackWorkoutsList = FXCollections.observableArrayList();
			theInstance.mAllTricepWorkoutsList = FXCollections.observableArrayList();
			theInstance.mAllLegWorkoutsList = FXCollections.observableArrayList();
			
			try
			{
				/*
				1) Create a DBModel Object for the specific database
				2) Initialize the databases from files if need be
				3) Get a result set and add them to the observable list
				*/
				
				// Workouts
				theInstance.mWorkoutsDB = new DBModel(DB_NAME, WORKOUTS_TABLE_NAME, WORKOUTS_FIELD_NAMES, WORKOUTS_FIELD_TYPES);
				theInstance.initializeWorkoutsDBFromFile();
				theInstance.initializeWorkoutLists();
				theInstance.initializeMuscleGroupsList();
				
				// TrackedWorkouts
				theInstance.mTrackedWorkoutsDB = new DBModel(DB_NAME, TRACKED_WORKOUTS_TABLE_NAME, TRACKED_WORKOUTS_FIELD_NAMES, TRACKED_WORKOUTS_FIELD_TYPES);
				// theInstance.initializeTrackedWorkoutsList(theInstance.mTrackedWorkoutsDB.getAllRecords());
				
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
				String[] data = fileScanner.nextLine().split(",");
				String[] values = new String[WORKOUTS_FIELD_NAMES.length - 1];
				
				values[0] = data[0];
				values[1] = data[1];
				
				theInstance.mWorkoutsDB.createRecord(Arrays.copyOfRange(WORKOUTS_FIELD_NAMES, 1, WORKOUTS_FIELD_NAMES.length), values);
				++recordsCreated;
			}
			
			fileScanner.close();
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		return recordsCreated;
	}
	
	/**
	 * Initializes all of the different workouts lists.
	 * @param workouts The ResultSet containing all of the workouts in the database
	 * @throws SQLException
	 */
	private void initializeWorkoutLists() throws SQLException
	{
		ResultSet workouts = theInstance.mWorkoutsDB.getAllRecords();
		
		if(workouts != null)
		{
			while(workouts.next())
			{
				int id = workouts.getInt(WORKOUTS_FIELD_NAMES[0]);
				String muscleGroup = workouts.getString(WORKOUTS_FIELD_NAMES[1]);
				String workoutName = workouts.getString(WORKOUTS_FIELD_NAMES[2]);
				
				switch(muscleGroup)
				{
					case "Shoulders":
						theInstance.mAllShoulderWorkoutsList.add(workoutName);
						break;
					case "Chest":
						theInstance.mAllChestWorkoutsList.add(workoutName);
						break;
					case "Abs":
						theInstance.mAllAbWorkoutsList.add(workoutName);
						break;
					case "Back":
						theInstance.mAllBackWorkoutsList.add(workoutName);
						break;
					case "Biceps":
						theInstance.mAllBicepWorkoutsList.add(workoutName);
						break;
					case "Triceps":
						theInstance.mAllTricepWorkoutsList.add(workoutName);
						break;
					case "Legs":
						theInstance.mAllLegWorkoutsList.add(workoutName);
						break;
				}
				
				theInstance.mAllWorkoutsList.add(new Workout(id, muscleGroup, workoutName));
			}
		}
	}
	
	/**
	 * Initializes the mMuscleGroupsList to contain only UNIQUE muscle groups from the
	 * workouts table.
	 * @param workouts The ResultSet containing all of the workouts in the database
	 * @throws SQLException
	 */
	private void initializeMuscleGroupsList() throws SQLException
	{
		ResultSet workouts = theInstance.mWorkoutsDB.getAllRecords();
		
		while(workouts.next())
			if(!theInstance.mAllMuscleGroupsList.contains(workouts.getString(2)))
				theInstance.mAllMuscleGroupsList.add(workouts.getString(2));
	}
	
	/**
	 * 
	 * @param muscleGroup
	 * @return
	 */
	private boolean addToMuscleGroupsList(String muscleGroup)
	{
		if(!theInstance.mAllMuscleGroupsList.contains(muscleGroup))
		{
			theInstance.mAllMuscleGroupsList.add(muscleGroup);
			return true;
		}
		else
			return false;
			
	}
	
	/**
	 * Initializes the mCurrentlyViewedTrackedWorkoutsList to contain all records of that
	 * specific workout by filtering on the workout name.
	 * @param workoutName
	 * @return The number of records that were added to the list
	 * @throws SQLException
	 */
	public int initializeViewedTrackedWorkoutsList(String workoutName) throws SQLException
	{
		int createdRecords = 0;
		ResultSet trackedWorkouts = theInstance.mTrackedWorkoutsDB.getAllRecords();
		
		while(trackedWorkouts.next())
		{
			String name = trackedWorkouts.getString(TRACKED_WORKOUTS_FIELD_NAMES[1]);
			if(name.equalsIgnoreCase(workoutName))
			{
				int id = trackedWorkouts.getInt(TRACKED_WORKOUTS_FIELD_NAMES[0]);
				String muscleGroup = trackedWorkouts.getString(TRACKED_WORKOUTS_FIELD_NAMES[2]);
				int reps = trackedWorkouts.getInt(TRACKED_WORKOUTS_FIELD_NAMES[3]);
				double weight = trackedWorkouts.getDouble(TRACKED_WORKOUTS_FIELD_NAMES[4]);
				
				theInstance.mCurrentlyViewedTrackedWorkoutList.add(new TrackedWorkout(id, name, muscleGroup, reps, weight));
				++createdRecords;
			}
		}
		
		return createdRecords;
	}
	
	public ObservableList<String> getAllMuscleGroups()
	{
		return mAllMuscleGroupsList;
	}
	
	public ObservableList<String> getAllShoulderWorkoutsList()
	{
		return mAllShoulderWorkoutsList;
	}
	public ObservableList<String> getAllChestWorkoutsList()
	{
		return mAllChestWorkoutsList;
	}
	public ObservableList<String> getAllAbWorkoutsList()
	{
		return mAllAbWorkoutsList;
	}
	public ObservableList<String> getAllBicepWorkoutsList()
	{
		return mAllBicepWorkoutsList;
	}
	public ObservableList<String> getAllBackWorkoutsList()
	{
		return mAllBackWorkoutsList;
	}
	public ObservableList<String> getAllTricepWorkoutsList()
	{
		return mAllTricepWorkoutsList;
	}
	public ObservableList<String> getAllLegWorkoutsList()
	{
		return mAllLegWorkoutsList;
	}

	@Override
	public void close() throws Exception 
	{
		mWorkoutsDB.close();
		mTrackedWorkoutsDB.close();
	}
}
