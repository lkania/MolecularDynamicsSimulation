package itba.edu.ar.simulation.output.series;

import java.io.IOException;

public interface Plotter {

	public void addSeriesPoint(double temperature, Double... values);

	public void plot() throws IOException;

}
