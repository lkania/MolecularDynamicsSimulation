package itba.edu.ar.simulation.output.info;

import itba.edu.ar.simulation.brownianMovement.observer.AbstractBrownianMovementObserver;
import itba.edu.ar.simulation.brownianMovement.observer.BrownianMovementObserver;

public class StatPrinter {

	private long collisions = 0;
	private double totalSimulationTime; 
	private double avgCollisionTime;
	
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
			System.out.println("Collision Frecuency: "+collisions/totalSimulationTime);
			System.out.println("Average Collision Time: "+avgCollisionTime);
		};
	};
	
	public BrownianMovementObserver getBrownianMovementObserver(){
		return bmo;
	}
	
	
}
