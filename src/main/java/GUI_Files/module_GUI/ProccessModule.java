package GUI_Files.module_GUI;

import java.awt.Color;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ProccessModule implements IProccess {
	private String proccesName;
	private Time start;
	private Time end;
	private List<ProccessData> proccessList;
	private Color color;
	
	public ProccessModule(String name, Color color) {
		this.proccesName = name;
		this.start = new Time(0);
		this.end = new Time(0);
		this.proccessList = new ArrayList<ProccessData>();
		this.color = color;
	}

	@Override
	public void AddDuration(Time start, Time end) {
		if(OverLapes(start, end))
			System.out.println(proccesName + ": overlapping occured");
		else {
			if(start.before(this.start))
				this.start = start;
			if(end.after(this.end))
				this.end = end;
			ProccessData pData = new ProccessData(start, end);
			proccessList.add(pData);
		}
	}

	@Override
	public boolean OverLapes(Time start, Time end) {
		for(ProccessData prc : proccessList) {
			if(prc.GetStart().before(start) && prc.GetEnd().after(start))
				return true;
			if(prc.GetStart().before(end) && prc.GetEnd().after(end))
				return true;
		}
		return false;
	}

	@Override
	public String GetProccessName() {
		return this.proccesName;
	}

	@Override
	public List<ProccessData> GetProccessList() {
		return this.proccessList;
	}

	@Override
	public Time GetMaxProccessTime() {
		return this.end;
	}

	@Override
	public Time GetMinProccessTime() {
		return this.start;
	}

	@Override
	public Color GetProccessColor() {
		return this.color;
	}
}
