package CPUSchedulersSimulator.AGScheduling;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class AGAlgorithm {
	private PriorityQueue<AGProcessData> AGQueueInput;
	private PriorityQueue<AGProcessData> AGFactorQueue;
	private Queue<AGProcessData> AGQueue;
	private List<AGProcessData> processData;
	private List<AGProcessData> InReadyList;
	private List<AGProcessData> InDeadList;

	private double CurrentTime;
	private double contextSwitchingHeadacheTime;
	private boolean priority;

	private double GetMean(List<AGProcessData> list) {
		double sum = 0.0, count = list.size();
		for (AGProcessData data : list) {
			sum += data.quantum;
		}
		return (double) (sum / count);
	}

	public AGAlgorithm(AGInputData[] inputData, double contextSwitchingHeadacheTime) {
		this.processData = new ArrayList<AGProcessData>();
		this.InReadyList = new ArrayList<AGProcessData>();
		this.InDeadList = new ArrayList<AGProcessData>();
		this.contextSwitchingHeadacheTime = contextSwitchingHeadacheTime;
		this.priority = false;

		Comparator<AGProcessData> AGInputDataComparator = (o1, o2) -> {
			if (o1.arrivalTime < o2.arrivalTime)
				return -1;
			if (o1.arrivalTime > o2.arrivalTime)
				return 1;
			return 0;
		};
		
		Comparator<AGProcessData> AGFactorComparator = (o1, o2) -> {
			if (o1.AGFactor < o2.AGFactor)
				return -1;
			if (o1.AGFactor > o2.AGFactor)
				return 1;
			return 0;
		};

		for (AGInputData data : inputData) {
			AGProcessData pData = new AGProcessData(data.processName, data.burstTime, data.arrivalTime, data.priority,
					data.quantum, data.color);
			this.processData.add(pData);
		}

		this.AGQueue = new LinkedList<AGProcessData>();
		this.AGFactorQueue = new PriorityQueue<AGProcessData>(AGFactorComparator);
		this.AGQueueInput = new PriorityQueue<AGProcessData>(AGInputDataComparator);
		for (AGProcessData data : this.processData) {
			this.AGQueueInput.add(data);
		}
	}

	private void CalcWaitingTime(AGProcessData process) {
		process.waitingTime += CurrentTime - process.lastWait;
	}
	
	public List<AGProcessData> Run() {
		int noOfProcesses = AGQueueInput.size();
		int doneProcesses = 0;

		while (doneProcesses != noOfProcesses) {

			RefreshListFirst();

			AGProcessData pdata;
			if(this.priority) {
				pdata = AGFactorQueue.poll();
				AGQueue.remove(pdata);
				this.priority = false;
			}
			else {
				pdata = AGQueue.poll();
				AGFactorQueue.remove(pdata);
			}
			System.out.println("Name: " + pdata.processName + " CurrentTime: " + CurrentTime);
		
			CalcWaitingTime(pdata);

			boolean pComplete = nonPreemptive(pdata);

			if (pComplete) {
				doneProcesses++;
			} else {
				RefreshList();
				if (preemptive(pdata))
					doneProcesses++;
			}

			System.out.print("Quantum: ");
			for (AGProcessData temp : InReadyList) {
				System.out.print(temp.processName + ": " + temp.quantum + " ");
			}
			System.out.println("\n");
		}
		return InDeadList;

	}

	private void RefreshList() {
		while (!AGQueueInput.isEmpty() && AGQueueInput.peek().arrivalTime <= CurrentTime) {
			AGProcessData tpData = AGQueueInput.poll();
			InReadyList.add(tpData);
			AGQueue.add(tpData);
			AGFactorQueue.add(tpData);
		}
	}
	
	private void RefreshListFirst() {
		if(!AGQueueInput.isEmpty() && AGQueue.isEmpty()) {
			this.CurrentTime = this.AGQueueInput.peek().arrivalTime;
		}
		while (!AGQueueInput.isEmpty() && AGQueueInput.peek().arrivalTime <= CurrentTime) {
			AGProcessData tpData = AGQueueInput.poll();
			InReadyList.add(tpData);
			AGQueue.add(tpData);
			AGFactorQueue.add(tpData);
		}
	}

	private boolean GetHigherPriority(AGProcessData process) {
		for (AGProcessData prcData : InReadyList) {
			if (process.AGFactor > prcData.AGFactor) {
				return false;
			}
		}
		return true;
	}

	private boolean ExcuteProcess(AGProcessData process) {
		if (Math.ceil(process.quantum / 2.0) >= process.burstTime - process.doneWork) {
			CurrentTime += process.burstTime - process.doneWork;
			process.doneWork = process.burstTime;
			process.quantum = 0;
			process.quantumHistory.add(process.quantum);
			InReadyList.remove(process);
			InDeadList.add(process);
			AGFactorQueue.remove(process);
			return true;
		} else {
			double tp = Math.ceil(process.quantum / 2.0);
			process.doneWork += tp;
			return false;
		}
	}
	private boolean nonExcuteProcess(AGProcessData process) {
		if (process.quantum - Math.ceil(process.quantum / 2.0) >= process.burstTime - process.doneWork) {
			CurrentTime += process.burstTime - process.doneWork;
			process.doneWork = process.burstTime;
			process.quantum = 0;
			process.quantumHistory.add(process.quantum);
			InReadyList.remove(process);
			InDeadList.add(process);
			AGFactorQueue.remove(process);
			return true;
		} else {
			double tp = process.quantum - Math.ceil(process.quantum / 2.0);
			process.doneWork += tp;
			return false;
		}
	}

	private boolean nonPreemptive(AGProcessData process) {
		Time start = new Time((long) CurrentTime);
		if (ExcuteProcess(process)) {
			Time end = new Time((long) CurrentTime);
			process.saveDuations.AddDuration(start, end);
			return true;
		} else {
			CurrentTime += Math.ceil(process.quantum / 2.0);
			return false;
		}
	}

	private boolean preemptive(AGProcessData process) {
		if (!AGQueueInput.isEmpty() && AGQueueInput.peek().arrivalTime >= CurrentTime + Math.ceil(process.quantum / 2.0)) {
			if (GetHigherPriority(process)) {
				Time start = new Time((long) (CurrentTime - Math.ceil(process.quantum / 2.0)));
				boolean done = nonExcuteProcess(process);
				if (!done) {
					CurrentTime += process.quantum - Math.ceil(process.quantum / 2.0);
					process.quantum += Math.ceil((GetMean(InReadyList) * 0.1));
					process.quantumHistory.add(process.quantum);
					process.lastWait = CurrentTime;
					Time end = new Time((long) CurrentTime);
					process.saveDuations.AddDuration(start, end);
					AGQueue.add(process);
					AGFactorQueue.add(process);
					CurrentTime += contextSwitchingHeadacheTime;
					return false;
				} else {
					Time end = new Time((long) CurrentTime);
					process.saveDuations.AddDuration(start, end);
					CurrentTime += contextSwitchingHeadacheTime;
					return true;
				}

			} else {
				Time start = new Time((long) (CurrentTime - Math.ceil(process.quantum / 2.0)));
				Time end = new Time((long) CurrentTime);
				process.saveDuations.AddDuration(start, end);
				process.quantum += process.quantum - Math.ceil(process.quantum / 2.0);
				process.quantumHistory.add(process.quantum);
				process.lastWait = CurrentTime;
				CurrentTime += contextSwitchingHeadacheTime;
				AGQueue.add(process);
				AGFactorQueue.add(process);
				this.priority = true;
				return false;
			}
		}
		else {
			if (GetHigherPriority(process)) {
				double remTime = 0.0;
				double remQuantum = Math.ceil(process.quantum / 2.0);
				if(!AGQueueInput.isEmpty())
					remTime = AGQueueInput.peek().arrivalTime - CurrentTime;
				else
					remTime = remQuantum;
				Time start = new Time((long) (CurrentTime - Math.ceil(process.quantum / 2.0)));
				Time end = null;
				
				if (process.burstTime - process.doneWork >= remTime) {
					process.doneWork += remTime;
					CurrentTime += remTime;
					remQuantum -= remTime;
				} 
				else {
					CurrentTime += process.burstTime - process.doneWork;
					process.doneWork = process.burstTime;
					process.quantum = 0;
					process.quantumHistory.add(process.quantum);
					end = new Time((long) CurrentTime);
					InReadyList.remove(process);
					InDeadList.add(process);
					AGFactorQueue.remove(process);
					CurrentTime += contextSwitchingHeadacheTime;
					return true;
				}

				RefreshList();
				boolean in = false;
				while (!AGQueueInput.isEmpty() && AGQueueInput.peek().AGFactor >= process.AGFactor && process.doneWork < process.burstTime
						&& remQuantum > 0) {
					in  = true;
					CurrentTime++;
					process.doneWork++;
					remQuantum--;
					RefreshList();
					if(GetHigherPriority(process)) {
						// leave with reaming quantum
						process.quantum += remQuantum;
						process.quantumHistory.add(process.quantum);
						process.lastWait = CurrentTime;
						end = new Time((long)CurrentTime);
						process.saveDuations.AddDuration(start, end);
						AGQueue.add(process);
						AGFactorQueue.add(process);
						CurrentTime += contextSwitchingHeadacheTime;
						this.priority = true;
						return false;
					}
				}
				if(!in) {
					if(process.burstTime <= process.doneWork) {
						//CurrentTime += process.burstTime - process.doneWork;
						process.doneWork = process.burstTime;
						process.quantum = 0;
						process.quantumHistory.add(process.quantum);
						end = new Time((long) CurrentTime);
						process.saveDuations.AddDuration(start, end);
						InReadyList.remove(process);
						InDeadList.add(process);
						AGFactorQueue.remove(process);
						CurrentTime += contextSwitchingHeadacheTime;
						return true;
					}
					else {
						process.quantum += remQuantum;
						process.quantumHistory.add(process.quantum);
						process.lastWait = CurrentTime;
						end = new Time((long)CurrentTime);
						process.saveDuations.AddDuration(start, end);
						AGQueue.add(process);
						AGFactorQueue.add(process);
						CurrentTime += contextSwitchingHeadacheTime;
						this.priority = true;
						return false;
					}
				}
				if(process.burstTime > process.doneWork) {
					// leave with ceil mean of quantum
					double mean = Math.ceil(GetMean(InReadyList)*0.1);
					process.quantum += mean;
					process.quantumHistory.add(process.quantum);
					process.lastWait = CurrentTime;
					end = new Time((long) CurrentTime);
					process.saveDuations.AddDuration(start, end);
					AGQueue.add(process);
					AGFactorQueue.add(process);
					CurrentTime += contextSwitchingHeadacheTime;
					return false;
				}
			}
			else {
				Time start = new Time((long) (CurrentTime - Math.ceil(process.quantum / 2.0)));
				Time end = new Time((long) CurrentTime);
				process.saveDuations.AddDuration(start, end);
				process.quantum += process.quantum - Math.ceil(process.quantum / 2.0);
				process.quantumHistory.add(process.quantum);
				process.lastWait = CurrentTime;
				CurrentTime += contextSwitchingHeadacheTime;
				AGQueue.add(process);
				AGFactorQueue.add(process);
				return false;
			}
			
		}
		return false;
	}
	
	
}
