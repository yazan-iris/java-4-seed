package edu.iris.seed.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

import edu.iris.seed.Blockette;
import edu.iris.seed.SeedException;
import edu.iris.seed.record.Header.Type;

public class EmptyRecord extends SeedRecord<Blockette> {

	public EmptyRecord() {
		this(0);
	}

	public EmptyRecord(int sequence) {
		this(sequence, false);
	}

	public EmptyRecord(int sequence, boolean continuation) {
		super(ControlHeader.Builder.newInstance().build(sequence, Type.E, continuation));
	}

	@Override
	public Blockette add(Blockette t) throws SeedException {
		return t;

	}

	@Override
	public int writeTo(OutputStream outputStream, int recordLength, int sequence) throws SeedException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Blockette get(int... type) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Blockette> getAll() {
		return Collections.emptyList();
	}
	
	public boolean isEmpty() {
		return true;
	}

	public int size() {
		return 0;
	}
}
