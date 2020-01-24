package edu.iris.seed.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.iris.seed.SeedControlHeader;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedHeader.Type;
import edu.iris.seed.SeedOutputStream;
import edu.iris.seed.SeedRecord;
import edu.iris.seed.abbreviation.AbbreviationBlockette;
import edu.iris.seed.io.BlocketteIterator;

public class AbbreviationRecord extends SeedRecord<AbbreviationBlockette> {

	private Map<Integer, Dictionary> dictionaries = new HashMap<>();

	public AbbreviationRecord() {
		this(1, false);
	}

	public AbbreviationRecord(int sequence, boolean continuation) {
		this(new SeedControlHeader(sequence, Type.A, continuation));
	}

	public AbbreviationRecord(SeedControlHeader header) {
		super(header);
	}

	@Override
	public AbbreviationBlockette get(int... type) {
		// TODO Auto-generated method stub
		return null;
	}

	public AbbreviationBlockette add(AbbreviationBlockette a) throws SeedException {
		int type = a.getType();
		Dictionary d = null;
		if (type > 40 && type < 50) {
			d = dictionaries.get(60);
			if (d == null) {
				d = new Dictionary(60, 9999);
				dictionaries.put(60, d);
			}
		} else {
			d = dictionaries.get(a.getType());
			if (d == null) {
				d = new Dictionary(a.getType(), 9999);
				dictionaries.put(a.getType(), d);
			}
		}

		return d.add(a);
	}

	public boolean addAll(Collection<AbbreviationBlockette> c) throws SeedException {
		int size = size();
		for (AbbreviationBlockette a : c) {
			add(a);
		}
		return size != size();
	}

	public AbbreviationBlockette get(int type, int lookup) {
		int bType = type;
		if (bType > 40 && bType < 50) {
			bType = 60;
		}

		Dictionary d = dictionaries.get(bType);
		if (d == null || d.isEmpty()) {
			return null;
		}
		return d.get(lookup);
	}

	public int size() {
		int size = 0;
		for (Entry<Integer, Dictionary> e : this.dictionaries.entrySet()) {
			size += e.getValue().size();
		}
		return size;
	}

	public List<AbbreviationBlockette> blockettes() {
		List<AbbreviationBlockette> list = new ArrayList<>();
		for (Entry<Integer, Dictionary> e : this.dictionaries.entrySet()) {
			list.addAll(e.getValue().getAll());
		}
		return list;
	}

	public void clear() {
		for (Dictionary d : this.dictionaries.values()) {
			d.clear();
		}
	}

	/*
	 * 
	 * 
	 * @Override public AbbreviationBlockette add(AbbreviationBlockette a) throws
	 * SeedException { int type = a.getType(); Dictionary d = null; if (type > 40 &&
	 * type < 50) { d = dictionaries.get(60); if (d == null) { d = new
	 * Dictionary(60, 9999); dictionaries.put(60, d); } } else { d =
	 * dictionaries.get(a.getType()); if (d == null) { d = new
	 * Dictionary(a.getType(), 9999); dictionaries.put(a.getType(), d); } }
	 * 
	 * return d.add(a); }
	 * 
	 * @Override public AbbreviationBlockette get(int... type) { if (type == null ||
	 * type.length == 0) { return null; } int bType = type[0]; if (bType > 40 &&
	 * bType < 50) { bType = 60; } Dictionary d = dictionaries.get(bType); if (d ==
	 * null) { return null; } if (type.length > 1) { return d.get(type[1]); } return
	 * null; }
	 * 
	 * public AbbreviationBlockette get(int type, int lookup) { int bType = type; if
	 * (bType > 40 && bType < 50) { bType = 60; }
	 * 
	 * Dictionary d = dictionaries.get(bType); if (d == null || d.isEmpty()) {
	 * return null; } return d.get(lookup); }
	 * 
	 * public List<AbbreviationBlockette> getAll() { return this.blockettes;
	 * 
	 * }
	 * 
	 * public boolean isEmpty() { return this.blockettes.isEmpty(); }
	 * 
	 * public int size() { return this.blockettes.size(); }
	 */
	@Override
	public int writeTo(OutputStream outputStream, int recordLength, int sequence) throws SeedException, IOException {
		SeedOutputStream stream = new SeedOutputStream(outputStream, recordLength, sequence,
				this.getHeader().getRecordType());
		stream.write(this.blockettes());
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

		public AbbreviationRecord build() throws SeedException { 
			AbbreviationRecord record = new AbbreviationRecord(SeedControlHeader.Builder.newInstance(bytes).build());
			BlocketteIterator<AbbreviationBlockette> it = new BlocketteIterator<AbbreviationBlockette>(8, bytes);
			while (it.hasNext()) {
				AbbreviationBlockette b = it.next();
				record.add(b);
			}
			return record;
		}
	}

	class Dictionary {
		private Map<Integer, AbbreviationBlockette> map = new HashMap<>();
		private Counter counter;
		private final int type;

		Dictionary(int type, int capacity) {
			this.type = type;
			counter = new Counter(capacity);
		}

		AbbreviationBlockette add(AbbreviationBlockette b) throws SeedException {
			if (map.containsValue(b)) {
				for (AbbreviationBlockette ab : map.values()) {
					if (ab.equals(b)) {
						return ab;
					}
				}
			} else {
				if (b.getLookupKey() == 0) {
					int lookup = counter.increment();
					b.setLookupKey(lookup);
				}
				// int lookup = counter.increment();
				// b.setLookupKey(lookup);
				map.put(b.getLookupKey(), b);
			}
			return b;
		}

		AbbreviationBlockette get(int id) {
			return map.get(id);
		}

		public List<AbbreviationBlockette> getAll() {
			return new ArrayList<>(map.values());
		}

		int size() {
			return map.size();
		}

		void clear() {
			map.clear();
		}

		boolean isEmpty() {
			return map.isEmpty();
		}

		class Counter {
			private int count = 0;
			private int max;

			Counter(int max) {
				this.max = max;
			}

			public synchronized int increment() throws SeedException {
				if (count > max) {
					throw new SeedException("Dictionary type {} reached its maximum capacity of {}", type, max);
				}
				return ++count;
			}
		}
	}

}
