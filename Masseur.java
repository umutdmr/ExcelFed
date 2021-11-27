
public class Masseur implements Comparable<Masseur>
{
	private double currentTime;
	private int id;
	public Masseur(int id)
	{
		this.currentTime = 0;
		
		this.id = id;
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
	
	public int compareTo(Masseur masseur)
	{
		if(this.getCurrentTime() > masseur.getCurrentTime())
		{
			return 1;
		}
		else if(this.getCurrentTime() < masseur.getCurrentTime())
		{
			return -1;
		}
		else
		{
			if(this.id > masseur.getId())
			{
				return 1;
			}
			else if(this.id < masseur.getId())
			{
				return -1;
			}
			return 0;
		}
		
	}
}
 