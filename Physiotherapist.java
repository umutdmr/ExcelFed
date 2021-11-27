
public class Physiotherapist implements Comparable<Physiotherapist>
{
	double serviceTime;
	private int id;
	double currentTime;
	public Physiotherapist(int id, double serviceTime)
	{
		currentTime = 0;
		this.id = id;
		this.serviceTime = serviceTime;;
	}
	
	public int getId()
	{
		return this.id;
	}
	public int compareTo(Physiotherapist physiotherapist)
	{
		if(this.getCurrentTime() > physiotherapist.getCurrentTime())
		{
			return 1;
		}
		else if(this.getCurrentTime() < physiotherapist.getCurrentTime())
		{
			return -1;
		}
		else
		{
			if(this.id > physiotherapist.getId())
			{
				return 1;
			}
			else if(this.id < physiotherapist.getId())
			{
				return -1;
			}
			return 0;
		}
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
}
 