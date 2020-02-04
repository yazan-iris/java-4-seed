package edu.iris.seed.io;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.ControlBlockette;
import edu.iris.seed.IncompleteBlockette;
import edu.iris.seed.SeedBlockette;
import edu.iris.seed.SeedException;
import edu.iris.seed.lang.SeedStrings;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ControlBlocketteIterator<T extends ControlBlockette> implements Iterator<T> {
	private T cachedBlockette;
	private boolean finished;

	private int index;
	private byte[] bytes;

	public ControlBlocketteIterator(int index, byte[] bytes) throws SeedException {
		if (bytes.length < 7) {
			throw new SeedException("byte array is too short, expected at least 7 but received {}", bytes.length);
		}
		this.index = index;
		this.bytes = bytes;
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
					final T blockette = read();
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
	public T next() {
		return nextBlockette();
	}

	public T nextBlockette() {
		if (!hasNext()) {
			throw new NoSuchElementException("No more blockettes");
		}
		final T currentBlockette = cachedBlockette;
		cachedBlockette = null;
		return currentBlockette;
	}

	private T read() throws SeedException {
		if (this.bytes == null) {
			return null;
		}

		int recordSize = bytes.length;
		if (index >= recordSize - 1) {
			return null;
		}
		int type = -1;
		int length = -1;

		if (index + 7 > recordSize || "".equals(new String(bytes, index + 3, 4).trim())) {
			return null;
		}
		type = SeedStrings.parseInt(bytes, index, 3);
		length = Integer.parseInt(new String(bytes, index + 3, 4).trim());

		if (length + index <= bytes.length) {
			byte[] b = new byte[length];
			System.arraycopy(bytes, index, b, 0, length);
			index += length;
			BlocketteBuilder<? extends ControlBlockette> builder = SeedBlockette.controlBlocketteBuilder(type);
			@SuppressWarnings("unchecked")
			T blockette = (T) builder.fromBytes(b).build();
			return blockette;
		} else {
			// create incomplete blockette
			byte[] bb = new byte[bytes.length - index];
			System.arraycopy(bytes, index, bb, 0, bb.length);
			@SuppressWarnings("unchecked")
			T b = (T) new IncompleteBlockette(type, bb, length);
			index += bb.length;
			return b;
		}
	}

	public byte[] skip(int length) throws SeedException {
		if (this.bytes == null) {
			throw new SeedException("Expected a valid record but was null");
		}
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
