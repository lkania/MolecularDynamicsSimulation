package itba.edu.ar.test.impl;

import java.io.IOException;
import java.util.List;

import itba.edu.ar.input.file.data.StaticFileData;
import itba.edu.ar.simulation.brownianMovement.impl.BrownianMovement;
import itba.edu.ar.simulation.output.series.CollisionFrecuencyVsTemperature;
import itba.edu.ar.test.AbstractTest;

public class TemperatureTest extends AbstractTest {

	private double fromTemperature;
	private double toTemperature;
	private double stepTemperature;
	private static final double length = 0.5;
	private static String path = System.getProperty("user.dir") + "/";
	private static int _SIMULATION_TIMES_=3;

	public TemperatureTest(double fromTemperature, double stepTemperature, double toTemperature) {
		super();
		this.fromTemperature = fromTemperature;
		this.toTemperature = toTemperature;
		this.stepTemperature = stepTemperature;
	}

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IOException {
		(new TemperatureTest(1,1,2)).simulate();
	}

	public void simulate() throws InstantiationException, IllegalAccessException, IOException {
		CollisionFrecuencyVsTemperature cfvt = new CollisionFrecuencyVsTemperature(path);

		for (double temperature = fromTemperature; temperature <= toTemperature; temperature += stepTemperature) {

			List<StaticFileData> staticFileDatas = getstaticFileDatasFromTemperature(temperature);
			BrownianMovement brownianMovement = new BrownianMovement(length, staticFileDatas, path);
			brownianMovement.subscribe(cfvt.getBrownianMovementObserver());

			for (int simulation = 0; simulation < _SIMULATION_TIMES_; simulation++) {
				brownianMovement.simulate(3);
			}

			cfvt.addSeriesPoint(temperature);

		}
		cfvt.plot();

	}

}
