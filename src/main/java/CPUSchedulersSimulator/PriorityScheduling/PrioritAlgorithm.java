package CPUSchedulersSimulator.PriorityScheduling;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PrioritAlgorithm {
	private List<PriorityData> processList;
	
	private PriorityQueue<PriorityData> firstQueue; // kulhom we mtrbin 3la asas el AT
	private PriorityQueue<PriorityData> secondQueue; // men geh 3ndina
	
	private List<PriorityData> InDeadList;
	
	private int CurrentTime;
	
	public PrioritAlgorithm(List<PriorityData> list) {
		Comparator<PriorityData> ATDataComparator = (o1, o2) -> {
			if (o1.arivalTime < o2.arivalTime)
				return -1;
			if (o1.arivalTime > o2.arivalTime)
				return 1;
			return 0;
		};
		
		Comparator<PriorityData> PrioriyDataComparator = (o1, o2) -> {
			if (o1.priority < o2.priority)
				return -1;
			if (o1.priority > o2.priority)
				return 1;
			return 0;
		};
		
		this.processList = list;
		this.InDeadList = new ArrayList<PriorityData>();
		
		this.firstQueue = new PriorityQueue<PriorityData>(ATDataComparator);
		this.secondQueue = new PriorityQueue<PriorityData>(PrioriyDataComparator);
		
		for(PriorityData data : this.processList) {
			this.firstQueue.add(data);
		}
		
		this.CurrentTime = 0;
	}
	
	private void RefreshSecondQueue() {
		if(this.secondQueue.isEmpty() && !this.firstQueue.isEmpty()) {
			this.CurrentTime += this.firstQueue.peek().arivalTime;
		}
		
		while(!firstQueue.isEmpty() && this.CurrentTime >= firstQueue.peek().arivalTime) {
			PriorityData data = firstQueue.poll();
			this.secondQueue.add(data);
		}
	}
	
	private void StarvationFix() {
		List<PriorityData> list = new ArrayList<PriorityData>();
		while(!this.secondQueue.isEmpty()) {
			PriorityData tempData = this.secondQueue.poll();
			tempData.processedTimes++;
			if(tempData.processedTimes >= 5)
				tempData.priority--;
			list.add(tempData);
		}
		
		for(PriorityData tempData : list) {
			this.secondQueue.add(tempData);
		}
	}
	
	public List<PriorityData> Run() {
		
		int MaxNoOfProcess = this.firstQueue.size();
		int DoneProcesses = 0;
		
		while (DoneProcesses < MaxNoOfProcess) {
			RefreshSecondQueue();
			
			Time start = new Time(this.CurrentTime);
			
			PriorityData data = this.secondQueue.poll();
			
			data.waitingTime = this.CurrentTime - data.arivalTime;
			this.CurrentTime += data.burstTime;
			
			Time end = new Time(this.CurrentTime);
			data.saveDuration.AddDuration(start, end);
			
			this.InDeadList.add(data);
			DoneProcesses++;
			
			RefreshSecondQueue();
			StarvationFix();
		}
		
		
		return InDeadList;
	}
}
