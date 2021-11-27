import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Queue;
import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.ArrayList;
public class project2main 
{
 
	public static void main(String[] args) throws FileNotFoundException
	{
		//fifo, if two player added at the same time, the one with lower id added first.
		Queue<Player> trainingQueue = new LinkedList<Player>();
		//prioritized with currentTrainingTime.
		Queue<Player> physiotherapyQueue = new LinkedList<Player>();
		//prioritized with skillLevel.
		PriorityQueue<Player> massageQueue = new PriorityQueue<Player>();
		PriorityQueue<Arrival> arrivals = new PriorityQueue<Arrival>();
		ArrayList<Player> players = new ArrayList<Player>();
		PriorityQueue<Physiotherapist> physiotherapists = new PriorityQueue<Physiotherapist>();
		PriorityQueue<TrainingCoach> trainers = new PriorityQueue<TrainingCoach>();
		PriorityQueue<Masseur> masseurs = new PriorityQueue<Masseur>();
		Scanner in = new Scanner(new File (args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		//reading the data:
		int playerNum = in.nextInt();
		in.nextLine();
		
		//will assigned to players
		for(int i = 0; i < playerNum; i++)
		{
			int id = in.nextInt();
			int skillLevel = in.nextInt();
			players.add(new Player(id, skillLevel));
			
			
			
		}
		int arrivalNum = in.nextInt();
		in.nextLine();
		//arrivals
		for(int i = 0; i < arrivalNum; i++)
		{
			String type = in.next();
			int id = in.nextInt();
			double time = in.nextDouble();
			double duration = in.nextDouble();
			arrivals.add(new Arrival(type, id, time, duration));
			
			
			
		}
		
		int physiotherapistNum = in.nextInt();
		
		for(int j = 0; j < physiotherapistNum; j++)
		{
			double serviceTime = in.nextDouble();
			
			physiotherapists.add(new Physiotherapist(j, serviceTime));
			
		}
		int trainingCoachs = in.nextInt();
		int masseurNum = in.nextInt();
		for(int i = 0; i < trainingCoachs; i++)
		{
			trainers.add(new TrainingCoach(i));
		}
		for(int i = 0; i < masseurNum; i++)
		{
			masseurs.add(new Masseur(i));
		}
		
		//for the output:
		int maxLengthOfTheTrainingQueue = 0;
		int maxLengthOfThePhysiotherapyQueue = 0;
		int maxLengthOfTheMassageQueue = 0;
		double avrageWaitingTimeTrainingQueue = 0;
		double averageWaitingTimePhysiotherapyQueue = 0;
		double averageWaitingTimeMassageQueue = 0;
		double averageTrainingTime = 0;
		int totalTrainingNum = 0;
		double averagePhysiotherapyTime = 0;
		int totalPhysiotherapyNum = 0;
		double averageMassageTime = 0;
		int totalMassageNum = 0;
		double averageTurnaroundTime = 0;
		int idOfTheMostPyhsiotherapyPlayer = 0;
		double waitingTimeOfTheMostPyhsiotherapyPlayer = 0;
		int idOfTheLeastMassagePlayer = 0;
		double waitingTimeOfTheLeastMassagePlayer= 0;
		int totalInvalidAttempts = 0;
		int totalCanceledAttempts = 0;
		double totalTimePassed = 0;
		
		
		double rightTime = 0;
		while(arrivalNum > 0)
		{
			
			Arrival a = arrivals.poll();
			Player p = players.get(a.getId());
			double time = a.getTime();
			rightTime = time;
			
			
			if(a.getType().startsWith("m"))
			{
				
				if(p.maxMassageServices == 0)
				{
					totalInvalidAttempts++;
					arrivalNum--;
					continue;
				}
				if(p.getCurrentTime() > rightTime)
				{
					totalCanceledAttempts++;
					rightTime = p.getCurrentTime();
					arrivalNum--;
					continue;
				}
				
				double duration = a.getDuration();
				PriorityQueue<Masseur> masseursTmp = new PriorityQueue<Masseur>();
				Masseur m = null;
				while(masseurs.size() != 0)
				{
					m = masseurs.poll();
					
					if(m.getCurrentTime() <= time)
					{
						break;
					}
					masseursTmp.add(m);
					
					
					m = null;
				}
				while(masseursTmp.size() != 0)
				{
					masseurs.add(masseursTmp.poll());
				}
				
				if(m != null)
				{
					
					if(massageQueue.size() == 0)
					{
						
						m.setCurrentTime(time + duration);
						p.setCurrentTime(m.getCurrentTime());
						averageMassageTime += duration;
						totalMassageNum++;
						p.maxMassageServices--;
						masseurs.add(m);
						
						
					}
					else
					{
						
						if(!a.isIn)
						{
							massageQueue.add(p);
						}
						Player tmp = massageQueue.poll();
						if(tmp.equals(p))
						{
							
							m.setCurrentTime(time + duration);
							p.setCurrentTime(m.getCurrentTime());
							averageMassageTime += duration;
							totalMassageNum++;
							p.maxMassageServices--;
							masseurs.add(m);
							
						}
						else
						{
							
							Arrival tmpArrival = null;
							PriorityQueue<Arrival> tmpArrivals = new PriorityQueue<Arrival>();
							
							while(tmpArrival == null)
							{
								
								Arrival b = arrivals.poll();
								if(b.getId() == tmp.getId());
								{
									
									tmpArrival = b;
									time = tmpArrival.getTime();
									duration = tmpArrival.getDuration();
									while(tmpArrivals.size() != 0)
									{
										arrivals.add(tmpArrivals.poll());
									}
									m.setCurrentTime(time + duration);
									tmp.setCurrentTime(m.getCurrentTime());
									tmp.maxMassageServices--;
									masseurs.add(m);
									
									time = masseurs.peek().getCurrentTime();
									a.setTime(time);
									//p.addTimeToWaitingForMassage(time - p.getCurrentTime());
									if(!a.isIn)
									{
										a.changeIsIn();
									}
									arrivals.add(a);
								}
								tmpArrivals.add(b);
								
								
							}
							
							
						}
					}
				}
				else
				{
					
					
					time = masseurs.peek().getCurrentTime();
					averageWaitingTimeMassageQueue += (time - a.getTime());
					p.addTimeToWaitingForMassage(time - p.getCurrentTime());
					a.setTime(time);
					massageQueue.add(p);
					a.changeIsIn();
					arrivals.add(a);
					arrivalNum++;
					
					if(maxLengthOfTheMassageQueue < massageQueue.size())
					{
						maxLengthOfTheMassageQueue = massageQueue.size();
					}
				}
			}
			else
			{
				if(p.getCurrentTime() > rightTime)
				{
					totalCanceledAttempts++;
					rightTime = p.getCurrentTime();
					arrivalNum--;
					continue;
				}
				
				Player forPhysiotherapy = null;
				double duration = a.getDuration();
				
				TrainingCoach t = null;
				PriorityQueue<TrainingCoach> trainersTmp = new PriorityQueue<TrainingCoach>();
				while(trainers.size() != 0)
				{
					t = trainers.poll();
					if(t.getCurrentTime() <= time)
					{
						break;
					}
					trainersTmp.add(t);
					t = null;
				}
				while(trainersTmp.size() != 0)
				{
					trainers.add(trainersTmp.poll());
				}
				if(t != null)
				{
					
					if(trainingQueue.size() == 0)
					{
						
						t.setCurrentTime(time + duration);
						p.setCurrentTime(t.getCurrentTime());
						p.setTimeWhenEnterTraining(time);
						p.setTimeWhenLeaveTraining(duration + time);
						trainers.add(t);
						p.setCurrentTrainingTime(duration);
						averageTrainingTime += duration;
						totalTrainingNum++;
						forPhysiotherapy = p;
						
					}
					/*else
					{
						
						if(!a.isIn)
						{
							massageQueue.add(p);
						}
						Player tmp = massageQueue.poll();
						if(tmp.equals(p))
						{
							
							m.setCurrentTime(time + duration);
							p.setCurrentTime(m.getCurrentTime());
							averageMassageTime += duration;
							totalMassageNum++;
							p.maxMassageServices--;
							masseurs.add(m);
							
						}
						else
						{
							
							Arrival tmpArrival = null;
							PriorityQueue<Arrival> tmpArrivals = new PriorityQueue<Arrival>();
							
							while(tmpArrival == null)
							{
								
								Arrival b = arrivals.poll();
								if(b.getId() == tmp.getId());
								{
									
									tmpArrival = b;
									time = tmpArrival.getTime();
									duration = tmpArrival.getDuration();
									while(tmpArrivals.size() != 0)
									{
										arrivals.add(tmpArrivals.poll());
									}
									m.setCurrentTime(time + duration);
									tmp.setCurrentTime(m.getCurrentTime());
									tmp.maxMassageServices--;
									masseurs.add(m);
									
									time = masseurs.peek().getCurrentTime();
									a.setTime(time);
									//p.addTimeToWaitingForMassage(time - p.getCurrentTime());
									if(!a.isIn)
									{
										a.changeIsIn();
									}
									arrivals.add(a);
								}
								tmpArrivals.add(b);
								
								
							}
							
							
						}
					}*/
					else
					{


						trainingQueue.poll();
						t.setCurrentTime(time + duration);
						p.setCurrentTime(t.getCurrentTime());
						p.setTimeWhenEnterTraining(time);
						p.setTimeWhenLeaveTraining(duration + time);
						trainers.add(t);
						p.setCurrentTrainingTime(duration);
						averageTrainingTime += duration;
						totalTrainingNum++;
						forPhysiotherapy = p;
					}
				}
				else
				{
					
					time = trainers.peek().getCurrentTime();
					avrageWaitingTimeTrainingQueue += (time - a.getTime());
					a.setTime(time);
					trainingQueue.add(p);
					a.changeIsIn();
					arrivals.add(a);
					arrivalNum++;
					if(maxLengthOfTheTrainingQueue < trainingQueue.size())
					{
						maxLengthOfTheTrainingQueue = trainingQueue.size();
					}
					
				}
				if(forPhysiotherapy != null)
				{
					
					Physiotherapist phys = null;
					for(int i = 0; i < physiotherapists.size(); i++)
					{
						phys = physiotherapists.poll();
						if(phys.getCurrentTime() <= p.getCurrentTime())
						{
							break;
						}
						physiotherapists.add(phys);
						phys = null;
					}
					if(phys != null)
					{
						if(physiotherapyQueue.size() == 0)
						{
							
							phys.setCurrentTime(time + duration + phys.serviceTime);
							averageTurnaroundTime += phys.getCurrentTime() - p.getTimeWhenEnterTraining();
							p.setCurrentTime(phys.getCurrentTime());
							averagePhysiotherapyTime += phys.serviceTime;
							totalPhysiotherapyNum++;
							p.setCurrentTrainingTime(0);
							physiotherapists.add(phys);
							averageWaitingTimePhysiotherapyQueue += (time + duration) - p.getTimeWhenLeaveTraining();
							p.addTimeToWaitingForPhysiotherapy((time + duration) - p.getTimeWhenLeaveTraining());
							
						}
						else
						{
							Player tmp = physiotherapyQueue.poll();
							if(p.currentTrainingTime > tmp.getCurrentTrainingTime())
							{
								physiotherapyQueue.add(p);
								physiotherapyQueue.add(tmp);
							}
							else if(p.currentTrainingTime < tmp.getCurrentTrainingTime())
							{
								physiotherapyQueue.add(tmp);
								physiotherapyQueue.add(p);
								
							}
							else
							{
								if(p.getCurrentTime() < tmp.getCurrentTime())
								{
									physiotherapyQueue.add(p);
									physiotherapyQueue.add(tmp);
								}
								else if(p.getCurrentTime() > tmp.getCurrentTime())
								{
									physiotherapyQueue.add(tmp);
									physiotherapyQueue.add(p);
									
								}
								else
								{
									if(p.getId() < tmp.getId())
									{
										physiotherapyQueue.add(p);
										physiotherapyQueue.add(tmp);
									}
									else
									{
										physiotherapyQueue.add(tmp);
										physiotherapyQueue.add(p);
										
									}
								}
							}
							Player toPhys = physiotherapyQueue.poll();
							phys.setCurrentTime(time + duration + phys.serviceTime);
							toPhys.setCurrentTime(phys.getCurrentTime());
							toPhys.setCurrentTrainingTime(0);
							physiotherapists.add(phys);
							averagePhysiotherapyTime += phys.serviceTime;
							totalPhysiotherapyNum++;
							averageTurnaroundTime += phys.getCurrentTime() - toPhys.getTimeWhenEnterTraining();
							averageWaitingTimePhysiotherapyQueue += (time + duration) - toPhys.getTimeWhenLeaveTraining();
							toPhys.addTimeToWaitingForPhysiotherapy((time + duration) - toPhys.getTimeWhenLeaveTraining());
						}
					}
					else
					{
						physiotherapyQueue.add(p);
						if(maxLengthOfThePhysiotherapyQueue < physiotherapyQueue.size())
						{
							maxLengthOfThePhysiotherapyQueue = physiotherapyQueue.size();
						}
					}
				}
			}
			arrivalNum--;
			if(arrivalNum == 0)
			{
				rightTime += a.getDuration();
			}
			
		}
		
		totalTimePassed = rightTime;
		averageMassageTime /= totalMassageNum;
		averageWaitingTimeMassageQueue /= totalMassageNum;
		averageTrainingTime /= totalTrainingNum;
		avrageWaitingTimeTrainingQueue /= totalTrainingNum;
		averageTurnaroundTime /= totalTrainingNum;
		averagePhysiotherapyTime /= totalPhysiotherapyNum;
		averageWaitingTimePhysiotherapyQueue /= totalPhysiotherapyNum;
		double waitingForP = 0;
		double waitingForM = 0;
		int idForP = 0;
		int idForM = 0;
		boolean ifThree = false;
		for(int i = 0; i < players.size(); i++)
		{
			
			if(players.get(i).getWaitingForPhysiotherapy() > waitingForP)
			{
				waitingForP = players.get(i).getWaitingForPhysiotherapy();
				idForP = players.get(i).getId();
			}
			if(players.get(i).getWaitingForMassage() < waitingForM)
			{
				
				waitingForM = players.get(i).getWaitingForMassage();
				idForM = players.get(i).getId();
				if(players.get(i).maxMassageServices == 0)
				{
					ifThree = true;
				}
			}
		}
		if(!ifThree)
		{
			idForM = -1;
			waitingForM = -1;
		}
		
		
		//print the output
		out.println(maxLengthOfTheTrainingQueue);
		out.println(maxLengthOfThePhysiotherapyQueue);
		out.println(maxLengthOfTheMassageQueue);
		out.println(String. format("%.3f", avrageWaitingTimeTrainingQueue));
		out.println(String. format("%.3f", averageWaitingTimePhysiotherapyQueue));
		out.println(String. format("%.3f", averageWaitingTimeMassageQueue));
		out.println(String. format("%.3f", averageTrainingTime));
		out.println(String. format("%.3f", averagePhysiotherapyTime));
		out.println(String. format("%.3f", averageMassageTime));
		out.println(String. format("%.3f", averageTurnaroundTime));
		out.println(String.valueOf(idOfTheMostPyhsiotherapyPlayer) + " " + String. format("%.3f",waitingTimeOfTheMostPyhsiotherapyPlayer));
		out.println(String.valueOf(idOfTheLeastMassagePlayer) + " " + String. format("%.3f",waitingTimeOfTheLeastMassagePlayer));
		out.println(totalInvalidAttempts);
		out.println(totalCanceledAttempts);
		out.println(String. format("%.3f",totalTimePassed));

	}
}
