package CPUSchedulersSimulator.SRTF;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import GUI_Files.controller_GUI.MainApp_Controller;
import GUI_Files.module_GUI.ProcessModule;

public class SRTFmain {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new FileReader("SRTFinput.txt"));
		
		
		System.out.println("Please enter the number of Processes: ");
		int n = Integer.parseInt(br.readLine());
		SRTF[] Procces = new SRTF[n];
		for (int i = 0; i < Procces.length; i++) {
			Procces[i] = new SRTF();
		}

		int CS = 0;
		for (int i = 0; i < n; i++) {
			String[] scan = br.readLine().split(" ");
			
			System.out.print("enter process name: ");
			Procces[i].name = scan[0];
			System.out.println();
			
			System.out.println("enter process arrival time: ");
			Procces[i].arrivalTime = Integer.parseInt(scan[1]);
			System.out.println();
			
			System.out.println("enter process brust time: ");
			Procces[i].remainingTime = Integer.parseInt(scan[2]);
			System.out.println();
			
			scan = br.readLine().split(" ");
			int r = Integer.parseInt(scan[0]), g = Integer.parseInt(scan[1]), b = Integer.parseInt(scan[1]);
			Procces[i].color = new Color(r,g,b);
			
			Procces[i].saveDuration = new ProcessModule(Procces[i].name, Procces[i].color);
		}
		CS = Integer.parseInt(br.readLine());
		br.close();
		
		SRTF.ContextSwitching = CS;
		
		int total_time = 0;
		for (int i = 0; i < n; i++) {
			total_time += Procces[i].remainingTime;
		}
		int time_chart[] = new int[total_time + 30];
		for (int i = 0; i < total_time + 30; i++)
			time_chart[i] = i;
		while (true) {

			if (SRTF.TotalNumberOfProcces == n)
				break;
			SRTF.TotalNumberOfProcces = 0;

			SRTF.excute(n, Procces, time_chart, total_time);

		}
		
		List<ProcessModule> processData = new ArrayList<ProcessModule>();
		for(SRTF data : Procces) {
			ProcessModule tempData = new ProcessModule(data);
			processData.add(tempData);
		}
		MainApp_Controller.RunMainApp("CPU SJF", processData);
		
		System.out.println();
		SRTF.PrintAverage(Procces, n);
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////


	}
}
