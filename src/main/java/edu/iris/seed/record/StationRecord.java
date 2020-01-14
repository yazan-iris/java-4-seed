package edu.iris.seed.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import edu.iris.seed.SeedException;
import edu.iris.seed.SeedOutputStream;
import edu.iris.seed.record.Header.Type;
import edu.iris.seed.station.B050;
import edu.iris.seed.station.StationBlockette;

public class StationRecord extends SeedRecord<StationBlockette> {

	private B050 b050;
	private int numberOfBlockettes;
	public StationRecord() {
		this(0, false);
	}

	public StationRecord(int sequence, boolean continuation) {
		this(ControlHeader.Builder.newInstance().build(sequence, Type.S, continuation));
	}

	public StationRecord(ControlHeader header) {
		super(header);
	}

	@Override
	public StationBlockette add(StationBlockette t) throws SeedException {
		if (this.b050 != null) {
			throw new SeedException("No blockette of type 50 available,add it forst.", t.toSeedString());
		}
		this.b050.add(t);
		return t;

	}

	public void add(B050 b050) throws SeedException {
		if (this.b050 != null) {
			throw new SeedException("Duplicate blockettes, [{}] already exist in this record", b050.toSeedString());
		}
		this.b050 = b050;
	}

	public B050 getB050() {
		return this.b050;
	}

	public List<StationBlockette> get(int type) {
		if (type == 50) {
			return Arrays.asList(this.b050);
		} else {
			return Collections.emptyList();
		}
	}

	@Override
	public StationBlockette get(int... type) {
		if (type == null || type.length == 0) {
			return null;
		}
		List<StationBlockette> blockettes = get(type[0]);
		if (blockettes == null || blockettes.isEmpty()) {
			return null;
		}
		return blockettes.get(0);
	}

	public List<StationBlockette> getAll() {
		if (this.b050 == null) {

		}
		List<StationBlockette> l = new ArrayList<>();
		l.add(this.b050);
		l.addAll(this.b050.getAll());
		return l;
	}

	@Override
	public int getNumberOfBlockettes() {
		return getAll().size();
	}
	@Override
	public int writeTo(OutputStream outputStream, int recordLength, int sequence) throws SeedException, IOException {
		SeedOutputStream stream = new SeedOutputStream(outputStream, recordLength, sequence,
				this.getHeader().getType());
		stream.write(getAll());
		return stream.flush();
	}

	public static class Builder {

		private Builder() {
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public StationRecord build(byte[] bytes) throws SeedException {
			StationRecord record = new StationRecord(ControlHeader.Builder.newInstance().build(bytes));
			record.setBytes(bytes);
			return record;
		}
	}

}
