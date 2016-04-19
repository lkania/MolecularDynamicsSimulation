package itba.edu.ar.simulation.output.info;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import itba.edu.ar.cellIndexMethod.data.particle.Particle;
import itba.edu.ar.simulation.brownianMovement.observer.AbstractBrownianMovementObserver;
import itba.edu.ar.simulation.brownianMovement.observer.BrownianMovementObserver;

public class StatPrinter {

	private long collisions = 0;
	private double totalSimulationTime; 
	private double avgCollisionTime;
	private String pathFolder;
	private static String fileName = "Simulation-Stats";
	
	public StatPrinter(String pathFolder) {
		super();
		this.pathFolder = pathFolder;
	}

	private BrownianMovementObserver bmo = new AbstractBrownianMovementObserver(){


		@Override
		public void collisionTime(double collisionTime) {
			collisions++;
		};
		
		@Override
		public void avgCollisionTime(double avgCollisionTime) {
			StatPrinter.this.avgCollisionTime=avgCollisionTime;
		};
		
		@Override
		public void simulationDuration(double totalTime) {
			totalSimulationTime=totalTime;
		};
		
		@Override
		public void simulationEnded() {
			List<String> fileContent = new LinkedList<String>();
			fileContent.add("Collision Frecuency: "+collisions/totalSimulationTime);
			fileContent.add("Average Collision Time: "+avgCollisionTime);
			
			try {
				Files.write(Paths.get(pathFolder + fileName), fileContent, Charset.forName("UTF-8"));
			} catch (IOException e) {
				throw new IllegalAccessError();
			}
		};
	};
	
	public BrownianMovementObserver getBrownianMovementObserver(){
		return bmo;
	}
	
	
}
