package itba.edu.ar.test.impl;

import java.io.IOException;
import java.util.List;

import itba.edu.ar.input.file.data.StaticFileData;
import itba.edu.ar.simulation.brownianMovement.impl.BrownianMovement;
import itba.edu.ar.simulation.output.chart.CollisionTimeDistribution;
import itba.edu.ar.simulation.output.chart.VelocityDistribution;
import itba.edu.ar.simulation.output.info.BrownianMovementFileOutput;
import itba.edu.ar.simulation.output.info.StatPrinter;
import itba.edu.ar.test.AbstractTest;

public class BrownianMovementPaperTest extends AbstractTest {

	private static final int _TOTAL_FRAMES_ = 5000;
	private static final double length = 0.5;
	private static final double rangeSize = 0.01;
	private static String path = System.getProperty("user.dir") + "/";

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IOException {

		List<StaticFileData> staticFileDatas = (new BrownianMovementPaperTest()).getstaticFileDatas();

		BrownianMovement brownianMovement = new BrownianMovement(length, staticFileDatas, path);
		brownianMovement.subscribe(new BrownianMovementFileOutput(path, length));
		brownianMovement.subscribe(new VelocityDistribution(rangeSize, path,_TOTAL_FRAMES_).getBrownianMovementObserver());
		brownianMovement.subscribe(new CollisionTimeDistribution(rangeSize, path));
		brownianMovement.subscribe((new StatPrinter(path)).getBrownianMovementObserver());
		brownianMovement.simulate(_TOTAL_FRAMES_);

	}

}
