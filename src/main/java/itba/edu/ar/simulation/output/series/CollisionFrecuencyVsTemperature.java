package itba.edu.ar.simulation.output.series;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import itba.edu.ar.simulation.brownianMovement.observer.AbstractBrownianMovementObserver;
import itba.edu.ar.simulation.brownianMovement.observer.BrownianMovementObserver;

public class CollisionFrecuencyVsTemperature {

	private static final String xAxis = "Temperature";
	private static final String yAxis = "Collision Frecuency";
	private static final List<String> seriesNames;
	private static String plotName = "CollisionFrecuency Vs Temperature";

	static {
		seriesNames = new ArrayList<String>();
		seriesNames.add("All frames");
	}

	private Plotter seriesPlotter;

	public CollisionFrecuencyVsTemperature(String path) {
		seriesPlotter = new SeriesPlotter(plotName, xAxis, yAxis, seriesNames, path);
	}

	private long collisions = 0;
	private double totalSimulationTime;
	private double collisionFrecuency;

	private BrownianMovementObserver bmo = new AbstractBrownianMovementObserver() {

		@Override
		public void collisionTime(double collisionTime) {
			collisions++;
		};

		@Override
		public void simulationDuration(double totalTime) {
			totalSimulationTime = totalTime;
		};

		public void simulationEnded() {
			collisionFrecuency = collisions / totalSimulationTime;
			collisions = 0;
		};

	};

	public BrownianMovementObserver getBrownianMovementObserver() {
		return bmo;
	}

	public void addSeriesPoint(double temperature) {
		seriesPlotter.addSeriesPoint(temperature, collisionFrecuency);
	}

	public void plot() throws IOException {
		seriesPlotter.plot();
	}

}
