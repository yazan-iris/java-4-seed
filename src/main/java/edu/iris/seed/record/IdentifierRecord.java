package edu.iris.seed.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TreeMap;

import edu.iris.seed.SeedControlHeader;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedHeader.Type;
import edu.iris.seed.SeedOutputStream;
import edu.iris.seed.SeedRecord;
import edu.iris.seed.Identifier.IdentifierBlockette;
import edu.iris.seed.io.ControlBlocketteIterator;

public class IdentifierRecord extends SeedRecord<IdentifierBlockette> {

	private Map<Integer, IdentifierBlockette> map = new TreeMap<>();

	public IdentifierRecord() {
		this(1, false);
	}

	public IdentifierRecord(int sequence, boolean continuation) {
		this(SeedControlHeader.Builder.newInstance(sequence, Type.V, continuation).build());
	}

	public IdentifierRecord(SeedControlHeader header) {
		super(header);
	}

	@Override
	public IdentifierBlockette add(IdentifierBlockette t) throws SeedException {
		int type = t.getType();
		IdentifierBlockette current = map.get(type);
		if (current == null) {
			map.put(type, t);
		} else {
			// check if we can append
		}
		return t;
	}

	public boolean addAll(Collection<IdentifierBlockette> c) throws SeedException {
		int size = this.size();
		for (IdentifierBlockette i : c) {
			add(i);
		}
		return size != this.size();
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public void clear() {
		map.clear();

	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean remove(IdentifierBlockette i) {
		return map.remove(i.getType(), i);
	}

	public List<IdentifierBlockette> blockettes() {
		return new ArrayList<>(map.values());
	}

	public IdentifierBlockette get(int... number) {
		if (number != null && number.length > 0) {
			return map.get(number[0]);
		}
		return null;
	}

	@Override
	public int writeTo(OutputStream outputStream, int recordLength, int sequence) throws SeedException, IOException {
		SeedOutputStream stream = new SeedOutputStream(outputStream, recordLength, sequence,
				this.getHeader().getRecordType());
		stream.writeControl(blockettes());
		return stream.flush();
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

		public IdentifierRecord build() throws SeedException {
			IdentifierRecord record = new IdentifierRecord(SeedControlHeader.Builder.newInstance(bytes).build());

			ControlBlocketteIterator<IdentifierBlockette> it = new ControlBlocketteIterator<>(8,
					bytes);
			while (it.hasNext()) {
				IdentifierBlockette b = it.next();
				record.add(b);
			}
			return record;
		}
	}

}
