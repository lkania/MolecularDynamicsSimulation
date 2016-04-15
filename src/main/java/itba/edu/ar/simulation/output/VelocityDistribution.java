package itba.edu.ar.simulation.output;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler.LegendPosition;

import itba.edu.ar.cellIndexMethod.data.particle.Particle;
import itba.edu.ar.simulation.brownianMovement.impl.BrownianMovementObserver;

public class VelocityDistribution implements BrownianMovementObserver {

	private static final String plotName = "Velocity Distribution";
	private static final String yAxis = "Quantities Particles";
	private static final String xAxis = "Velocity Range";
	private static final String seriesName = "All frames";
	private Map<Integer, Integer> velocityDistribution = new HashMap<Integer, Integer>();
	private double rangeSize;


	public VelocityDistribution(double rangeSize,String path) {
		super();
		this.rangeSize = rangeSize;
	}

	public void frameEnded(int timeStep, List<Particle> particles) {
		for (Particle particle : particles) {
			int range = (int) Math.floor(particle.getVelocityAbs() / rangeSize);
			addToRange(range);
		}
	}
	
	private void addToRange(int range) {
		if (!velocityDistribution.containsKey(range)) {
			velocityDistribution.put(range, 0);
		}
		velocityDistribution.put(range, velocityDistribution.get(range) + 1);

	}

	public void simulationEnded() {
		final CategoryChart chart = new CategoryChartBuilder().width(600).height(400).title(plotName).xAxisTitle(xAxis)
				.yAxisTitle(yAxis).build();
		chart.getStyler().setLegendPosition(LegendPosition.OutsideE);
		
		List<Double> x = new LinkedList<Double>();
		List<Double> y = new LinkedList<Double>();
				
		for(Entry<Integer,Integer> range : velocityDistribution.entrySet()){
			x.add(range.getKey()*rangeSize+rangeSize/2);
			y.add((double)range.getValue());
		}
		
		chart.addSeries(seriesName, doubleListToArray(x), doubleListToArray(y));
		
	    new SwingWrapper(chart).displayChart();

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
