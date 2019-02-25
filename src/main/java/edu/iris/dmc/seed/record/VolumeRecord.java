package edu.iris.dmc.seed.record;

import edu.iris.dmc.seed.SeedException;

public class VolumeRecord extends AbstractRecord {

	public VolumeRecord(int sequence, boolean continuation) throws SeedException {
		super(sequence, 'V', continuation);
	}

	public VolumeRecord(int size, int sequence, boolean continuation) throws SeedException {
		super(sequence, 'V', continuation, size);
	}


}
