package itba.edu.ar.test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import itba.edu.ar.input.file.data.StaticFileData;
import itba.edu.ar.simulation.brownianMovement.impl.BrownianMovement;
import itba.edu.ar.simulation.output.BrownianMovementFileOutput;
import itba.edu.ar.simulation.output.VelocityDistribution;

public class Test {

	private static final double length = 0.5;
	private static final double rangeSize = 0.01;
	private static String path = System.getProperty("user.dir")+"/";
	private static List<StaticFileData> staticFileDatas= new LinkedList<StaticFileData>();

	static {
		staticFileDatas.add(new StaticFileData(1, 100, 0.05));
		staticFileDatas.add(new StaticFileData(20, 1, 0.005,0.1,-0.1));
	}

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, IOException {

		BrownianMovement brownianMovement = new BrownianMovement(length, staticFileDatas, path);
		brownianMovement.Subscribe(new BrownianMovementFileOutput(path, length));
		brownianMovement.Subscribe(new VelocityDistribution(rangeSize, path));
		brownianMovement.simulate(1000);

	}


}
