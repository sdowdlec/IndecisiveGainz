package indecisivegainz.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
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
	private ObservableList<String> mGeneratedRoutineList;
	
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
	private static final String[] TRACKED_WORKOUTS_FIELD_NAMES =  { "id", "muscle_group", "workout_name", "weight", "reps", "date_recorded" };
	private static final String[] TRACKED_WORKOUTS_FIELD_TYPES = { "INTEGER PRIMARY KEY", "TEXT", "TEXT", "REAL", "INTEGER", "TEXT" };
	
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
			theInstance.mGeneratedRoutineList = FXCollections.observableArrayList();
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
						mAllShoulderWorkoutsList.add(workoutName);
						break;
					case "Chest":
						mAllChestWorkoutsList.add(workoutName);
						break;
					case "Abs":
						mAllAbWorkoutsList.add(workoutName);
						break;
					case "Back":
						mAllBackWorkoutsList.add(workoutName);
						break;
					case "Biceps":
						mAllBicepWorkoutsList.add(workoutName);
						break;
					case "Triceps":
						mAllTricepWorkoutsList.add(workoutName);
						break;
					case "Legs":
						mAllLegWorkoutsList.add(workoutName);
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
	public int initializeTrackedWorkoutsHistoryList(String workoutName)
	{
		// "id", "muscle_group", "workout_name", "weight", "reps", "date_recorded"
		int createdRecords = 0;
		
		if(mCurrentlyViewedTrackedWorkoutList.size() != 0)
			mCurrentlyViewedTrackedWorkoutList.clear();
		
		try 
		{
			ResultSet trackedWorkouts = mTrackedWorkoutsDB.getRecordsOnField(TRACKED_WORKOUTS_FIELD_NAMES[2], workoutName);
				
			while(trackedWorkouts.next())
			{
				int id = trackedWorkouts.getInt(TRACKED_WORKOUTS_FIELD_NAMES[0]);
				String muscleGroup = trackedWorkouts.getString(TRACKED_WORKOUTS_FIELD_NAMES[1]);
				String name = trackedWorkouts.getString(TRACKED_WORKOUTS_FIELD_NAMES[2]);
				int reps = trackedWorkouts.getInt(TRACKED_WORKOUTS_FIELD_NAMES[3]);
				double weight = trackedWorkouts.getDouble(TRACKED_WORKOUTS_FIELD_NAMES[4]);
				String dateRecorded = trackedWorkouts.getString(TRACKED_WORKOUTS_FIELD_NAMES[5]);
						
				mCurrentlyViewedTrackedWorkoutList.add(new TrackedWorkout(id, muscleGroup, name, reps, weight, dateRecorded));
				++createdRecords;
			}
		} 
		catch (SQLException e) 
		{
				e.printStackTrace();
		}
		
		return createdRecords;
	}
	
	public ObservableList<TrackedWorkout> getTrackedHistoryList()
	{
		return mCurrentlyViewedTrackedWorkoutList;
	}
	
	public ObservableList<Workout> getAllWorkoutsList()
	{
		return mAllWorkoutsList;
	}
	
	public ObservableList<String> getAllMuscleGroups()
	{
		FXCollections.sort(mAllMuscleGroupsList);
		return mAllMuscleGroupsList;
	}
	
	public ObservableList<String> getAllShoulderWorkoutsList()
	{
		FXCollections.sort(mAllShoulderWorkoutsList);
		return mAllShoulderWorkoutsList;
	}
	public ObservableList<String> getAllChestWorkoutsList()
	{
		FXCollections.sort(mAllChestWorkoutsList);
		return mAllChestWorkoutsList;
	}
	public ObservableList<String> getAllAbWorkoutsList()
	{
		FXCollections.sort(mAllAbWorkoutsList);
		return mAllAbWorkoutsList;
	}
	public ObservableList<String> getAllBicepWorkoutsList()
	{
		FXCollections.sort(mAllBicepWorkoutsList);
		return mAllBicepWorkoutsList;
	}
	public ObservableList<String> getAllBackWorkoutsList()
	{
		FXCollections.sort(mAllBackWorkoutsList);
		return mAllBackWorkoutsList;
	}
	public ObservableList<String> getAllTricepWorkoutsList()
	{
		FXCollections.sort(mAllTricepWorkoutsList);
		return mAllTricepWorkoutsList;
	}
	public ObservableList<String> getAllLegWorkoutsList()
	{
		FXCollections.sort(mAllLegWorkoutsList);
		return mAllLegWorkoutsList;
	}
	
	public boolean addNewWorkout(String workoutName, String muscleGroup)
	{
		// TODO Look into making this feel less hacky
		// TODO Fix bug where the new workout is being addd but not displayed in the ObservableList  / ListView
		if(workoutName.equals("") || muscleGroup == null)
			return false;
		
		int count = 0;
		for(Workout w : mAllWorkoutsList)
			if( !(w.getMuscleGroup().equalsIgnoreCase(muscleGroup) && w.getWorkoutName().equalsIgnoreCase(workoutName)) )
				++count;

		if(count == mAllWorkoutsList.size())
		{
			try
			{
				String[] values = { muscleGroup, workoutName };
				int id = mWorkoutsDB.createRecord(Arrays.copyOfRange(WORKOUTS_FIELD_NAMES, 1, WORKOUTS_FIELD_NAMES.length), values);
				mAllWorkoutsList.add(new Workout(id, muscleGroup, workoutName));
				addWorkoutToCorrectWorkoutList(workoutName, muscleGroup);
				return true;
			}
			catch(SQLException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	public void addWorkoutToCorrectWorkoutList(String workoutName, String muscleGroup)
	{
		switch(muscleGroup)
		{
			case "Shoulders":
				mAllShoulderWorkoutsList.add(workoutName);
				break;
			case "Chest":
				mAllChestWorkoutsList.add(workoutName);
				break;
			case "Abs":
				mAllAbWorkoutsList.add(workoutName);
				break;
			case "Back":
				mAllBackWorkoutsList.add(workoutName);
				break;
			case "Biceps":
				mAllBicepWorkoutsList.add(workoutName);
				break;
			case "Triceps":
				mAllTricepWorkoutsList.add(workoutName);
				break;
			case "Legs":
				mAllLegWorkoutsList.add(workoutName);
				break;
		}
	}
	
	public boolean trackNewWorkout(String workoutName, String muscleGroup, String reps, String weight, String dateRecorded)
	{
		if(reps.equals("") || weight.equals(""))
			return false;
		
		try
		{
			Integer.valueOf(reps);
			Double.valueOf(weight);
			
			String[] values = { muscleGroup, workoutName, reps, weight, dateRecorded };
			mTrackedWorkoutsDB.createRecord(Arrays.copyOfRange(TRACKED_WORKOUTS_FIELD_NAMES, 1, TRACKED_WORKOUTS_FIELD_NAMES.length), values);
			
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		catch(SQLException e)
		{
			return false;
		}
	}
	
	public boolean deleteTrackedWorkoutFromHistory(TrackedWorkout selectedItem)
	{
		try
		{
			mCurrentlyViewedTrackedWorkoutList.remove(selectedItem);
			mTrackedWorkoutsDB.deleteRecord(String.valueOf(selectedItem.getId()));
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public String getPersonalRecordFromHistory()
	{
		double pr = 0.0;
		for(TrackedWorkout tw : mCurrentlyViewedTrackedWorkoutList)
		{
			if(tw.getWeight() > pr)
				pr = tw.getWeight();
		}
		
		return (pr == 0.0) ? "N/A" : pr + " lbs";
	}
	
	/**
	 * 
	 * @param muscleGroups
	 * @param numUniqueWorkouts
	 */
	public void generateRoutine(String[] muscleGroups, int[] numUniqueWorkouts)
	{
		if(mGeneratedRoutineList.size() > 0)
			mGeneratedRoutineList.clear();
		
		for(int i = 0; i < muscleGroups.length; i++)
		{
			switch(muscleGroups[i])
			{
				case "Shoulders":
					if(numUniqueWorkouts[i] > mAllShoulderWorkoutsList.size())
						numUniqueWorkouts[i] = mAllShoulderWorkoutsList.size();
					for(int j = 0; j < numUniqueWorkouts[i]; j++)
					{
						int generatedIndex = new Random().nextInt(mAllShoulderWorkoutsList.size());
						String selectedWorkout = mAllShoulderWorkoutsList.get(generatedIndex);
						
						if(!mGeneratedRoutineList.contains(selectedWorkout))
							mGeneratedRoutineList.add(selectedWorkout);
						else
							j--;
					}
					break;
				case "Chest":
					if(numUniqueWorkouts[i] > mAllChestWorkoutsList.size())
						numUniqueWorkouts[i] = mAllChestWorkoutsList.size();
					for(int j = 0; j < numUniqueWorkouts[i]; j++)
					{
						int generatedIndex = new Random().nextInt(mAllChestWorkoutsList.size());
						String selectedWorkout = mAllChestWorkoutsList.get(generatedIndex);
						
						if(!mGeneratedRoutineList.contains(selectedWorkout))
							mGeneratedRoutineList.add(selectedWorkout);
						else
							j--;
					}
					break;
				case "Abs":
					if(numUniqueWorkouts[i] > mAllAbWorkoutsList.size())
						numUniqueWorkouts[i] = mAllAbWorkoutsList.size();
					for(int j = 0; j < numUniqueWorkouts[i]; j++)
					{
						int generatedIndex = new Random().nextInt(mAllAbWorkoutsList.size());
						String selectedWorkout = mAllAbWorkoutsList.get(generatedIndex);
						
						if(!mGeneratedRoutineList.contains(selectedWorkout))
							mGeneratedRoutineList.add(selectedWorkout);
						else
							j--;
					}
					break;
				case "Back":
					if(numUniqueWorkouts[i] > mAllBackWorkoutsList.size())
						numUniqueWorkouts[i] = mAllBackWorkoutsList.size();
					for(int j = 0; j < numUniqueWorkouts[i]; j++)
					{
						int generatedIndex = new Random().nextInt(mAllBackWorkoutsList.size());
						String selectedWorkout = mAllBackWorkoutsList.get(generatedIndex);
						
						if(!mGeneratedRoutineList.contains(selectedWorkout))
							mGeneratedRoutineList.add(selectedWorkout);
						else
							j--;
					}
					break;
				case "Biceps":
					if(numUniqueWorkouts[i] > mAllBicepWorkoutsList.size())
						numUniqueWorkouts[i] = mAllBicepWorkoutsList.size();
					for(int j = 0; j < numUniqueWorkouts[i]; j++)
					{
						int generatedIndex = new Random().nextInt(mAllBicepWorkoutsList.size());
						String selectedWorkout = mAllBicepWorkoutsList.get(generatedIndex);
						
						if(!mGeneratedRoutineList.contains(selectedWorkout))
							mGeneratedRoutineList.add(selectedWorkout);
						else
							j--;
					}
					break;
				case "Triceps":
					if(numUniqueWorkouts[i] > mAllTricepWorkoutsList.size())
						numUniqueWorkouts[i] = mAllTricepWorkoutsList.size();
					for(int j = 0; j < numUniqueWorkouts[i]; j++)
					{
						int generatedIndex = new Random().nextInt(mAllTricepWorkoutsList.size());
						String selectedWorkout = mAllTricepWorkoutsList.get(generatedIndex);
						
						if(!mGeneratedRoutineList.contains(selectedWorkout))
							mGeneratedRoutineList.add(selectedWorkout);
						else
							j--;
					}
					break;
				case "Legs":
					if(numUniqueWorkouts[i] > mAllLegWorkoutsList.size())
						numUniqueWorkouts[i] = mAllLegWorkoutsList.size();
					for(int j = 0; j < numUniqueWorkouts[i]; j++)
					{
						int generatedIndex = new Random().nextInt(mAllLegWorkoutsList.size());
						String selectedWorkout = mAllLegWorkoutsList.get(generatedIndex);
						
						if(!mGeneratedRoutineList.contains(selectedWorkout))
							mGeneratedRoutineList.add(selectedWorkout);
						else
							j--;
					}
					break;
			}
		}
	}
	
	public ObservableList<String> getGeneratedRoutine()
	{
		return mGeneratedRoutineList;
	}
	
	/**
	 * Check if an array contains duplicates in O(n) complexity.
	 * Sets cannot contain duplicates so if you try to add a duplicate it will return false.
	 * @param list
	 * @return
	 */
	public boolean containsDuplicates(String[] list)
	{
		HashSet<String> set = new HashSet<>();
		
		for(String element : list)
			if(set.add(element) == false)
				return true;
		
		return false;
	}
	
	@Override
	public void close() throws Exception 
	{
		mWorkoutsDB.close();
		mTrackedWorkoutsDB.close();
	}
}
