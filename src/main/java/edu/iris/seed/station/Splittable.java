package edu.iris.seed.station;

import java.util.List;

import edu.iris.seed.SeedException;

public interface Splittable {
	public List<? extends ResponseBlockette> split();
}
