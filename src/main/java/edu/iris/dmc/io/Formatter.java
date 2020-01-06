package edu.iris.dmc.io;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.SeedException;

public interface Formatter {
	public String format(Blockette blockette)throws SeedException;
}
