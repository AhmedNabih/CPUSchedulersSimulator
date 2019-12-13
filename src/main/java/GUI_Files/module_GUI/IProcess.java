package GUI_Files.module_GUI;

import java.awt.Color;
import java.sql.Time;
import java.util.List;

public interface IProcess {
	public abstract String GetProcessName();
	public abstract List<ProcessData> GetProcessList();
	public abstract Time GetMaxProcessTime();
	public abstract Time GetMinProcessTime();
	public abstract Color GetProcessColor();
	public abstract void AddDuration(Time start, Time end);
	public abstract boolean OverLapes(Time start, Time end);
}
