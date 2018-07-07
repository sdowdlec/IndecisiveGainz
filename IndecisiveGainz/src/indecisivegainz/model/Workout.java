package indecisivegainz.model;
/**
 * 
 * @author Sean Dowdle
 *
 */
public class Workout 
{
	protected int mId;
	protected String mWorkoutName;
	protected String mMuscleGroup;
	
	/**
	 * 
	 * @param workoutName
	 * @param muscleGroup
	 */
	public Workout(int id, String muscleGroup, String workoutName)
	{
		mId = id;
		mWorkoutName = workoutName;
		mMuscleGroup = muscleGroup;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getWorkoutName()
	{
		return mWorkoutName;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getMuscleGroup()
	{
		return mMuscleGroup;
	}
	
	/**
	 * 
	 * @param workoutName
	 */
	public void setWorkoutName(String workoutName)
	{
		mWorkoutName = workoutName;
	}
	
	/**
	 * 
	 * @param muscleGroup
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
