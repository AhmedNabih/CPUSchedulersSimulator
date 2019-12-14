package CPUSchedulersSimulator.SJF;

import java.awt.Color;
import java.sql.Time;

import GUI_Files.module_GUI.IProcess;

public class newSJF {
	public String pid;
	public Color color;
	public int arrivalTime ;
	public int burstTime ; 
	public int completeTime ; 
	public int aroundTime ; 
	public int waitingTime ;
	public IProcess saveDuration;
	public static int startTime=0;
	public static int TotalNumberOFProcess=0;
	public static int avgwt=0;
	public static int avgta=0;
	public int f;  // f means it is flag it checks process is completed or not	
	public static void excute(newSJF Procces[],int n)
	{
		int c=n, min=Integer.MAX_VALUE;
		for (int i=0; i<n; i++)
		{
			/*
			 * If i'th process arrival time <= system time and its flag=0 and burst<min 
			 * That process will be executed first 
			 */ 
			if ((Procces[i].arrivalTime <= startTime) && (Procces[i].f == 0) && (Procces[i].burstTime<min))
			{
				min=Procces[i].burstTime;
				c=i;
			}
		}
		
		/* lw mafesh wala procces el arrival time bta3ha a2al aw besawy el zero mn el if aly fo2 .. hanzwd el start time b 1 */
		if (c==n) 
			startTime++;
		else
		{
			Procces[c].completeTime=startTime+Procces[c].burstTime;
			Procces[c].aroundTime=Procces[c].completeTime-Procces[c].arrivalTime;
			Procces[c].waitingTime=Procces[c].aroundTime-Procces[c].burstTime;
			Procces[c].f=1;
			Time start = new Time(startTime);
			Time end = new Time(Procces[c].completeTime);
			Procces[c].saveDuration.AddDuration(start, end);
			startTime += Procces[c].burstTime;
			TotalNumberOFProcess++;
			
		}		
	}
	public static void Print(newSJF Procces[],int n)
	{
		for(int i=0;i<n;i++)
		{
			avgwt+= Procces[i].waitingTime;
			avgta+= Procces[i].aroundTime;
			System.out.println(Procces[i].pid+"\t"+Procces[i].arrivalTime+"\t"+Procces[i].burstTime+"\t"+Procces[i].completeTime+"\t"+Procces[i].aroundTime+"\t"+Procces[i].waitingTime);
		}
		System.out.println ("\naverage tat is "+ (float)(avgta/n));
		System.out.println ("average wt is "+ (float)(avgwt/n));
	}
	}
