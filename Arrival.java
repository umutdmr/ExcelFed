
public class Arrival implements Comparable<Arrival>
{
	private String type;
	private int id;
	private double time;
	private double duration;
	boolean isIn;
	public Arrival(String type, int id, double time, double duration)
	{
		this.duration = duration;
		this.id = id;
		this.time = time;
		this.type = type;
		this.isIn = false;

	}
	
	public int compareTo(Arrival arrival)
	{
		if(this.time > arrival.time)
		{
			return 1;
		}
		else if(this.time < arrival.time)
		{
			return -1;
		}
		else
		{
			if(this.id > arrival.getId())
			{
				return 1;
			}
			else if(this.id < arrival.getId())
			{
				return -1;
			}
		}
		return 0;
	}
	public String toString()
	{
		return type.concat(" " + String.valueOf(time));
	}
	public String getType()
	{
		return this.type;
	}
	public int getId()
	{
		return this.id;
	}
	public double getTime()
	{
		return this.time;
	}
	public void setTime(double time)
	{
		this.time = time;
	}
	public double getDuration()
	{
		return this.duration;
	}
	public void changeIsIn()
	{
		this.isIn = !this.isIn;
	}
	
 
}
