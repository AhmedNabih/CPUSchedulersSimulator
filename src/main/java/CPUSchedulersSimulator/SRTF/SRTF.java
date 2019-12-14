package CPUSchedulersSimulator.SRTF;

import java.awt.Color;
import java.sql.Time;

import GUI_Files.module_GUI.ProcessModule;

public class SRTF {
	public String name;
	public static int flag = 0;
	public int arrivalTime;
	public int remainingTime;
	public int waitingTime;
	public int TurnAroundTime;
	public Color color;
	public ProcessModule saveDuration;
	public static int startTime = 0;
	public static int TotalNumberOfProcces = 0;
	public static int c = 0;
	public static int miniArrivalTime;
	public static int oldC;
	public static int ContextSwitching;

	public static void excute(int n, SRTF Procces[], int time_chart[], int total_time) {
		c = n;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < n; i++) {
			/*
			 * If i'th process arrival time <= system time That process will be executed
			 * first
			 */
			if (Procces[i].arrivalTime <= startTime)// Condition to check if Process has arrived
			{
				if (Procces[i].remainingTime <= min && Procces[i].remainingTime != 0) {
					flag++;
					if (flag == 1)
						miniArrivalTime = Procces[i].arrivalTime;

					min = Procces[i].remainingTime;
					c = i;
					if (Procces[i].arrivalTime < miniArrivalTime)
						break;
				}
			}

		}
		if (c == n) {
			startTime++;
		} else {
			Time start = new Time(startTime);
			Procces[c].remainingTime--;
			for (int i = 0; i < n; i++) {
				if (Procces[i].arrivalTime <= startTime) {
					if (Procces[i].remainingTime != 0) {
						Procces[i].TurnAroundTime++;// If process has arrived and it has not already completed execution
													// its TT is incremented by 1
						if (c != oldC) // not the same process
						{
							Procces[i].TurnAroundTime += SRTF.ContextSwitching; // Context Switching
							Procces[i].waitingTime += SRTF.ContextSwitching;
						}

						if (i != c)// If the process has not been currently assigned the CPU and has arrived its WT
									// is incremented by 1
							Procces[i].waitingTime++;
					} else if (i == c)// This is a special case in which the process has been assigned CPU and has
										// completed its execution
						Procces[i].TurnAroundTime++;
				}
			}
			startTime++;
			Time end = new Time(startTime);
			Procces[c].saveDuration.AddDuration(start, end);
			if ((startTime - 1 - miniArrivalTime) != 0) {
				if ((c + 1) != time_chart[startTime - 2 - miniArrivalTime])
				// If the CPU has been assigned to a different Process we need to print the
				// current value of time and the name of
				// the new Process
				{
					System.out.print("--" + (startTime - 1) + "--P" + (c + 1));
				}
			} else// If the current time is 0 i.e the printing has just started we need to print
					// the name of the First selected Process
				System.out.print((startTime - 1) + "--P" + (c + 1));
			if ((startTime - 1 - miniArrivalTime) == total_time - 1)// All the process names have been printed now we
																	// have to print the time at which execution ends
			{
				System.out.print("--" + (startTime));
			}

			for (int i = 0; i < n; i++) {
				if (Procces[i].remainingTime <= 0)
					TotalNumberOfProcces++;
			}

		}
		oldC = c;
	}

	public static void PrintAverage(SRTF Procces[], int n) {
		float avgwt = 0, avgta = 0;
		for (int i = 0; i < n; i++) {
			avgwt += Procces[i].waitingTime;
			avgta += Procces[i].TurnAroundTime;
			System.out.println("Process ID" + "\t" + "arrivalTime" + "\t" + "TurnAroundTime" + "\t" + "waitingTime");
			System.out.println((i + 1) + "\t" + "\t" + Procces[i].arrivalTime + "\t" + "\t" + Procces[i].TurnAroundTime
					+ "\t" + "\t" + Procces[i].waitingTime);
		}
		System.out.println("\naverage tat is " + (float) (avgta / n));
		System.out.println("average wt is " + (float) (avgwt / n));
	}
}
