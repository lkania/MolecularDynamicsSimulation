package itba.edu.ar.test;

import java.util.LinkedList;
import java.util.List;

import itba.edu.ar.input.file.data.StaticFileData;

public abstract class AbstractTest {

	private int bigParticleQuantity = 1;
	private double bigParticleMass = 100;
	private double bigParticleRadio = 0.05;
	private int smallParticleQuantity = 20;
	private double smallParticleMass = 1;
	private double smallParticleRadio = 0.005;
	
	
	protected List<StaticFileData> getstaticFileDatas() {
		return getstaticFileDatasFromVelocity(0.1);
	}

	private List<StaticFileData> getstaticFileDatasFromVelocity(double velocityAbs) {
		List<StaticFileData> staticFileDatas = new LinkedList<StaticFileData>();
		staticFileDatas.add(new StaticFileData(bigParticleQuantity, bigParticleMass, bigParticleRadio));
		staticFileDatas
				.add(new StaticFileData(smallParticleQuantity, smallParticleMass, smallParticleRadio, velocityAbs, -velocityAbs));
		return staticFileDatas;
	}
	
	protected List<StaticFileData> getstaticFileDatasFromTemperature(double temperature) {
		double avgMass = (bigParticleMass * bigParticleQuantity + smallParticleMass * smallParticleQuantity)
				/ getTotalParticles();
		double avgVelocity = getAvgVelocity(temperature, avgMass);
		return getstaticFileDatasFromVelocity(avgVelocity);
	}

	private double getTotalParticles() {
		return bigParticleQuantity + smallParticleQuantity;
	}

	private static double _K_ = 1.381 * Math.pow(10, -23);

	private static double getAvgVelocity(double temperature, double avgMass) {
		return Math.sqrt(_K_ * temperature / avgMass);
	}

	
}
