package edu.iris.seed.record;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.iris.seed.SeedException;
import edu.iris.seed.SeedOutputStream;
import edu.iris.seed.abbreviation.AbbreviationBlockette;
import edu.iris.seed.record.Header.Type;
import edu.iris.seed.station.ResponseDictionaryBlockette;

public class AbbreviationRecord extends SeedRecord<AbbreviationBlockette> {

	private Map<Integer, Dictionary> dictionaries = new HashMap<>();
	private Map<String, ResponseDictionaryBlockette> responseDictionary = new HashMap<>();

	private int numberOfBlockettes;

	public AbbreviationRecord() {
		this(0, false);
	}

	public AbbreviationRecord(int sequence, boolean continuation) {
		this(ControlHeader.Builder.newInstance().build(sequence, Type.A, continuation));
	}

	public AbbreviationRecord(ControlHeader header) {
		super(header);
	}

	@Override
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

	public List<AbbreviationBlockette> getAll() {
		List<AbbreviationBlockette> l = new ArrayList<>();
		for (Entry<Integer, AbbreviationRecord.Dictionary> e : dictionaries.entrySet()) {
			Dictionary d = e.getValue();
			if (d != null) {
				l.addAll(d.getAll());
			}
		}
		return l;

	}

	@Override
	public int getNumberOfBlockettes() {
		return this.numberOfBlockettes;
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

		public AbbreviationRecord build(byte[] bytes) throws SeedException {
			AbbreviationRecord record = new AbbreviationRecord(ControlHeader.Builder.newInstance().build(bytes));
			record.setBytes(bytes);
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

		AbbreviationBlockette add(AbbreviationBlockette a) throws SeedException {
			if (map.containsValue(a)) {
				for (AbbreviationBlockette ab : map.values()) {
					if (ab.equals(a)) {
						return ab;
					}
				}
			} else {
				int lookup = counter.increment();
				a.setLookupKey(lookup);
				map.put(lookup, a);
			}
			return a;
		}

		AbbreviationBlockette get(int id) {
			return map.get(id);
		}

		public List<AbbreviationBlockette> getAll() {
			return new ArrayList<>(map.values());
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
