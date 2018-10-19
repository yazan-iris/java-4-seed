package edu.iris.dmc.seed.record;

import edu.iris.dmc.seed.SeedException;

public class StationRecord extends AbstractRecord {

	public StationRecord(int sequence, boolean continuation) throws SeedException {
		super(sequence, 'S', continuation);
	}

	public StationRecord(int size, int sequence, boolean continuation) throws SeedException {
		super(sequence, 'S', continuation, size);
	}

}
