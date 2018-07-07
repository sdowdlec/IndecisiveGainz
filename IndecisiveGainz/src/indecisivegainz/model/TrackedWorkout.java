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
	private String mDateRecorded;
	
	/**
	 * 
	 * @param id
	 * @param workoutName
	 * @param muscleGroup
	 * @param reps
	 * @param weight
	 * @param dateRecorded
	 */
	public TrackedWorkout(int id, String workoutName, String muscleGroup, int reps, double weight, String dateRecorded)
	{
		super(id, workoutName, muscleGroup);
		mReps = reps;
		mWeight = weight;
		mDateRecorded = dateRecorded;
	}
	
	public int getId()
	{
		return mId;
	}
	
	public void setId(int newId)
	{
		mId = newId;
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
	
	/**
	 * 
	 * @return
	 */
	public String getDateRecorded()
	{
		return mDateRecorded;
	}
	
	/**
	 * 
	 * @param newDate
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
		return "Reps: " + mReps + "\t\t\tWeight: " + mWeight + "\t\t\t" + formattedDate;
	}
	
	
}
