package itba.edu.ar.simulation.output.chart;

import java.io.IOException;

import itba.edu.ar.cellIndexMethod.data.particle.Particle;
import itba.edu.ar.simulation.brownianMovement.observer.AbstractBrownianMovementObserver;
import itba.edu.ar.simulation.brownianMovement.observer.BrownianMovementObserver;

public class VelocityDistribution extends AbstractCategoryChart {

	private static final String plotName = "Velocity Distribution";
	private static final String yAxis = "Quantity of Particles";
	private static final String xAxis = "Velocity Ranges";
	private AbstractBrownianMovementObserver abm = new AbstractBrownianMovementObserver() {

		public void frameEnded(int frame, java.util.List<Particle> particles) {
			if (frame < captureFromFrame)
				return;

			for (Particle particle : particles) {
				int range = (int) Math.floor(particle.getVelocityAbs() / rangeSize);
				addToRange(range);
			}
		};

		public void simulationEnded() throws IOException {
			plot();
		};
	};
	private int captureFromFrame;

	public VelocityDistribution(double rangeSize, String path, int frames) {
		super(plotName, yAxis, xAxis, "Last third of "+frames+" frames", rangeSize, path);
		this.captureFromFrame = (int) Math.floor(frames * 2 / (double) 3);
	}

	public BrownianMovementObserver getBrownianMovementObserver() {
		return abm;
	}

}
