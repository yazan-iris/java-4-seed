package edu.iris.seed.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import edu.iris.seed.Blockette;
import edu.iris.seed.SeedControlHeader;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedHeader.Type;
import edu.iris.seed.SeedRecord;

public class EmptyRecord extends SeedRecord<Blockette> {

	public EmptyRecord() {
		this(0);
	}

	public EmptyRecord(int sequence) {
		this(sequence, false);
	}

	public EmptyRecord(int sequence, boolean continuation) {
		super(SeedControlHeader.Builder.newInstance(sequence, Type.E, continuation).build());
	}

	@Override
	public Blockette add(Blockette t) throws SeedException {
		return t;

	}

	@Override
	public int writeTo(OutputStream outputStream, int recordLength, int sequence) throws SeedException, IOException {
		return 0;
	}

	@Override
	public Blockette get(int... type) {
		return null;
	}

	@Override
	public List<Blockette> blockettes() {
		return Collections.emptyList();
	}

	public boolean isEmpty() {
		return true;
	}

	public int size() {
		return 0;
	}

	@Override
	public void clear() {

	}

	@Override
	public boolean addAll(Collection<Blockette> c) throws SeedException {
		return false;
	}

	@Override
	public boolean remove(Blockette e) {
		return false;
	}
}
