package indecisivegainz.model;
/**
 * 
 * @author Sean Dowdle
 *
 */
public class TrackedWorkout extends Workout 
{
	private int mReps;
	private double mWeight;
	
	/**
	 * 
	 * @param workoutName
	 * @param muscleGroup
	 * @param reps
	 * @param weight
	 */
	public TrackedWorkout(String workoutName, String muscleGroup, int reps, double weight)
	{
		super(workoutName, muscleGroup);
		mReps = reps;
		mWeight = weight;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getReps()
	{
		return mReps;
	}
	
	/**
	 * 
	 * @return
	 */
	public double getWeight()
	{
		return mWeight;
	}
	
	/**
	 * 
	 * @param reps
	 */
	public void setReps(int reps)
	{
		mReps = reps;
	}
	
	/**
	 * 
	 * @param weight
	 */
	public void setWeight(double weight)
	{
		mWeight = weight;
	}
}
