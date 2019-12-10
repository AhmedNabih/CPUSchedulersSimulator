package CPUSchedulersSimulator;

public class Main {
	public static void main(String[] args) {
		CPUSystemInvoker invoker = new CPUSystemInvoker();
		
		SJF sjf = new SJF();
		
		invoker.AddSystem(sjf);
		
		invoker.Invoke();
		
	}
}
