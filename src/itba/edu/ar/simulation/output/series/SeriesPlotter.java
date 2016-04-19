package itba.edu.ar.simulation.output.series;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.style.Styler.LegendPosition;

public class SeriesPlotter implements Plotter {

	private HashMap<Double, SeriesPoint> series = new HashMap<Double, SeriesPoint>();
	private String plotName;
	private String xAxis;
	private String yAxis;
	private String path;
	private List<String> seriesNames;

	public SeriesPlotter(String plotName, String xAxis, String yAxis, List<String> seriesNames, String path) {
		super();
		this.plotName = plotName;
		this.xAxis = xAxis;
		this.yAxis = yAxis;
		this.path = path;
		this.seriesNames = seriesNames;
	}

	private int getQuantityOfSeries() {
		return seriesNames.size();
	}


	public void addSeriesPoint(double temperature, Double... values) {

		if (!series.containsKey(temperature)) {
			series.put(temperature, new SeriesPoint(getQuantityOfSeries()));
		}
		series.get(temperature).addValue(values);

	}

	public void plot() throws IOException {
		final XYChart chart = new XYChartBuilder().width(600).height(400).title(plotName).xAxisTitle(xAxis)
				.yAxisTitle(yAxis).build();

		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		
		List<Double> temperatures = asSortedList(series.keySet());
		List<ArrayList<Double>> finalSeries = getLists(getQuantityOfSeries());

		for (Double temperature : temperatures) {
			SeriesPoint sp = series.get(temperature);

			for (int serie = 0; serie < getQuantityOfSeries(); serie++) {
				Double value = sp.getAvgValueofSerie(serie);
				finalSeries.get(serie).add(value);
			}
		}

		double[] x = doubleListToArray(temperatures);
		List<double[]> allY = doubleListsToArray(finalSeries);

		for (int serie = 0; serie < getQuantityOfSeries(); serie++) {
			chart.addSeries(seriesNames.get(serie), x, allY.get(serie));
		}

		BitmapEncoder.saveBitmapWithDPI(chart, path + getFileName(), BitmapFormat.PNG, 300);
	}

	private String getFileName() {
		String ans = plotName;
		ans = ans.replaceAll("\\s", "-");
		return ans;
	}

	private List<ArrayList<Double>> getLists(int quantity) {
		List<ArrayList<Double>> ans = new ArrayList<ArrayList<Double>>();
		for (int i = 0; i < quantity; i++) {
			ans.add(new ArrayList<Double>());
		}
		return ans;
	}

	private static List<double[]> doubleListsToArray(List<ArrayList<Double>> finalSeries) {
		List<double[]> ans = new ArrayList<double[]>();
		for (List<Double> list : finalSeries) {
			ans.add(doubleListToArray(list));
		}
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

	public static <T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
		List<T> list = new LinkedList<T>(c);
		java.util.Collections.sort(list);
		return list;
	}

	private class SeriesPoint {

		private Double[] avgValues;
		private int simulationTimes = 0;

		public SeriesPoint(int quantityOfSeries) {
			avgValues = new Double[quantityOfSeries];
		}

		public void addValue(Double... values) {
			for (int i = 0; i < values.length; i++) {
				avgValues[i] = addValue(avgValues[i], values[i]);
			}
			simulationTimes++;
		}

		public double getAvgValueofSerie(int index) {
			return avgValues[index] / simulationTimes;
		}

		private Double addValue(Double v1, Double v2) {
			if (v1 == null)
				return v2;
			return v1 + v2;
		}
	}

}
