package itba.edu.ar.data.collision;

import itba.edu.ar.cellIndexMethod.data.particle.FloatPoint;
import itba.edu.ar.cellIndexMethod.data.particle.Particle;
import itba.edu.ar.utilities.ParticleLibrary;

public class ParticleCollision implements Collision {

	private Particle p1;
	private Particle p2;

	public ParticleCollision(Particle p1, Particle p2) {
		super();
		this.p1 = p1;
		this.p2 = p2;
	}

	public void collide() {
		FloatPoint deltaVelocity = ParticleLibrary.getDeltaVelocity(p1, p2);
		FloatPoint deltaPosition = ParticleLibrary.getDeltaPosition(p1, p2);
		double deltaVelocityMultiplydeltaPosition = deltaVelocity.multiply(deltaPosition);
		double sigma = p1.getRadio() + p2.getRadio();

		double impulse = 2 * p1.getMass() * p2.getMass() * (deltaVelocityMultiplydeltaPosition)
				/ (sigma * (p1.getMass() + p2.getMass()));
		double impulseX = impulse * deltaPosition.getX() / sigma;
		double impulseY = impulse * deltaPosition.getY() / sigma;
	
		setVelocity(p1, impulseX, impulseY, 1);
		setVelocity(p2, impulseX, impulseY, -1);

	}
	
	private void setVelocity(Particle particle,double impulseX,double impulseY,int sign){
		particle.setVelocity(particle.getVelocity().getX()+sign*impulseX/particle.getMass(),particle.getVelocity().getY()+sign*impulseY/particle.getMass());
	}

	public double getMaxVelocity() {
		
		return Math.max(p1.getVelocityAbs(), p2.getVelocityAbs());
			
	}
}
