package itba.edu.ar.simulation.brownianMovement.observer;

import java.io.IOException;
import java.util.List;

import itba.edu.ar.cellIndexMethod.data.particle.Particle;

public interface BrownianMovementObserver {

	public void frameEnded(int timeStep, List<Particle> particles);
	public void collisionTime(double collisionTime);
	public void simulationEnded() throws IOException;
	public void simulationDuration(double totalTime);
	public void avgCollisionTime(double avgCollisionTime);
	
}
