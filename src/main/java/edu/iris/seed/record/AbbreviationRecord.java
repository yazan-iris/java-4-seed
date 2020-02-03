package edu.iris.seed.record;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
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
import edu.iris.seed.io.ControlBlocketteIterator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		if (type == null || type.length == 0) {
			return null;
		}
		int bType = type[0];
		if (bType > 40 && bType < 50) {
			bType = 60;
		}
		Dictionary d = dictionaries.get(bType);
		if (d == null) {
			return null;
		}
		if (type.length > 1) {
			return d.get(type[1]);
		}
		return null;
	}
	/*
	 * public AbbreviationBlockette get(int type, int lookup) { int bType = type; if
	 * (bType > 40 && bType < 50) { bType = 60; }
	 * 
	 * Dictionary d = dictionaries.get(bType); if (d == null || d.isEmpty()) {
	 * return null; } return d.get(lookup); }
	 */

	public AbbreviationBlockette add(AbbreviationBlockette a) throws SeedException {
		if (log.isDebugEnabled()) {
			log.debug("Adding {} to abbreviation record.", a.getType());
		}
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

	/*
	 * public boolean addAll(Collection<AbbreviationBlockette> c) throws
	 * SeedException { int size = size(); for (AbbreviationBlockette a : c) {
	 * add(a); } return size != size(); }
	 */

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
		stream.writeControl(this.blockettes());
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
			ControlBlocketteIterator<AbbreviationBlockette> it = new ControlBlocketteIterator<AbbreviationBlockette>(8,
					bytes);
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

		AbbreviationBlockette findByValue(AbbreviationBlockette b) {
			for (AbbreviationBlockette a : map.values()) {
				if (a.equals(b)) {
					return a;
				}
			}
			return null;
		}

		AbbreviationBlockette add(AbbreviationBlockette b) throws SeedException {
			if (b == null) {
				return null;
			}
			int lookup = b.getLookupKey();
			if (lookup > 0) {
				if (lookup > counter.max) {
					throw new SeedException("Assigned lookup {} is larger than the maximum [{}] allowed by Seed, ",
							lookup, counter.max);
				}
				if (map.get(lookup) != null) {
					throw new SeedException("Cannot add dictionary blockette with lookup {}, it already exist.",
							lookup);
				}
			}

			if (lookup > 0) {
				AbbreviationBlockette old=map.get(lookup);
				if (old != null) {
					b=old;
				}else {
					map.put(b.getLookupKey(), b);
				}
			} else {
				AbbreviationBlockette a = findByValue(b);
				if (a == null) {
					lookup = counter.increment();
					b.setLookupKey(lookup);
					map.put(b.getLookupKey(), b);
				} else {
					b=a;
				}
			}

			if (log.isDebugEnabled()) {
				log.debug("B0{} has been added with lookupKey={}", b.getType(), b.getLookupKey());
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
