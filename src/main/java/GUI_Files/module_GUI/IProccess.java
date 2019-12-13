package GUI_Files.module_GUI;

import java.awt.Color;
import java.sql.Time;
import java.util.List;

public interface IProccess {
	public abstract String GetProccessName();
	public abstract List<ProccessData> GetProccessList();
	public abstract Time GetMaxProccessTime();
	public abstract Time GetMinProccessTime();
	public abstract Color GetProccessColor();
	public abstract void AddDuration(Time start, Time end);
	public abstract boolean OverLapes(Time start, Time end);
}
