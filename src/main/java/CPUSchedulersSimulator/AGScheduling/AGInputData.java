package CPUSchedulersSimulator.AGScheduling;

import java.awt.Color;

public class AGInputData {
	public String processName;
	public double burstTime;
	public double arrivalTime;
	public int priority;
	public double quantum;
	public Color color;
	
	public AGInputData(String pName, double BT, double AT, int priority, double quantum, Color color) {
		this.processName = pName;
		this.burstTime = BT;
		this.arrivalTime = AT;
		this.priority = priority;
		this.quantum = quantum;
		this.color = color;
	}
}
