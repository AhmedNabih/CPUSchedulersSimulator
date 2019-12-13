package GUI_Files.viewer_GUI;

import java.awt.Color;
import java.awt.Paint;
import org.jfree.chart.renderer.category.GanttRenderer;

class MyRenderer extends GanttRenderer {
	private MainApp_Viewer page;
	private static final long serialVersionUID = 1L;

	public MyRenderer(MainApp_Viewer page) {
		super();
		this.page = page;
	}

	@Override
	public Paint getItemPaint(int row, int col) {
		if (!page.map.isEmpty()) {
			Integer tp = col;
			Color ans = page.map.get(tp);
			return ans;
		} else
			return super.getItemPaint(row, col);
	}

}