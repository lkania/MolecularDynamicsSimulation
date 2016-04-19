package itba.edu.ar.simulation.output.info;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import itba.edu.ar.cellIndexMethod.data.particle.Particle;
import itba.edu.ar.simulation.brownianMovement.observer.AbstractBrownianMovementObserver;

public class BrownianMovementFileOutput extends AbstractBrownianMovementObserver {

	private static final String SEPARATOR = " ";
	private static final String COLUMNS_FILE = "Properties=id:I:1:pos:R:2:radius:I:1:mass:I:1:color:R:3:disp:R:2";
	private String pathFolder;
	private List<String> fileContent = new LinkedList<String>();
	private double length;

	public BrownianMovementFileOutput(String pathFolder, double length) {
		super();
		this.pathFolder = pathFolder;
		this.length = length;
	}

	public void frameEnded(int timeStep, List<Particle> particles) {
		StringBuilder sb = new StringBuilder();
		fileContent.add(particles.size() + "");

		fileContent.add("Time=" + timeStep + " " + sizeBox(length) + " " + COLUMNS_FILE);

		for (Particle particle : particles) {
			sb.append(particle.getId()).append(SEPARATOR).append(reescalePosition(particle.getPosition().getX())).append(SEPARATOR)
					.append(reescalePosition(particle.getPosition().getY())).append(SEPARATOR).append(particle.getRadio()).append(SEPARATOR).append(particle.getMass());
			addColor(sb, particle);
			addDisplacementVector(sb, particle);
			fileContent.add(sb.toString());
			sb = new StringBuilder();
		}

	}
	
	private double reescalePosition(double pos){	// When the position is less than 1, it has to be reescaled in order to Ovito understand 	
		return pos * 2;
	}

	private String sizeBox(double length) {
		String sizeX = length + " 0.00000000 0.00000000";
		String sizeY = "0.00000000 " + length + " 0.00000000";
		String sizeZ = "0.00000000 0.00000000 0.000000000000000001"; // sizeZ!=(0,0,0)
																		// for
																		// Ovito
																		// recognize
																		// the
																		// box
																		// size
		String sizeBox = "Lattice=\"" + sizeX + " " + sizeY + " " + sizeZ + "\"";
		return sizeBox;
	}

	private void addColor(StringBuilder sb, Particle particle) {
		sb.append(SEPARATOR).append(colorRange(Math.cos(particle.getAngle()))).append(SEPARATOR).append(colorRange(Math.sin(particle.getAngle()))
				+ SEPARATOR + 1 + SEPARATOR);
	}

	private double colorRange(double color) {
		return color / 2 + 0.5;
	}

	private void addDisplacementVector(StringBuilder sb, Particle particle) {
		double proyY = Math.sin(particle.getAngle()) * particle.getVelocityAbs();
		double proyX = Math.cos(particle.getAngle()) * particle.getVelocityAbs();

		sb.append(proyX).append(SEPARATOR).append(proyY);
	}

	private String getFileName() {
		return "BrownianMovement";
	}


	public void simulationEnded(){
		try {
			Files.write(Paths.get(pathFolder + getFileName()), fileContent, Charset.forName("UTF-8"));
		} catch (IOException e) {
			throw new IllegalAccessError();
		}

	}

}
