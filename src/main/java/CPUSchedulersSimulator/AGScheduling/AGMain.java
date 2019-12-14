package CPUSchedulersSimulator.AGScheduling;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import GUI_Files.controller_GUI.MainApp_Controller;
import GUI_Files.module_GUI.ProcessModule;

public class AGMain {
	public static double GetMean(List<AGProcessData> list, String key) {
		double sum = 0.0, count = list.size();
		for (AGProcessData data : list) {
			if(key.equals("WT"))
				sum += data.waitingTime;
			else if (key.equals("TAT"))
				sum += data.burstTime + data.waitingTime;
		}
		return (double) (sum / count);
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("AGinput.txt"));

		Comparator<AGInputData> AGInputDataComparator = (o1, o2) -> {
			if (o1.arrivalTime < o2.arrivalTime)
				return -1;
			if (o1.arrivalTime > o2.arrivalTime)
				return 1;
			return 0;
		};

		PriorityQueue<AGInputData> AGInputQueue = new PriorityQueue<AGInputData>(AGInputDataComparator);

		int n;
		String processName;
		double burstTime;
		double arrivalTime;
		int priority;
		double quantum;
		System.out.println("Enter the no of processes.");
		n = Integer.parseInt(br.readLine());
		AGInputData[] inputData = new AGInputData[n];
		for (int i = 0; i < n; i++) {
			String input = br.readLine();
			String[] tpData = input.split(" ");
			
			processName = tpData[0];
			burstTime = Double.parseDouble(tpData[1]);
			arrivalTime = Double.parseDouble(tpData[2]);
			priority = Integer.parseInt(tpData[3]);
			quantum = Double.parseDouble(tpData[4]);
			
			input = br.readLine();
			tpData = input.split(" ");
			
			int r = Integer.parseInt(tpData[0]), g = Integer.parseInt(tpData[1]), b = Integer.parseInt(tpData[2]);
			
			inputData[i] = new AGInputData(processName, burstTime, arrivalTime, priority, quantum, new Color(r, g, b));
			
			AGInputQueue.add(inputData[i]);
		}
		double CS = Double.parseDouble(br.readLine());
		br.close();

		
		
		AGAlgorithm algo = new AGAlgorithm(inputData, CS);
		List<AGProcessData> processesData = algo.Run();
		
		Collections.sort(processesData,new Comparator<AGProcessData>() {

			@Override
			public int compare(AGProcessData o1, AGProcessData o2) {
				if (o1.arrivalTime < o2.arrivalTime)
					return -1;
				if (o1.arrivalTime > o2.arrivalTime)
					return 1;
				return 0;
			}
			
		});
		
		List<ProcessModule> doneData = new ArrayList<ProcessModule>();
		for(AGProcessData tempData : processesData) {
			System.out.print("Name: " + tempData.processName + " Waiting Time: " + tempData.waitingTime);
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
