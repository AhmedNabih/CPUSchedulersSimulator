package CPUSchedulersSimulator.SJF;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import GUI_Files.controller_GUI.MainApp_Controller;
import GUI_Files.module_GUI.ProcessModule;

public class SJFmain {

	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new FileReader("SJFinput.txt"));
		System.out.println("enter no of process:");
		int n = Integer.parseInt( br.readLine() );
		newSJF[] Procces = new newSJF[n];
		for (int i = 0; i < Procces.length; i++) {
			Procces[i] = new newSJF();
		}

		for (int i = 0; i < n; i++) {
			String[] scan = br.readLine().split(" ");
			
			System.out.print("enter process name: ");
			Procces[i].pid = scan[0];
			System.out.println();
			
			System.out.println("enter process arrival time: ");
			Procces[i].arrivalTime = Integer.parseInt(scan[1]);
			System.out.println();
			
			System.out.println("enter process brust time: ");
			Procces[i].burstTime = Integer.parseInt(scan[2]);
			System.out.println();
			
			scan = br.readLine().split(" ");
			int r = Integer.parseInt(scan[0]), g = Integer.parseInt(scan[1]), b = Integer.parseInt(scan[1]);
			Procces[i].color = new Color(r,g,b);
			
			Procces[i].saveDuration = new ProcessModule(Procces[i].pid, Procces[i].color);
			
			Procces[i].f = 0;
		}

		br.close();
		
		while (true) {
			if (newSJF.TotalNumberOFProcess == n) // total no of process = completed process loop will be terminated
				break;
			newSJF.excute(Procces, n);
		}
		
		List<ProcessModule> processData = new ArrayList<ProcessModule>();
		for(newSJF data : Procces) {
			ProcessModule tempData = new ProcessModule(data);
			processData.add(tempData);
		}
		MainApp_Controller.RunMainApp("CPU SJF", processData);
		
		System.out.println("\npid  arrival brust  complete turn waiting");
		newSJF.Print(Procces, n);


	}
}
