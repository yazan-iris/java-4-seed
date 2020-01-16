package edu.iris.seed.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

import edu.iris.seed.SeedException;
import edu.iris.seed.record.Header.Type;
import edu.iris.seed.timespan.TimeSpanBlockette;

public class TimeSpanRecord extends SeedRecord<TimeSpanBlockette> {

	public TimeSpanRecord() {
		this(-1, false);
	}

	public TimeSpanRecord(int sequence, boolean continuation) {
		this(ControlHeader.Builder.newInstance().build(sequence, Type.T, continuation));
	}

	public TimeSpanRecord(ControlHeader header) {
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

		private Builder() {
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public TimeSpanRecord build(byte[] bytes) throws SeedException {
			TimeSpanRecord record = new TimeSpanRecord(ControlHeader.Builder.newInstance().build(bytes));

			record.setBytes(bytes);
			return record;
		}
	}

	@Override
	public TimeSpanBlockette get(int... type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TimeSpanBlockette> getAll() {
		return Collections.emptyList();
	}
	public boolean isEmpty() {
		return this.getAll().isEmpty();
	}

	public int size() {
		return this.getAll().size();
	}
}
