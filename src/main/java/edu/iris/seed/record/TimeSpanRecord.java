package edu.iris.seed.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.SeedControlHeader;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedHeader.Type;
import edu.iris.seed.SeedRecord;
import edu.iris.seed.timespan.TimeSpanBlockette;

public class TimeSpanRecord extends SeedRecord<TimeSpanBlockette> {

	private List<TimeSpanBlockette> blockettes = new ArrayList<>();

	public TimeSpanRecord() {
		this(-1, false);
	}

	public TimeSpanRecord(int sequence, boolean continuation) {
		this(SeedControlHeader.Builder.newInstance(sequence, Type.T, continuation).build());
	}

	public TimeSpanRecord(SeedControlHeader header) {
		super(header);
	}

	@Override
	public TimeSpanBlockette add(TimeSpanBlockette t) throws SeedException {
		return t;
	}

	@Override
	public int writeTo(OutputStream outputStream, int recordLength, int sequence) throws SeedException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public static class Builder {
		private byte[] bytes;

		private Builder() {
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public Builder fromBytes(byte[] bytes) throws SeedException {
			this.bytes = bytes;
			return this;
		}

		public TimeSpanRecord build(byte[] bytes) throws SeedException {
			TimeSpanRecord record = new TimeSpanRecord(SeedControlHeader.Builder.newInstance(bytes).build());
			return record;
		}
	}

	@Override
	public TimeSpanBlockette get(int... type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TimeSpanBlockette> blockettes() {
		return blockettes;
	}

	public boolean isEmpty() {
		return this.blockettes().isEmpty();
	}

	public int size() {
		return this.blockettes().size();
	}

	@Override
	public void clear() {
		blockettes.clear();

	}
}
