package edu.iris.seed.io;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.iris.seed.Blockette;
import edu.iris.seed.Record;
import edu.iris.seed.SeedBlockette;
import edu.iris.seed.SeedException;
import edu.iris.seed.lang.SeedStrings;
import edu.iris.seed.station.IncompleteBlockette;

public class SeedRecordBlocketteIterator implements Iterator<Blockette> {

	private Record<? extends Blockette> record;
	private Blockette cachedBlockette;
	private boolean finished;

	private int index;

	public SeedRecordBlocketteIterator(Record<? extends Blockette> record) {
		this.record = record;
		this.index = 8;
	}

	@Override
	public boolean hasNext() {
		if (cachedBlockette != null) {
			return true;
		} else if (finished) {
			return false;
		} else {
			try {
				while (true) {
					final Blockette blockette = read();
					if (blockette == null) {
						finished = true;
						return false;
					} else {
						cachedBlockette = blockette;
						return true;
					}
				}
			} catch (final SeedException ioe) {
				throw new IllegalStateException(ioe);
			}
		}
	}

	@Override
	public Blockette next() {
		return nextBlockette();
	}

	public Blockette nextBlockette() {
		if (!hasNext()) {
			throw new NoSuchElementException("No more blockettes");
		}
		final Blockette currentBlockette = cachedBlockette;
		cachedBlockette = null;
		return currentBlockette;
	}

	private Blockette read() throws SeedException {
		if (this.record == null || this.record.getBytes() == null) {
			return null;
		}
		byte[] bytes = record.getBytes();
		int recordSize = bytes.length;
		if (bytes == null || index >= recordSize - 1) {
			return null;
		}
		if (index + 7 > recordSize || "".equals(new String(bytes, index + 3, 4).trim())) {
			return null;
		}

		int type = SeedStrings.parseInt(bytes, index, 3);
		int length = Integer.parseInt(new String(bytes, index + 3, 4).trim());
		if (!SeedBlockette.controlMap.containsKey(type)) {
			// throw new SeedException("Error looking up blockette of type {}, invalid
			// blockette type.",type);
		}
		if (length + index <= bytes.length) {
			byte[] b = new byte[length];
			System.arraycopy(bytes, index, b, 0, length);
			index += length;
			return SeedBlockette.Builder.newInstance().build(type, b);
		} else {
			// create incomplete blockette
			byte[] bb = new byte[bytes.length - index];
			System.arraycopy(bytes, index, bb, 0, bb.length);

			IncompleteBlockette b = new IncompleteBlockette(type, bb, length);
			index += bb.length;
			return b;
		}
	}

	public byte[] skip(int length) throws SeedException {
		if (record == null || record.getBytes() == null) {
			throw new SeedException("Expected a valid record but was null");
		}
		byte[] bytes = record.getBytes();
		int available = bytes.length - index;
		int l = length;
		if (length > available) {
			l = available;
		}
		byte[] newBytes = new byte[l];
		System.arraycopy(bytes, index, newBytes, 0, l);
		index += length;
		return newBytes;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove unsupported on BlocketteIterator");
	}
}
