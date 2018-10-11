package edu.iris.dmc.seed.control.station;

import java.util.List;

import edu.iris.dmc.seed.Blockette;

public interface OverFlowBlockette {

	public List<Blockette> split();
	public boolean isOverFlown();
}
