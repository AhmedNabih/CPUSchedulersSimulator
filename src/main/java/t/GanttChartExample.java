package t;

import java.awt.Color;
import java.awt.Paint;
import java.sql.Time;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.GanttRenderer;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;



public class GanttChartExample extends JFrame {
	public static Map<Integer, Color> map = new HashMap<Integer, Color>();
	private static final long serialVersionUID = 1L;

	void addProccesColor(Integer column, Color color) {
		map.put(column, color);
	}

	public GanttChartExample(String title) {
		super(title);
		// Create dataset
		IntervalCategoryDataset dataset = getCategoryDataset();

		// Create chart
		JFreeChart chart = ChartFactory.createGanttChart("Gantt Chart Example", // Chart title
				"Software Development Phases", // X-Axis Label
				"Timeline", // Y-Axis Label
				dataset, true, true, false);

		final CategoryPlot plot = (CategoryPlot) chart.getPlot();
		MyRenderery renderer = new MyRenderery();
		plot.setRenderer(renderer);

		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1080, 720));
		
		setContentPane(chartPanel);
	}

	private IntervalCategoryDataset getCategoryDataset() {

		TaskSeries series1 = new TaskSeries("Estimated Date");
		
		series1.add(new Task("Requirement", new Time(0), new Time(5)));
		addProccesColor(0, Color.black);

		series1.add(new Task("Design", new Time(1), new Time(4)));
		addProccesColor(1, Color.red);
		
		series1.add(new Task("Coding", new Time(3), new Time(7)));
		addProccesColor(2, Color.green);
		
		series1.add(new Task("Testing", new Time(8), new Time(10)));
		addProccesColor(3, Color.gray);
		
		series1.add(new Task("Deployment", new Time(9), new Time(15)));
		addProccesColor(4, Color.blue);
		
		TaskSeriesCollection dataset = new TaskSeriesCollection();
		dataset.add(series1);
		return dataset;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			GanttChartExample example = new GanttChartExample("Gantt Chart Example");
			example.setSize(800, 400);
			example.setLocationRelativeTo(null);
			example.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
			example.setVisible(true);
		});
	}
}

class MyRenderery extends GanttRenderer {
	private static final long serialVersionUID = 1L;

	public MyRenderery() {
		super();
	}

	@Override
	public Paint getItemPaint(int row, int col) {
		if (!GanttChartExample.map.isEmpty()) {
			Integer tp = col;
			Color ans = GanttChartExample.map.get(tp);
			return ans;
		} else
			return super.getItemPaint(row, col);
	}

}