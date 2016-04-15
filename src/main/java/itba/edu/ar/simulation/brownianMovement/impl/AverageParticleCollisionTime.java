package itba.edu.ar.simulation.brownianMovement.impl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import itba.edu.ar.cellIndexMethod.CellIndexMethod;
import itba.edu.ar.cellIndexMethod.IndexMatrix;
import itba.edu.ar.cellIndexMethod.IndexMatrixBuilder;
import itba.edu.ar.cellIndexMethod.route.routeImpl.BruteForceRoute;
import itba.edu.ar.simulation.brownianMovement.AbstractBrownianMovement;

public class AverageParticleCollisionTime extends AbstractBrownianMovement {

	private static double BRUTE_FORCE_INTERACTION_RADIO = 1;
	private List<Double> minCollisionTimes = new LinkedList<Double>();
	private IndexMatrix indexMatrix;

	public AverageParticleCollisionTime(String staticPath, String dynamicPath)
			throws InstantiationException, IllegalAccessException, IOException {
		indexMatrix = IndexMatrixBuilder.getIndexMatrix(staticPath, dynamicPath, 1, 0);
		this.particles = indexMatrix.getParticles();
		generateWalls(indexMatrix.getLength());
	}

	public Double getAverageCollisionTime(int collisions)
			throws InstantiationException, IllegalAccessException, IOException {

		CellIndexMethod cellIndexMethod = new CellIndexMethod(indexMatrix, new BruteForceRoute(indexMatrix.getLength()),
				BRUTE_FORCE_INTERACTION_RADIO);

		for (int collision = 0; collision < collisions; collision++) {
			System.out.println("Collision: "+collision);
			cellIndexMethod.execute();

			Double minCollisionTime = getMinCollisionTime();
			minCollisionTimes.add(minCollisionTime);

			calculateNextStep(minCollisionTime);

			indexMatrix.clear();
			indexMatrix.addParticles(particles);
		}

		return getAvgCollisionTimes(minCollisionTimes);

	}

	private static Double getAvgCollisionTimes(List<Double> minCollisionTimes) {
		Double ans = 0.0;
		for (Double value : minCollisionTimes) {
			ans += value;
		}
		return ans / minCollisionTimes.size();
	}

}
