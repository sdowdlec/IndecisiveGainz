package indecisivegainz.model;
/**
 * A subclass of Workout which represents a user tracked workout.
 * TrackedWorkouts consists of the number of reps
 * and amount of weight the user did for the given
 * workout.
 * @author Sean Dowdle
 *
 */
public class TrackedWorkout extends Workout 
{
	private int mReps;
	private double mWeight;
	private String mDateRecorded;
	
	/**
	 * Constructs a new TrackedWorkout object.
	 * A TrackedWorkout object represents a user tracked set for a given workout.
	 * A set consists of the amount of reps and weight, as well as which workout it was.
	 * @param id The id of the TrackedWorkout from the database
	 * @param workoutName The name of the workout the user wants to track
	 * @param muscleGroup The muscle group the workout is associated with that the user wants to track
	 * @param reps The number of reps the user recorded doing for the given set
	 * @param weight The amount of weight (in pounds) the user recorded doing for the given set
	 * @param dateRecorded The date that the user recorded doing the workout
	 */
	public TrackedWorkout(int id, String muscleGroup, String workoutName, int reps, double weight, String dateRecorded)
	{
		super(id, workoutName, muscleGroup);
		mReps = reps;
		mWeight = weight;
		mDateRecorded = dateRecorded;
	}
	
	/**
	 * Returns the id of the TrackedWorkout.
	 * @return the id of the TrackedWorkout
	 */
	public int getId()
	{
		return mId;
	}
	
	/**
	 * Sets the id of the TrackedWorkout.
	 * @param newId The new id to set
	 */
	public void setId(int newId)
	{
		mId = newId;
	}
	
	/**
	 * Returns the number of reps recorded for the given set and workout.
	 * @return the number of reps recorded
	 */
	public int getReps()
	{
		return mReps;
	}
	
	/**
	 * Returns the amount of weight recorded (in pounds) for the given set and workout.
	 * @return the amount of weight recorded (in pounds)
	 */
	public double getWeight()
	{
		return mWeight;
	}
	
	/**
	 * Sets the number of reps done for the TrackedWorkout.
	 * @param reps The number of reps to set
	 */
	public void setReps(int reps)
	{
		mReps = reps;
	}
	
	/**
	 * Sets the amount of weight lifted for the TrackedWorkout.
	 * @param weight The amount of weight (in pounds) to set
	 */
	public void setWeight(double weight)
	{
		mWeight = weight;
	}
	
	/**
	 * Returns the date the workout was tracked.
	 * @return the date the workout was tracked
	 */
	public String getDateRecorded()
	{
		return mDateRecorded;
	}
	
	/**
	 * Sets the date the TrackedWorkout was recorded.
	 * @param newDate The date recorded to set
	 */
	public void setDateRecorded(String newDate)
	{
		mDateRecorded = newDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		String[] dateData = mDateRecorded.split("\\.");
		String formattedDate = dateData[1] + "/" + dateData[2] + "/" + dateData[0];
		return mReps + "\t\t\t\t" + mWeight + " lbs" + "\t\t\t\t\t" + formattedDate;
	}
}