package itba.edu.ar.simulation.brownianMovement.impl;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import itba.edu.ar.cellIndexMethod.CellIndexMethod;
import itba.edu.ar.cellIndexMethod.IndexMatrix;
import itba.edu.ar.cellIndexMethod.IndexMatrixBuilder;
import itba.edu.ar.cellIndexMethod.data.particle.Particle;
import itba.edu.ar.cellIndexMethod.route.routeImpl.OptimizedRoute;
import itba.edu.ar.input.file.CellIndexMethodFileGenerator;
import itba.edu.ar.input.file.data.StaticFileData;
import itba.edu.ar.simulation.brownianMovement.AbstractBrownianMovement;
import itba.edu.ar.simulation.brownianMovement.observer.BrownianMovementObserver;

public class BrownianMovement extends AbstractBrownianMovement {

	private static final int _COLLISIONS_PER_FRAME_ = 2;
	private static final int _COLLISIONS_ = 1000;
	private static final int timeStep = 0;
	private double length;
	private String path;
	private List<String> staticPaths = new LinkedList<String>();
	private List<String> dynamicPaths = new LinkedList<String>();
	private Double averageCollisionTime;
	private List<BrownianMovementObserver> subscribers = new LinkedList<BrownianMovementObserver>();
	private List<StaticFileData> staticFileDatas;

	public BrownianMovement(double length, List<StaticFileData> staticFileDatas, String path)
			throws InstantiationException, IllegalAccessException, IOException {
		super();
		this.length = length;
		this.staticFileDatas = staticFileDatas;
		this.path = path;
		generateWalls(length);
		setUpSimulation();
	}

	private void setUpSimulation() throws InstantiationException, IllegalAccessException, IOException {

		CellIndexMethodFileGenerator.generate(staticPaths, dynamicPaths, length, staticFileDatas, path);

		AverageParticleCollisionTime averageParticleCollisionTime = new AverageParticleCollisionTime(staticPaths.get(0),
				dynamicPaths.get(0));
		averageCollisionTime = averageParticleCollisionTime.getAverageCollisionTime(_COLLISIONS_);

	}

	public void simulate(int frames) throws InstantiationException, IllegalAccessException, IOException {

		notifyAvgCollisionTime(averageCollisionTime);

		int cellQuantity = getMaxCellQuantity();
		IndexMatrix indexMatrix = IndexMatrixBuilder.getIndexMatrix(staticPaths.get(0), dynamicPaths.get(0),
				cellQuantity, timeStep);
		particles = indexMatrix.getParticles();

		CellIndexMethod cellIndexMethod = new CellIndexMethod(indexMatrix,
				new OptimizedRoute(cellQuantity, false, indexMatrix.getLength()), getInteractionRadio(), getMaxRadio());

		Double minCollisionTime;

		double timeBetweenFrames = averageCollisionTime * _COLLISIONS_PER_FRAME_;
		notifySimulationDuration(timeBetweenFrames * frames);

		int timelapse = 0;

		for (int frame = 0; frame < frames; frame++) {
			System.out.println("Frame: " + frame);

			for (double currentTime = 0; currentTime < timeBetweenFrames; currentTime += minCollisionTime) {

				cellIndexMethod.execute();

				minCollisionTime = getMinCollisionTime();

				if (currentTime + minCollisionTime > timeBetweenFrames) {
					moveParticles(timeBetweenFrames - currentTime);
					notifyFrameEnded(timelapse, particles);
					timelapse++;
					currentTime = timeBetweenFrames;
				} else {
					calculateNextStep(minCollisionTime);
					notifyCollisionTime(minCollisionTime);
				}

				indexMatrix.clear();
				indexMatrix.addParticles(particles);

			}
		}
		notifySimulationEnded();

	}

	private void notifySimulationDuration(double totalTime) {
		for (BrownianMovementObserver subscriber : subscribers)
			subscriber.simulationDuration(totalTime);
	}

	private void notifyCollisionTime(double collisionTime) {
		for (BrownianMovementObserver subscriber : subscribers)
			subscriber.collisionTime(collisionTime);

	}

	private void notifySimulationEnded() throws IOException {
		for (BrownianMovementObserver subscriber : subscribers)
			subscriber.simulationEnded();
	}

	private void notifyAvgCollisionTime(double averageCollisionTime) {
		for (BrownianMovementObserver subscriber : subscribers)
			subscriber.avgCollisionTime(averageCollisionTime);
	}

	private double getInteractionRadio() {
		return getMaxVelocity() * averageCollisionTime * 2 + 2 * getMaxRadio();
	}

	private int getMaxCellQuantity() {
		return (int) Math.ceil(length / (getInteractionRadio() + 2 * getMaxRadio())) - 1;
	}

	private double getMaxRadio() {
		double ans = 0;
		for (StaticFileData data : staticFileDatas)
			if (data.getRadio() > ans)
				ans = data.getRadio();
		return ans;
	}

	private void notifyFrameEnded(int timelapse, List<Particle> particles) {
		for (BrownianMovementObserver subscriber : subscribers)
			subscriber.frameEnded(timelapse, particles);
	}

	public void subscribe(BrownianMovementObserver subscriber) {
		subscribers.add(subscriber);
	}

}
