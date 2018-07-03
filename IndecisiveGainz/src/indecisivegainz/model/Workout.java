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
	public Workout(int id, String workoutName, String muscleGroup)
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
}
