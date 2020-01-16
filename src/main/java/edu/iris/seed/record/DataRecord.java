package edu.iris.seed.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.List;

import edu.iris.seed.SeedException;
import edu.iris.seed.data.DataBlockette;
import edu.iris.seed.record.Header.Type;
import edu.iris.seed.timespan.TimeSpanBlockette;

public class DataRecord extends SeedRecord<DataBlockette> {

	public DataRecord(Type quality) {
		this(0, quality);
	}

	public DataRecord(int sequence, Type quality) {
		this(0, quality, false);
	}

	public DataRecord(int sequence, Type quality, boolean continuation) {
		this(DataHeader.Builder.newInstance().build(sequence, quality, continuation));
	}

	public DataRecord(DataHeader header) {
		super(header);
	}

	@Override
	public DataBlockette add(DataBlockette t) throws SeedException {
		return t;

	}

	@Override
	public DataBlockette get(int... type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DataBlockette> getAll() {
		return Collections.emptyList();
	}

	public boolean isEmpty() {
		return this.getAll().isEmpty();
	}

	public int size() {
		return this.getAll().size();
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

		public DataRecord build(byte[] bytes) throws SeedException {
			DataRecord record = new DataRecord(DataHeader.Builder.newInstance().build(bytes));
			record.setBytes(bytes);
			return record;
		}

	}
}
