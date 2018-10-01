package mvc.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import mvc.model.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;

/**
 * The Controller class for the MVC design.
 * Contains all necessary methods to be used throughout the project.
 * @author Sean Dowdle
 *
 */
public class Controller implements AutoCloseable
{
	private static Controller theInstance;
	
	private Controller() {}
	
	private DBModel mWorkoutsDB;
	private DBModel mTrackedWorkoutsDB;
	private DBModel mUsersDB;
	
	private int mCurrentUser = 0;
	
	private ObservableList<Workout> mAllWorkoutsList;
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
	
	// SQLite database name
	private static final String DB_NAME = "indecisive_gainz.db";
	// Workouts database constants
	private static final String WORKOUTS_TABLE_NAME = "workouts";
	private static final String[] WORKOUTS_FIELD_NAMES =  { "id", "muscle_group", "workout_name", "user_id" };
	private static final String[] WORKOUTS_FIELD_TYPES = { "INTEGER PRIMARY KEY", "TEXT", "TEXT", "TEXT" };
	private static final String WORKOUTS_DATA_FILE = "Default Workouts.csv";
	// TrackedWorkouts database constants
	private static final String TRACKED_WORKOUTS_TABLE_NAME = "tracked_workouts";
	private static final String[] TRACKED_WORKOUTS_FIELD_NAMES =  { "id", "muscle_group", "workout_name", "weight", "reps", "date_recorded", "user_id" };
	private static final String[] TRACKED_WORKOUTS_FIELD_TYPES = { "INTEGER PRIMARY KEY", "TEXT", "TEXT", "REAL", "INTEGER", "TEXT", "TEXT" };
	// Users database constants
	private static final String USERS_TABLE_NAME = "users";
	private static final String[] USERS_FIELD_NAMES =  { "id", "username", "hashed_password" };
	private static final String[] USERS_FIELD_TYPES = { "INTEGER PRIMARY KEY", "TEXT", "TEXT" };
	
