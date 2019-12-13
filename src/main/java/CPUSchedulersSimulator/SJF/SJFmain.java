
import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static  void Excution(SJF Procces[],int startTime,int c,int n,int TotalNumberOFProcess)
{
		
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		Scanner sc = new Scanner(System.in);
		 System.out.println ("enter no of process:");
		int n = sc.nextInt();
		SJF [] Procces=new SJF[n];
		for(int i = 0; i < Procces.length ; i++)
		{
			Procces[i] = new SJF();
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
			if (SJF.TotalNumberOFProcess == n) // total no of process = completed process loop will be terminated
				break;
			SJF.excute(Procces,n);
			}
		
		System.out.println("\npid  arrival brust  complete turn waiting");
		SJF.Print(Procces, n); 
	     
		sc.close();
	
}
		}
	

	
	


