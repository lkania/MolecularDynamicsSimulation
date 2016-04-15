package itba.edu.ar.utilities;

import itba.edu.ar.cellIndexMethod.data.particle.FloatPoint;
import itba.edu.ar.cellIndexMethod.data.particle.Particle;
import itba.edu.ar.data.Wall;

public class ParticleLibrary {

	public static FloatPoint getDeltaVelocity(Particle particle, Particle neightbour) {

		FloatPoint p1 = particle.getVelocity();
		FloatPoint p2 = neightbour.getVelocity();

		return new FloatPoint(p2.getX() - p1.getX(), p2.getY() - p1.getY());

	}

	public static FloatPoint getDeltaPosition(Particle particle, Particle neightbour) {

		FloatPoint p1 = particle.getPosition();
		FloatPoint p2 = neightbour.getPosition();

		return new FloatPoint(p2.getX() -p1.getX(), p2.getY() - p1.getY());
	}

	
}
