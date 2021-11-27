
public class TrainingCoach implements Comparable<TrainingCoach>
{
	private double currentTime;
	
	private int id;
	public TrainingCoach(int id)
	{
		this.id = id;
		this.currentTime = 0;
		
	}
	
	public double getCurrentTime()
	{
		return this.currentTime;
	}
	public void setCurrentTime(double currentTime)
	{
		this.currentTime = currentTime;
	}
	public void increaseCurrentTime(double currentTime)
	{
		this.currentTime += currentTime;
	}
	
	public int getId()
	{
		return this.id;
	}
	public int compareTo(TrainingCoach trainingCoach)
	{
		if(this.getCurrentTime() > trainingCoach.getCurrentTime())
		{
			return 1;
		}
		else if(this.getCurrentTime() < trainingCoach.getCurrentTime())
		{
			return -1;
		}
		else
		{
			if(this.id > trainingCoach.getId())
			{
				return 1;
			}
			else if(this.id < trainingCoach.getId())
			{
				return -1;
			}
			return 0;
		}
		
	}
}
 