package indecisivegainz.model;
/**
 * 
 * @author Sean Dowdle
 *
 */
public class Workout 
{
	protected String mWorkoutName;
	protected String mMuscleGroup;
	
	/**
	 * 
	 * @param workoutName
	 * @param muscleGroup
	 */
	public Workout(String workoutName, String muscleGroup)
	{
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