	/** 
	 * Uses lazy instantiation to instantiate an instance of the Controller.
	 * Initializes all necessary member variables with default data and existing data
	 * from the tables in the database.
	 * @return An instance of the Controller object
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
				theInstance.initializeMuscleGroupsList();
				
				// TrackedWorkouts
				theInstance.mTrackedWorkoutsDB = new DBModel(DB_NAME, TRACKED_WORKOUTS_TABLE_NAME, TRACKED_WORKOUTS_FIELD_NAMES, TRACKED_WORKOUTS_FIELD_TYPES);
				// theInstance.initializeTrackedWorkoutsList(theInstance.mTrackedWorkoutsDB.getAllRecords());
				
				// Users
				theInstance.mUsersDB = new DBModel(DB_NAME, USERS_TABLE_NAME, USERS_FIELD_NAMES, USERS_FIELD_TYPES);
				theInstance.initializeUsersDB();
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
				values[2] = "0";
				
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
	
	private void initializeUsersDB() throws SQLException
	{
		if(theInstance.mUsersDB.getRecordCount() == 0)
		{
			String[] values = { "Guest", null};
			theInstance.mUsersDB.createRecord(Arrays.copyOfRange(USERS_FIELD_NAMES, 1, USERS_FIELD_NAMES.length), values);
		}
	}
	
	/**
	 * Initializes all of the different Workouts lists.
	 * @throws SQLException
	 */
	public void initializeWorkoutLists()
	{
		try(ResultSet workouts = theInstance.mWorkoutsDB.getAllRecords();)
		{
			if(workouts != null)
			{
				if(!theInstance.mAllWorkoutsList.isEmpty())
				{
					theInstance.mAllWorkoutsList.clear();
					theInstance.mAllShoulderWorkoutsList.clear();
					theInstance.mAllChestWorkoutsList.clear();
					theInstance.mAllAbWorkoutsList.clear();
					theInstance.mAllBicepWorkoutsList.clear();
					theInstance.mAllBackWorkoutsList.clear();
					theInstance.mAllTricepWorkoutsList.clear();
					theInstance.mAllLegWorkoutsList.clear();
				}
				
				while(workouts.next())
				{
					String userId = workouts.getString("user_id");
					if(userId.equals("0") || userId.equals(String.valueOf(mCurrentUser)))
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
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	private void initializeMuscleGroupsList()
	{
		try(ResultSet workouts = theInstance.mWorkoutsDB.getAllRecords();)
		{
			while(workouts.next())
				if(!theInstance.mAllMuscleGroupsList.contains(workouts.getString(2)))
					theInstance.mAllMuscleGroupsList.add(workouts.getString(2));
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings("unused")
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
	 * specific workout that have been tracked by filtering on the workout name.
	 * @param workoutName The name of the workout to get the tracked history
	 * @return The number of records that were added to the list
	 * @throws SQLException
	 */
	public int initializeTrackedWorkoutsHistoryList(String workoutName)
	{
		// "id", "muscle_group", "workout_name", "weight", "reps", "date_recorded"
		int createdRecords = 0;
		
		if(mCurrentlyViewedTrackedWorkoutList.size() != 0)
			mCurrentlyViewedTrackedWorkoutList.clear();
		
		try(ResultSet trackedWorkouts = mTrackedWorkoutsDB.getRecordsOnField(TRACKED_WORKOUTS_FIELD_NAMES[2], workoutName);)
		{	
			while(trackedWorkouts.next())
			{
				String userId = trackedWorkouts.getString("user_id");
				if(userId.equals("0") || userId.equals(String.valueOf(mCurrentUser)))
				{
					int id = trackedWorkouts.getInt(TRACKED_WORKOUTS_FIELD_NAMES[0]);
					String muscleGroup = trackedWorkouts.getString(TRACKED_WORKOUTS_FIELD_NAMES[1]);
					String name = trackedWorkouts.getString(TRACKED_WORKOUTS_FIELD_NAMES[2]);
					double weight = trackedWorkouts.getDouble(TRACKED_WORKOUTS_FIELD_NAMES[3]);
					int reps = trackedWorkouts.getInt(TRACKED_WORKOUTS_FIELD_NAMES[4]);
					String dateRecorded = trackedWorkouts.getString(TRACKED_WORKOUTS_FIELD_NAMES[5]);
							
					mCurrentlyViewedTrackedWorkoutList.add(new TrackedWorkout(id, muscleGroup, name, weight, reps, dateRecorded));
					++createdRecords;
				}
			}
		} 
		catch (SQLException e) 
		{
				e.printStackTrace();
		}
		
		return createdRecords;
	}
	
	/**
	 * Adds a new workout to the database and Workouts lists if it is
	 * a workout not already added.
	 * @param workoutName The name of the workout to add
	 * @param muscleGroup The muscle group the workout belongs to
	 * @return A boolean based on if the workout was added or not
	 */
	public boolean addNewWorkout(String workoutName, String muscleGroup)
	{
		// TODO Look into making this feel less hacky
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
				String[] values = { muscleGroup, workoutName, String.valueOf(mCurrentUser) };
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
	
	private void addWorkoutToCorrectWorkoutList(String workoutName, String muscleGroup)
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
	
	/**
	 * Tracks the users weight and reps for a given workout to the tracked_workouts table.
	 * @param workoutName The name of the workout to track
	 * @param muscleGroup The muscle group the workout belongs to
	 * @param reps The number of reps recorded
	 * @param weight The weight recorded
	 * @param dateRecorded The date they tracked the workout
	 * @return A boolean based on if the workout was successfully tracked or not
	 */
	public boolean trackNewWorkout(String workoutName, String muscleGroup, String weight, String reps, String dateRecorded)
	{
		if(reps.equals("") || weight.equals(""))
			return false;
		
		try
		{
			Integer.valueOf(reps);
			Double.valueOf(weight);
			
			String[] values = { muscleGroup, workoutName, weight, reps, dateRecorded, String.valueOf(mCurrentUser) };
			mTrackedWorkoutsDB.createRecord(Arrays.copyOfRange(TRACKED_WORKOUTS_FIELD_NAMES, 1, TRACKED_WORKOUTS_FIELD_NAMES.length), values);
			
			return true;
		}
		catch(NumberFormatException e)
		{
			return false;
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Deletes the the selected TrackedWorkout from the database and
	 * mCurrentlyViewedTrackedWorkoutList.
	 * @param selectedItem The selected TrackedWorkout to delete
	 * @return A boolean based on if the selected item was deleted successfully
	 */
	public boolean deleteTrackedWorkoutFromHistory(TrackedWorkout selectedItem)
	{
		try
		{
			mTrackedWorkoutsDB.deleteRecord(String.valueOf(selectedItem.getId()));
			mCurrentlyViewedTrackedWorkoutList.remove(selectedItem);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Randomly generates a workout routine based on how many workouts the user
	 * wants to do for that specific muscle group.
	 * @param muscleGroups An array of all the muscle groups the user wants to generate their routine from
	 * @param numUniqueWorkouts The number of unique workouts to generate for each muscle group
	 */
	public void generateRoutine(String[] muscleGroups, int[] numUniqueWorkouts)
	{
		// TODO Look into reducing redundancies in the switch statement
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
	
	/**
	 * Check if an array contains duplicates in O(n) complexity.
	 * Sets cannot contain duplicates so if you try to add a duplicate it will return false.
	 * @param list The list to check for duplicates
	 * @return A boolean based on if the list contains duplicates or not
	 */
	public boolean containsDuplicates(String[] list)
	{
		HashSet<String> set = new HashSet<>();
		
		for(String element : list)
			if(set.add(element) == false)
				return true;
		
		return false;
	}
	
	// Getters
	
	/**
	 * Returns the generated routine list.
	 * @return The generated routine list
	 */
	public ObservableList<String> getGeneratedRoutine() { return mGeneratedRoutineList; }
	
	/**
	 * Returns the id of the currently logged in user.
	 * @return The id of the currently logged in user
	 */
	public int getCurrentUser() { return mCurrentUser; }
	
	/**
	 * Queries the users table to get the username based on a primary key.
	 * @return the username associated with the primary key
	 */
	public String getCurrentUsername()
	{
		String value = null;
		try {
			value = mUsersDB.getValueOnKey(mCurrentUser, USERS_FIELD_NAMES[1]);
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	/**
	 * Returns the users personal record for weight lifted 
	 * for the workout they are currently viewing.
	 * @return The users personal record for weight lifted
	 */
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
	
	public ObservableList<TrackedWorkout> getTrackedHistoryList() {
		return mCurrentlyViewedTrackedWorkoutList;
	}
	
	public ObservableList<Workout> getAllWorkoutsList() {
		return mAllWorkoutsList;
	}
	
	public ObservableList<String> getAllMuscleGroups() {
		FXCollections.sort(mAllMuscleGroupsList);
		return mAllMuscleGroupsList;
	}
	
	public ObservableList<String> getAllShoulderWorkoutsList() {
		FXCollections.sort(mAllShoulderWorkoutsList);
		return mAllShoulderWorkoutsList;
	}
	public ObservableList<String> getAllChestWorkoutsList() {
		FXCollections.sort(mAllChestWorkoutsList);
		return mAllChestWorkoutsList;
	}
	public ObservableList<String> getAllAbWorkoutsList() {
		FXCollections.sort(mAllAbWorkoutsList);
		return mAllAbWorkoutsList;
	}
	public ObservableList<String> getAllBicepWorkoutsList() {
		FXCollections.sort(mAllBicepWorkoutsList);
		return mAllBicepWorkoutsList;
	}
	public ObservableList<String> getAllBackWorkoutsList() {
		FXCollections.sort(mAllBackWorkoutsList);
		return mAllBackWorkoutsList;
	}
	public ObservableList<String> getAllTricepWorkoutsList() {
		FXCollections.sort(mAllTricepWorkoutsList);
		return mAllTricepWorkoutsList;
	}
	public ObservableList<String> getAllLegWorkoutsList() {
		FXCollections.sort(mAllLegWorkoutsList);
		return mAllLegWorkoutsList;
	}
	
	/**
	 * Gets the highest weight lifted for a tracked workout for each muscle group.
	 * A value for any given index may be set to null if there are no records for that muscle group in the table.
	 * This will be used by the home page for statistical purposes.
	 * @return an array of doubles of the highest weight lifted for each muscle group in alphabetical order
	 */
	public String[] getAllMaxWeight()
	{
		int numOfMuscleGroups = mAllMuscleGroupsList.size();
		String allMaxWeights[] = new String[numOfMuscleGroups];
		FXCollections.sort(mAllMuscleGroupsList);
		
		//  { "id", "muscle_group", "workout_name", "weight", "reps", "date_recorded", "user_id" }
		for(int i = 0; i < numOfMuscleGroups; i++)
		{
			String strMax = null;
			try {
				strMax = mTrackedWorkoutsDB.getMaxOnFieldId(TRACKED_WORKOUTS_FIELD_NAMES[3], TRACKED_WORKOUTS_FIELD_NAMES[1], mAllMuscleGroupsList.get(i), TRACKED_WORKOUTS_FIELD_NAMES[6], mCurrentUser);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}

			allMaxWeights[i] = strMax;
		}
		
		return allMaxWeights;
	}
	
	/**
	 * Gets the date recorded for each weight personal record.
	 * A value for any given index may be set to null if there are no records for that muscle group in the table.
	 * @param prList An array of the PRs for each muscle group to use to query the date recorded for it
	 * @return an array containing all of the dates recorded for each personal record
	 */
	public String[] getPRDatesFromList(String[] prList)
	{
		int listSize = prList.length;
		String[] allPRDates = new String[listSize];
		
		for(int i = 0; i < listSize; i++)
		{
			String prDate = null;
			if(prList[i] != null)
			{
				try {
					prDate = mTrackedWorkoutsDB.getItemOnConditionsId(prList[i], TRACKED_WORKOUTS_FIELD_NAMES[3], TRACKED_WORKOUTS_FIELD_NAMES[5], TRACKED_WORKOUTS_FIELD_NAMES[6], mCurrentUser);
					String[] dateData = prDate.split("\\.");
					prDate = dateData[1] + "/" + dateData[2] + "/" + dateData[0];
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			allPRDates[i] = prDate;
		}
		
		return allPRDates;
	}
	
	/**
	 * Returns the percentage that each muscle group takes up of the users total tracked workouts.
	 * @return the percentage that each muscle group takes up of the users total tracked workouts.
	 */
	public double[] getWorkoutPercentages()
	{
		double numTrackedWorkouts = 0.0;
		
		try {
			numTrackedWorkouts = mTrackedWorkoutsDB.getRecordCountOnValue(TRACKED_WORKOUTS_FIELD_NAMES[6], String.valueOf(mCurrentUser));
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		int numMuscleGroups = mAllMuscleGroupsList.size();
		double[] allWorkoutPercentages = new double[numMuscleGroups];
		
		if(numTrackedWorkouts > 0.0)
		{
			for(int i = 0; i < numMuscleGroups; i++)
			{
				try {
					double numMuscleGroup = mTrackedWorkoutsDB.getRecordCountOnValueId(TRACKED_WORKOUTS_FIELD_NAMES[1], mAllMuscleGroupsList.get(i), TRACKED_WORKOUTS_FIELD_NAMES[6], mCurrentUser);
					allWorkoutPercentages[i] = (numMuscleGroup / numTrackedWorkouts) * 100;
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return allWorkoutPercentages;
	}

	
	// Setters
	
	/**
	 * Sets the current user id to the new user id.
	 * @param userId The new user id to set as the current user
	 */
	public void setCurrentUser(int userId) { mCurrentUser = userId; }
	
	
	
	/**
	 * Implemented from AutoCloseable.
	 * Closes the connections to the databases.
	 */
	@Override
	public void close() throws Exception 
	{
		mWorkoutsDB.close();
		mTrackedWorkoutsDB.close();
		mUsersDB.close();
	}
}
