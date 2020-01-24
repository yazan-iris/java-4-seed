package edu.iris.seed.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.SeedDataHeader;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedRecord;
import edu.iris.seed.data.DataBlockette;

public class DataRecord extends SeedRecord<DataBlockette> {

	private List<DataBlockette> blockettes = new ArrayList<>();

	private byte[]data;
	public DataRecord(SeedDataHeader header) {
		super(header);
	}

	@Override
	public DataBlockette add(DataBlockette t) throws SeedException {
		blockettes.add(t);
		return t;

	}

	@Override
	public DataBlockette get(int... type) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public List<DataBlockette> blockettes() {
		return blockettes;
	}

	public boolean isEmpty() {
		return this.blockettes.isEmpty();
	}

	public int size() {
		return this.blockettes().size();
	}

	@Override
	public void clear() {
		blockettes.clear();
	}

	@Override
	public int writeTo(OutputStream outputStream, int recordLength, int sequence) throws SeedException, IOException {
		// TODO Auto-generated method stub
		return 0;
	}

	public static class Builder {
		private byte[] bytes;
		private SeedDataHeader header;

		private Builder() {
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public Builder fromBytes(byte[] bytes) throws SeedException {
			this.bytes = bytes;
			return this;
		}

		public Builder header(SeedDataHeader header) throws SeedException {
			this.header = header;
			return this;
		}

		public DataRecord build(boolean relax) throws SeedException {
			DataRecord record = null;
			if (bytes != null) {
				record = new DataRecord(SeedDataHeader.Builder.newInstance().bytes(bytes).build(false));
			} else if (header != null) {
				record = new DataRecord(header);
			} else {
				throw new SeedException("Cannot build DataRecord when bytes and header are null.");
			}
			return record;
		}

	}

}
