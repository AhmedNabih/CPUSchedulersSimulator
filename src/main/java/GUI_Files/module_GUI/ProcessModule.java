package GUI_Files.module_GUI;

import java.awt.Color;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import CPUSchedulersSimulator.AGScheduling.AGProcessData;

public class ProcessModule implements IProcess {
	private String processName;
	private Time start;
	private Time end;
	private List<ProcessData> processList;
	private Color color;
	
	public ProcessModule(String name, Color color) {
		this.processName = name;
		this.start = new Time(0);
		this.end = new Time(0);
		this.processList = new ArrayList<ProcessData>();
		this.color = color;
	}
	
	public ProcessModule(AGProcessData data) {
		this.processName = data.processName;
		this.start = data.saveDuations.GetMinProcessTime();
		this.end = data.saveDuations.GetMaxProcessTime();
		this.processList = data.saveDuations.GetProcessList();
		this.color = data.saveDuations.GetProcessColor();
	}

	@Override
	public void AddDuration(Time start, Time end) {
		if(OverLapes(start, end))
			System.out.println(processName + ": overlapping occured");
		else {
			if(start.before(this.start))
				this.start = start;
			if(end.after(this.end))
				this.end = end;
			ProcessData pData = new ProcessData(start, end);
			processList.add(pData);
		}
	}

	@Override
	public boolean OverLapes(Time start, Time end) {
		for(ProcessData prc : processList) {
			if(prc.GetStart().before(start) && prc.GetEnd().after(start))
				return true;
			if(prc.GetStart().before(end) && prc.GetEnd().after(end))
				return true;
		}
		return false;
	}

	@Override
	public String GetProcessName() {
		return this.processName;
	}

	@Override
	public List<ProcessData> GetProcessList() {
		return this.processList;
	}

	@Override
	public Time GetMaxProcessTime() {
		return this.end;
	}

	@Override
	public Time GetMinProcessTime() {
		return this.start;
	}

	@Override
	public Color GetProcessColor() {
		return this.color;
	}
}
