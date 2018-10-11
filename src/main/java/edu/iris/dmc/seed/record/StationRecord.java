package edu.iris.dmc.seed.record;

public class StationRecord extends AbstractRecord {

	public StationRecord(int sequence, boolean continuation) {
		super(sequence, 'S', continuation);
	}

	public StationRecord(int size, int sequence, boolean continuation) {
		super(sequence, 'S', continuation, size);
	}

}
