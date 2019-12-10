
import java.util.List;

public class CPUSystemInvoker {
	private List<ICPUSystem> systemsListl;
	
	public CPUSystemInvoker() {
		
	}
	
	public void AddSystem(ICPUSystem sys) {
		systemsListl.add(sys);
	}
	
	public void Invoke() {
		for(ICPUSystem sys : systemsListl) {
			sys.excute();
		}
	}
}
