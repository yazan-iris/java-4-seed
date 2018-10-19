package edu.iris.dmc.seed.control.station;

import java.util.List;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.SeedException;

public interface OverFlowBlockette {

	public List<Blockette> split();
	public boolean isOverFlown() throws SeedException;;
}
