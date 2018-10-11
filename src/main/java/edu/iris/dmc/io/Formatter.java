package edu.iris.dmc.io;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.control.dictionary.DictionaryBlockette;
import edu.iris.dmc.seed.control.index.IndexBlockette;
import edu.iris.dmc.seed.control.station.StationBlockette;

public interface Formatter {
	public String format(Blockette blockette);
}
