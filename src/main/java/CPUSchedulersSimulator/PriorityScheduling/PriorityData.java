package CPUSchedulersSimulator.PriorityScheduling;

import java.awt.Color;

import GUI_Files.module_GUI.ProcessModule;

public class PriorityData {
	public String name;
	public int priority;
	public int waitingTime;
	public int burstTime;
	public int arivalTime;
	public Color color;
	public int processedTimes;
	
	public int turnaroundTime;
	public ProcessModule saveDuration;
	
	public PriorityData(String name, int arivalTime,int burstTime, int priority, Color color) {
		this.name = name;
		this.arivalTime = arivalTime;
		this.burstTime = burstTime;
		this.priority = priority;
		this.color = color;
		this.saveDuration = new ProcessModule(this.name,this.color);
		this.processedTimes = 0;
	}
}
