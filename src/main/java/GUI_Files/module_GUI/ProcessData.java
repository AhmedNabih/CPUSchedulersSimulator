package GUI_Files.module_GUI;

import java.sql.Time;

public class ProcessData {
	private Time start;
	private Time end;

	public Time GetStart() {
		return start;
	}

	public Time GetEnd() {
		return end;
	}

	public ProcessData() {
		this.start = new Time(0);
		this.end = new Time(0);
	}

	public ProcessData(Time start, Time end) {
		this.start = start;
		this.end = end;
	}

	public String toString() {
		return this.start.toString() + this.end.toString();
	}

}
