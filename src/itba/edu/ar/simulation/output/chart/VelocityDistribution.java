package itba.edu.ar.simulation.output.chart;

import java.io.IOException;

import itba.edu.ar.cellIndexMethod.data.particle.Particle;
import itba.edu.ar.simulation.brownianMovement.observer.AbstractBrownianMovementObserver;
import itba.edu.ar.simulation.brownianMovement.observer.BrownianMovementObserver;

public class VelocityDistribution extends AbstractCategoryChart {

	private static final String plotName = "Velocity Distribution";
	private static final String yAxis = "Quantity of Particles";
	private static final String xAxis = "Velocity Ranges";
	private static final String serieName = "All frames";
	private AbstractBrownianMovementObserver abm = new AbstractBrownianMovementObserver() {

		public void frameEnded(int timeStep, java.util.List<Particle> particles) {
			for (Particle particle : particles) {
				int range = (int) Math.floor(particle.getVelocityAbs() / rangeSize);
				addToRange(range);
			}
		};

		public void simulationEnded() throws IOException {
			plot();
		};
	};

	public VelocityDistribution(double rangeSize, String path) {
		super(plotName, yAxis, xAxis, serieName, rangeSize, path);
	}

	public BrownianMovementObserver getBrownianMovementObserver() {
		return abm;
	}

}
