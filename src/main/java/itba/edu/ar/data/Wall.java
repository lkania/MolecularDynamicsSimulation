package itba.edu.ar.data;

import itba.edu.ar.cellIndexMethod.data.particle.FloatPoint;

public class Wall {

	public enum Orientation {
		Horizontal, Vertical
	};

	private FloatPoint position;
	private Orientation orientation;

	public Wall(FloatPoint position, Orientation orientation) {
		super();
		this.position = position;
		this.orientation = orientation;
	}

	public FloatPoint getPosition() {
		return position;
	}

	public Orientation getOrientation() {
		return orientation;
	}

}
