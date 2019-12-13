package CPUSchedulersSimulator.SRTF;


import java.io.IOException;
import java.util.Scanner;

public class SRTFmain {
	public static  void Excution(SRTF Procces[],int startTime,int c,int n,int TotalNumberOFProcess)
{
		
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the number of Processes: ");
		int n = sc.nextInt();
		SRTF [] Procces=new SRTF[n];
		for(int i = 0; i < Procces.length ; i++)
		{
			Procces[i] = new SRTF();
		}
		
		for(int i=0;i<n;i++)
		{
			System.out.println ("enter process " + (i+1) + " arrival time:");
			Procces[i].arrivalTime=sc.nextInt();
			System.out.println ("enter process " + (i+1) + " brust time:");
			Procces[i].remainingTime = sc.nextInt();
		}
		 int total_time = 0;
	     for(int i = 0; i < n; i++)
	     {
	      total_time += Procces[i].remainingTime;
	     }
	     int time_chart[] = new int[total_time+30];
	     for(int i=0;i<total_time+30;i++)
	    	 time_chart[i]=i;
		while(true)
		{
			
			if(SRTF.TotalNumberOfProcces==n)
				break;
			SRTF.TotalNumberOfProcces=0;
		
			SRTF.excute(n, Procces,time_chart,total_time);
			
		}
	      System.out.println();
	      	SRTF.PrintAverage(Procces, n);
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////
	     
		sc.close();
	
}
		}
	

	
	


