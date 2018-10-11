package edu.iris.dmc.seed.record;


public class DictionaryRecord extends AbstractRecord {

	public DictionaryRecord(int sequence, boolean continuation) {
		super(sequence, 'A', continuation);
	}

	public DictionaryRecord(int size, int sequence, boolean continuation) {
		super(sequence, 'A', continuation, size);
	}

}
