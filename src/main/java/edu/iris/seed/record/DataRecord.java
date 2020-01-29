package edu.iris.seed.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import edu.iris.seed.DataBlockette;
import edu.iris.seed.SeedDataHeader;
import edu.iris.seed.SeedDataOutputStream;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedRecord;
import edu.iris.seed.data.DataSection;

public class DataRecord extends SeedRecord<DataBlockette> {

	private Map<Integer, DataBlockette> map = new TreeMap<>();
	// private List<DataBlockette> blockettes = new ArrayList<>();

	private byte[] data;

	public DataRecord(SeedDataHeader header) {
		super(header);
	}

	@Override
	public DataBlockette add(DataBlockette t) throws SeedException {
		if (t == null) {

		}
		if (t instanceof DataSection) {
			DataSection ds = (DataSection) t;
			setData(ds.getData());
		}
		int type = t.getType();
		DataBlockette db = map.get(type);
		if (db == null) {
			map.put(type, t);
		}

		SeedDataHeader header = (SeedDataHeader) this.getHeader();

		header.setNumberOfFollowingBlockettes(map.size());
		map.entrySet().stream().forEach(e -> e.getValue());
		return t;
	}

	@Override
	public DataBlockette get(int... type) {
		if (type != null && type.length > 0) {
			return map.get(type[0]);
		}
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
		return new ArrayList<>(map.values());
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	public int size() {
		return this.blockettes().size();
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public int writeTo(OutputStream outputStream, int recordLength, int sequence) throws SeedException, IOException {

		// SeedContext.get().get(blocketteNumber)
		// this.
		// this.getData();

		SeedDataOutputStream stream = new SeedDataOutputStream(outputStream, recordLength, sequence, this);

		for (Entry<Integer, DataBlockette> e : map.entrySet()) {
			e.getValue().toSeedBytes();
		}

		stream.writeData(this.blockettes());
		return stream.flush();
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

		public DataRecord build() throws SeedException {
			DataRecord record = null;
			if (bytes != null) {
				record = new DataRecord(SeedDataHeader.Builder.newInstance().bytes(bytes).build());
			} else if (header != null) {
				record = new DataRecord(header);
			} else {
				throw new SeedException("Cannot build DataRecord when bytes and header are null.");
			}
			return record;
		}

	}

}
