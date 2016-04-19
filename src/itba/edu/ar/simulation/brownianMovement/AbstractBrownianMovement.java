package itba.edu.ar.simulation.brownianMovement;

import java.util.LinkedList;
import java.util.List;

import itba.edu.ar.cellIndexMethod.data.particle.FloatPoint;
import itba.edu.ar.cellIndexMethod.data.particle.Particle;
import itba.edu.ar.data.Wall;
import itba.edu.ar.data.Wall.Orientation;
import itba.edu.ar.data.collision.Collision;
import itba.edu.ar.data.collision.ParticleCollision;
import itba.edu.ar.data.collision.WallCollision;
import itba.edu.ar.utilities.ParticleLibrary;

public abstract class AbstractBrownianMovement {

	protected Collision collision = null;
	protected List<Particle> particles;

	private List<Wall> walls = new LinkedList<Wall>();
	private double maxVelocity = 0;

	protected List<Wall> getWalls() {
		return walls;
	}

	protected void generateWalls(double length) {
		walls.add(new Wall(new FloatPoint(0, 0), Orientation.Horizontal));
		walls.add(new Wall(new FloatPoint(0, length), Orientation.Horizontal));
		walls.add(new Wall(new FloatPoint(length, 0), Orientation.Vertical));
		walls.add(new Wall(new FloatPoint(0, 0), Orientation.Vertical));
	}

	protected Double getMinCollisionTime() {

		Double minParticleCollisionTime = null;
		Double collisionTime = null;

		for (Particle particle : particles) {

			for (Particle neightbour : particle.getNeightbours()) {

				collisionTime = getCollisionTime(particle, neightbour);

				if (collisionTime != null && collisionTime < 0)
					throw new IllegalAccessError();

				if (collisionTime != null
						&& (minParticleCollisionTime == null || collisionTime < minParticleCollisionTime)) {
					minParticleCollisionTime = collisionTime;
					collision = new ParticleCollision(particle, neightbour);

				}

			}

			for (Wall wall : walls) {
				collisionTime = getCollsionTime(particle, wall);

				if (collisionTime != null && collisionTime < 0)
					throw new IllegalAccessError();

				if (collisionTime != null
						&& (minParticleCollisionTime == null || collisionTime < minParticleCollisionTime)) {
					minParticleCollisionTime = collisionTime;
					collision = new WallCollision(wall, particle);

				}

			}

		}

		return minParticleCollisionTime;
	}
	
	private Double getCollsionTime(Particle particle, Wall wall) {

		Double collisionTime = null;

		switch (wall.getOrientation()) {
		case Horizontal:
			if (wall.getPosition().getY() == 0) {
				collisionTime = getCollisionTimeOrigin(particle.getRadio(), particle.getPosition().getY(),
						particle.getVelocity().getY(), wall.getPosition().getY());
			} else {
				collisionTime = getCollisionTimeLimit(particle.getRadio(), particle.getPosition().getY(),
						particle.getVelocity().getY(), wall.getPosition().getY());
			}

			break;
		case Vertical:
			if (wall.getPosition().getX() == 0) {
				collisionTime = getCollisionTimeOrigin(particle.getRadio(), particle.getPosition().getX(),
						particle.getVelocity().getX(), wall.getPosition().getX());
			} else {
				collisionTime = getCollisionTimeLimit(particle.getRadio(), particle.getPosition().getX(),
						particle.getVelocity().getX(), wall.getPosition().getX());
			}

			break;
		}

		return collisionTime;
	}

	private static Double getCollisionTimeLimit(double radio, double positionParticle, double velocityParticle,
			double positionWall) {

		if (velocityParticle == 0)
			return null;

		if (!(sign((positionWall - positionParticle)) == sign(velocityParticle)))
			return null;

		return (positionWall - radio - positionParticle) / velocityParticle;
	}

	private static Double getCollisionTimeOrigin(double radio, double positionParticle, double velocityParticle,
			double positionWall) {

		if (velocityParticle == 0)
			return null;

		if (!(sign((positionWall - positionParticle)) == sign(velocityParticle)))
			return null;

		return (radio - positionParticle) / velocityParticle;
	}

	public static int sign(double d) {
		if (d < 0)
			return -1;
		return 1;
	}

	public static Double getCollisionTime(Particle particle, Particle neightbour) {

		double sigma = particle.getRadio() + neightbour.getRadio();
		FloatPoint deltaVelocity = ParticleLibrary.getDeltaVelocity(particle, neightbour);
		FloatPoint deltaPosition = ParticleLibrary.getDeltaPosition(particle, neightbour);

		double deltaVelocityMultiplydeltaPosition = deltaVelocity.multiply(deltaPosition);

		if (deltaVelocityMultiplydeltaPosition >= 0)
			return null;

		double pow2deltaVelocity = deltaVelocity.multiply(deltaVelocity);

		double discriminant = Math.pow(deltaVelocityMultiplydeltaPosition, 2)
				- pow2deltaVelocity * (deltaPosition.multiply(deltaPosition) - Math.pow(sigma, 2));

		if (discriminant < 0)
			return null;

		Double time = (-1) * (deltaVelocityMultiplydeltaPosition + Math.sqrt(discriminant)) / (pow2deltaVelocity);

		return time;
	}


	protected void calculateNextStep(Double minCollisionTime) {

		moveParticles(minCollisionTime);
		modifyCollidedParticles();
	}

	protected void moveParticles(double minCollisionTime) {
		for (Particle particle : particles) {

			FloatPoint position = particle.getPosition();
			FloatPoint velocity = particle.getVelocity();

			FloatPoint newPosition = position.plus(velocity.multiply(minCollisionTime));
			particle.setPosition(newPosition);

		}
	}
	
	private void modifyCollidedParticles() {
		collision.collide();
		maxVelocity = Math.max(collision.getMaxVelocity(), maxVelocity);
	}

	protected Double getMaxVelocity() {
		return maxVelocity;
	}


}
