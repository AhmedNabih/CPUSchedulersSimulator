package GUI_Files.viewer_GUI;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import GUI_Files.module_GUI.IProcess;
import GUI_Files.module_GUI.ProcessData;
import GUI_Files.module_GUI.ProcessModule;

// MVC => Viewer, MainAPPViewer
public class MainApp_Viewer extends JFrame {
	private static final long serialVersionUID = 1L;
	public Map<Integer, Color> map = new HashMap<Integer, Color>();
	private List<ProcessModule> processList;

	public MainApp_Viewer(String title, List<ProcessModule> processList) {
		super(title);
		this.processList = processList;
		initialize();
	}

	void addProcessColor(Integer column, Color color) {
		map.put(column, color);
	}

	private IntervalCategoryDataset getCategoryDataset() {
		TaskSeries seriesList = new TaskSeries("");
		
		int columns = 0; 
		for(IProcess prc : processList) {
			Task BigTask = new Task(prc.GetProcessName(), prc.GetMinProcessTime(), prc.GetMaxProcessTime());	
			List<ProcessData> subProcessList = prc.GetProcessList();
			
			int subTasksSZ = subProcessList.size();
			Task[] subTasks = new Task[subTasksSZ];
			
			Integer key = 0;
			for(ProcessData prcData : subProcessList) {
				subTasks[key] = new Task("Process" + key.toString(), prcData.GetStart(), prcData.GetEnd());
				key++;
			}
			for(Task task : subTasks) {
				BigTask.addSubtask(task);
			}
			addProcessColor(columns, prc.GetProcessColor());
			columns++;
			
			seriesList.add(BigTask);
		}
		
		TaskSeriesCollection dataset = new TaskSeriesCollection();
		dataset.add(seriesList);
		return dataset;
	}
	
	
	private void initialize() {
		// Get Data
		IntervalCategoryDataset dataset = getCategoryDataset();
		
		// Create chart
		JFreeChart chart = ChartFactory.createGanttChart("CPU", // Chart title
				"Procceses", // X-Axis Label
				"Timeline", // Y-Axis Label
				dataset, 
				true, 
				true, 
				false);

		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		MyRenderer renderer = new MyRenderer(this);
		plot.setRenderer(renderer);

		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1080, 720));
		
		setContentPane(chartPanel);
	}
}
