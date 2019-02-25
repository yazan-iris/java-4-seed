package edu.iris.dmc.seed.record;

import java.util.Objects;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.SeedException;

public class DictionaryRecord extends AbstractRecord {

	public DictionaryRecord(int sequence, boolean continuation) throws SeedException {
		super(sequence, 'A', continuation);
	}

	public DictionaryRecord(int size, int sequence, boolean continuation) throws SeedException {
		super(sequence, 'A', continuation, size);
	}

}
