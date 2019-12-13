package CPUSchedulersSimulator.SJF;


import java.io.IOException;
import java.util.Scanner;

public class SJFmain {
	public static  void Excution(newSJF Procces[],int startTime,int c,int n,int TotalNumberOFProcess)
{
		
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		Scanner sc = new Scanner(System.in);
		 System.out.println ("enter no of process:");
		int n = sc.nextInt();
		newSJF [] Procces=new newSJF[n];
		for(int i = 0; i < Procces.length ; i++)
		{
			Procces[i] = new newSJF();
		}

		float avgwt=0, avgta=0;
		for(int i=0;i<n;i++)
		{
			System.out.println ("enter process " + (i+1) + " arrival time:");
			Procces[i].arrivalTime=sc.nextInt();
			System.out.println ("enter process " + (i+1) + " brust time:");
			Procces[i].burstTime = sc.nextInt();
			Procces[i].pid = i+1;
			Procces[i].f = 0;
		}
		
		while(true)
		{
			if (newSJF.TotalNumberOFProcess == n) // total no of process = completed process loop will be terminated
				break;
			newSJF.excute(Procces,n);
			}
		
		System.out.println("\npid  arrival brust  complete turn waiting");
		newSJF.Print(Procces, n); 
	     
		sc.close();
	
}
		}
	

	
	


