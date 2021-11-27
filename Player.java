public class Player implements Comparable<Player>
{
	private int skillLevel;
	double currentTrainingTime;
	double currentTime;
	int maxMassageServices;
	private int id;
	private double waitingForMassage;
	private double waitingForPhysiotherapy;
	private double timeWhenEnterTraining;
	private double timeWhenLeaveTraining;
	public Player(int id, int skillLevel)
	{
		this.timeWhenEnterTraining  = 0;
		this.id = id;
		this.skillLevel = skillLevel;
		this.currentTrainingTime = 0;
		this.currentTime = 0;
		this.maxMassageServices = 3;
		this.waitingForMassage = 0;
		this.waitingForPhysiotherapy = 0;
		this.timeWhenLeaveTraining = 0;
	}
	public void setTimeWhenEnterTraining(double amount)
	{
		this.timeWhenEnterTraining = amount;
	}
	public double getTimeWhenEnterTraining()
	{
		return this.timeWhenEnterTraining;
	}
	public void setTimeWhenLeaveTraining(double amount)
	{
		this.timeWhenLeaveTraining = amount;
	}
	public double getTimeWhenLeaveTraining()
	{
		return this.timeWhenLeaveTraining;
	}
	public void addTimeToWaitingForMassage(double amount)
	{
		this.waitingForMassage += amount;
	}
	public double getWaitingForPhysiotherapy()
	{
		return this.waitingForPhysiotherapy;
	}
	public double getWaitingForMassage()
	{
		return this.waitingForMassage;
	}
	public void addTimeToWaitingForPhysiotherapy(double amount)
	{
		this.waitingForPhysiotherapy += amount;
	}
	public void setToZero()
	{
		this.currentTrainingTime = 0;
	}
	public void setCurrentTrainingTime(double amount)
	{
		this.currentTrainingTime = amount;
	}
	public double getCurrentTrainingTime()
	{
		return this.currentTrainingTime;
	}
	public int GetSkillLevel()
	{
		return this.skillLevel;
	}
	
	// will be added to the massageQueue
	public int compareTo(Player player)  
	{
		if(this.skillLevel > player.skillLevel)
		{
			return -1;
		}
		else if(this.skillLevel < player.skillLevel)
		{
			return 1;
		}
		else
		{
			if(this.currentTime < player.getCurrentTime())
			{
				return -1;
			}
			else if(this.currentTime > player.getCurrentTime())
			{
				return 1;
			}
			else
			{
				if(this.id < player .getId())
				{
					return -1;
				}
				else if(this.id > player .getId())
				{
					return 1;
				}
			}
		}
		return 0;
	}
	public void setCurrentTime(double currentTime)
	{
		this.currentTime = currentTime;
	}
	public double getCurrentTime()
	{
		return this.currentTime;
	}
	public int getId()
	{
		return this.id;
	}
	public boolean equals(Player p)
	{
		if(this.id == p.getId())
		{
			return true;
		}
		return false;
	}

}
