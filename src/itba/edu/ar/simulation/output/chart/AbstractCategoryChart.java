package itba.edu.ar.simulation.output.chart;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;

public class AbstractCategoryChart{

	private String plotName;
	private String yAxis;
	private String xAxis;
	private String serieName;
	private Map<Integer, Integer> categories = new HashMap<Integer, Integer>();
	protected double rangeSize;
	private String path;

	public AbstractCategoryChart(String plotName, String yAxis, String xAxis, String serieName, double rangeSize) {
		super();
		this.plotName = plotName;
		this.yAxis = yAxis;
		this.xAxis = xAxis;
		this.serieName = serieName;
		this.rangeSize = rangeSize;
	}

	protected void addToRange(int range) {
		if (!categories.containsKey(range)) {
			categories.put(range, 0);
		}
		categories.put(range, categories.get(range) + 1);

	}

	protected void plot() throws IOException {
		final CategoryChart chart = new CategoryChartBuilder().width(1600).height(900).title(plotName).xAxisTitle(xAxis)
				.yAxisTitle(yAxis).build();
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);

		List<Double> x = new LinkedList<Double>();
		List<Double> y = new LinkedList<Double>();

		for (Entry<Integer, Integer> range : categories.entrySet()) {
			x.add(range.getKey() * rangeSize + rangeSize / 2);
			y.add((double) range.getValue());
		}

		chart.addSeries(serieName, doubleListToArray(x), doubleListToArray(y));

		BitmapEncoder.saveBitmapWithDPI(chart, path + getFileName(), BitmapFormat.PNG, 300);
	}

	private String getFileName() {
		String ans = plotName;
		ans = ans.replaceAll("\\s", "-");
		return ans;
	}
	
	private static double[] doubleListToArray(List<Double> list) {
		double[] ans = new double[list.size()];
		int i = 0;
		for (Double value : list) {
			ans[i] = value.doubleValue();
			i++;
		}
		return ans;
	}

}
