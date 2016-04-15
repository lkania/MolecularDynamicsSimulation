package itba.edu.ar.simulation.brownianMovement.impl;

import java.util.List;

import itba.edu.ar.cellIndexMethod.data.particle.Particle;

public interface BrownianMovementObserver {

	public void frameEnded(int timeStep, List<Particle> particles);
	public void simulationEnded();
	
}
