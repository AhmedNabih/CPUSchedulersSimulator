package CPUSchedulersSimulator.AGScheduling;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import GUI_Files.module_GUI.IProcess;
import GUI_Files.module_GUI.ProcessModule;

public class AGProcessData {
	public String processName;
	public double burstTime;
	public double arrivalTime;
	public int priority;
	public double quantum;
	public double AGFactor;
	public double waitingTime;
	public double turnaroundTime;
	public IProcess saveDuations;
	public double doneWork;
	public double lastWait;
	public List<Double> quantumHistory;
	
	public AGProcessData(String pName, double BT, double AT, int priority, double quantum, Color color) {
		this.processName = pName;
		this.burstTime = BT;
		this.arrivalTime = AT;
		this.priority = priority;
		this.quantum = quantum;
		this.AGFactor = this.priority + this.arrivalTime + this.burstTime;
		this.saveDuations = new ProcessModule(this.processName, color);
		this.waitingTime = 0.0;
		this.turnaroundTime = 0.0;
		this.doneWork = 0.0;
		this.quantumHistory = new ArrayList<Double>();
		quantumHistory.add(this.quantum);
		this.lastWait = this.arrivalTime;
	}
	
}
