package mvc.model;
/**
 * Represents a type of workout the user can do.
 * Workouts have a name and muscle group they are
 * associated with.
 * @author Sean Dowdle
 *
 */
public class Workout 
{
	protected int mId;
	protected String mWorkoutName;
	protected String mMuscleGroup;
	
	/**
	 * Constructs a new Workout object based on the name of the workout and muscle group.
	 * @param workoutName The name of the workout
	 * @param muscleGroup The muscle group the workout is associated with
	 */
	public Workout(int id, String muscleGroup, String workoutName)
	{
		mId = id;
		mWorkoutName = workoutName;
		mMuscleGroup = muscleGroup;
	}
	
	/**
	 * Returns the name of the workout.
	 * @return The name of the workout
	 */
	public String getWorkoutName()
	{
		return mWorkoutName;
	}
	
	/**
	 * Returns the name of the muscle group the workout is associated with
	 * @return The muscle group the workout is associated with
	 */
	public String getMuscleGroup()
	{
		return mMuscleGroup;
	}
	
	/**
	 * Sets the name of the workout.
	 * @param workoutName The name of workout to set
	 */
	public void setWorkoutName(String workoutName)
	{
		mWorkoutName = workoutName;
	}
	
	/**
	 * Sets the muscle group of the workout.
	 * @param muscleGroup The muscle group to set
	 */
	public void setMuscleGroup(String muscleGroup)
	{
		mMuscleGroup = muscleGroup;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() 
	{
		return  mWorkoutName;
	}
}
