package itba.edu.ar.data.collision;

import itba.edu.ar.cellIndexMethod.data.particle.FloatPoint;
import itba.edu.ar.cellIndexMethod.data.particle.Particle;
import itba.edu.ar.data.Wall;

public class WallCollision implements Collision {

	private Wall wall;
	private Particle particle;

	public WallCollision(Wall wall, Particle particle) {
		super();
		this.wall = wall;
		this.particle = particle;
	}

	public void collide() {
		FloatPoint velocity = particle.getVelocity();
		int vx = 0, vy=0;

		switch (wall.getOrientation()) {
		case Horizontal:
			vx = 1;
			vy = -1;
			break;
		case Vertical:
			vx = -1;
			vy = 1;
			break;
		}

		particle.setVelocity(velocity.getX() * vx, velocity.getY() * vy);
	}

	public double getMaxVelocity() {
		return particle.getVelocityAbs();
	}

}
