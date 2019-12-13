package CPUSchedulersSimulator.SJF;



public class newSJF {
	int pid;
	int arrivalTime ;
	int burstTime ; 
	int completeTime ; 
	int aroundTime ; 
	int waitingTime ;
	static int startTime=0;
	static int TotalNumberOFProcess=0;
	static int avgwt=0;
	static int avgta=0;
	int f;  // f means it is flag it checks process is completed or not	
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
			startTime+=Procces[c].burstTime;
			Procces[c].aroundTime=Procces[c].completeTime-Procces[c].arrivalTime;
			Procces[c].waitingTime=Procces[c].aroundTime-Procces[c].burstTime;
			Procces[c].f=1;
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
