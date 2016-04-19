package itba.edu.ar.simulation.output.chart;

import java.io.IOException;
import java.util.List;

import itba.edu.ar.cellIndexMethod.data.particle.Particle;
import itba.edu.ar.simulation.brownianMovement.observer.AbstractBrownianMovementObserver;
import itba.edu.ar.simulation.brownianMovement.observer.BrownianMovementObserver;

public class CollisionTimeDistribution extends AbstractCategoryChart implements BrownianMovementObserver{

	
	private static final String plotName = "Collision Time Distribution";
	private static final String yAxis = "Quantity of Collisions";
	private static final String xAxis = "Collision Time Ranges";
	private static final String serieName = "All frames";

	public CollisionTimeDistribution(double rangeSize,String path) {
		super(plotName, yAxis, xAxis, serieName, rangeSize);
	}

	public void frameEnded(int timeStep, List<Particle> particles) {
		//
	}


	public void simulationEnded() throws IOException {
		plot();
	}

	public void collisionTime(double collisionTime) {
		int range = (int) Math.floor(collisionTime / rangeSize);
		addToRange(range);
	}

	public void simulationDuration(double totalTime) {
		// TODO Auto-generated method stub
		
	}

	public void avgCollisionTime(double avgCollisionTime) {
		// TODO Auto-generated method stub
		
	}


	
	
	
	
}
