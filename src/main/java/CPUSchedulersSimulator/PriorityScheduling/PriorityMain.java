package CPUSchedulersSimulator.PriorityScheduling;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import GUI_Files.controller_GUI.MainApp_Controller;
import GUI_Files.module_GUI.ProcessModule;

public class PriorityMain {
	
	public static double GetMean(List<PriorityData> list, String key) {
		double sum = 0.0, count = list.size();
		for (PriorityData data : list) {
			if(key.equals("WT"))
				sum += data.waitingTime;
			else if (key.equals("TAT"))
				sum += data.burstTime + data.waitingTime;
		}
		return (double) (sum / count);
	}
	
	public static void main(String[] args) throws IOException {
		List<PriorityData> inputData = new ArrayList<PriorityData>();
		
		BufferedReader br = new BufferedReader(new FileReader("Priorityinput.txt"));

		int n;
		System.out.println("Enter the no of processes.");
		n = Integer.parseInt(br.readLine());
		
		for (int i = 0; i < n; i++) {
			String[] scan = br.readLine().split(" ");
			String[] scanColor = br.readLine().split(" ");
			int r = Integer.parseInt(scanColor[0]), g = Integer.parseInt(scanColor[1]), b = Integer.parseInt(scanColor[2]);
			
			PriorityData tempData = new PriorityData(scan[0], Integer.parseInt(scan[1]), Integer.parseInt(scan[2]), Integer.parseInt(scan[3]), new Color(r, g, b));
			inputData.add(tempData);
		}
		br.close();

		
		
		PrioritAlgorithm algo = new PrioritAlgorithm(inputData);
		List<PriorityData> processesData = algo.Run();
		
		Collections.sort(processesData,new Comparator<PriorityData>() {
			@Override
			public int compare(PriorityData o1, PriorityData o2) {
				if (o1.arivalTime < o2.arivalTime)
					return -1;
				if (o1.arivalTime > o2.arivalTime)
					return 1;
				return 0;
			}
			
		});
		
		List<ProcessModule> doneData = new ArrayList<ProcessModule>();
		for(PriorityData tempData : processesData) {
			System.out.print("Name: " + tempData.name + " Waiting Time: " + tempData.waitingTime);
			double ans = tempData.burstTime + tempData.waitingTime;
			System.out.println(" Turnaround Time: " + ans);
			ProcessModule temp = new ProcessModule(tempData);
			doneData.add(temp);
		}
		System.out.println("Avg Waiting Time: " + GetMean(processesData, "WT"));
		System.out.println("Avg Turnaround Time: " + GetMean(processesData, "TAT"));
		
		MainApp_Controller.RunMainApp("CPU", doneData);
	}
}
