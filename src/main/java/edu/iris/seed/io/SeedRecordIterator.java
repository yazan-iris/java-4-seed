package edu.iris.seed.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.iris.seed.Blockette;
import edu.iris.seed.Record;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedRecordInputStream;

public class SeedRecordIterator implements Iterator<Record<? extends Blockette>>, AutoCloseable {

	private SeedRecordInputStream seedRecordInputStream;

	private Record<? extends Blockette> cachedRecord;
	private boolean finished;

	public SeedRecordIterator(InputStream inputStream) throws IllegalArgumentException {
		if (inputStream == null) {
			throw new IllegalArgumentException("inputStream must not be null");
		}
		this.seedRecordInputStream = new SeedRecordInputStream(inputStream);
	}

	@Override
	public boolean hasNext() {
		if (cachedRecord != null) {
			return true;
		} else if (finished) {
			return false;
		} else {
			try {
				while (true) {
					final Record<? extends Blockette> record = seedRecordInputStream.next();
					if (record == null) {
						finished = true;
						return false;
					} else {
						cachedRecord = record;
						return true;
					}
				}
			} catch (final IOException | SeedException ioe) {
				try {
					seedRecordInputStream.close();
				} catch (IOException e) {
				}
				throw new IllegalStateException(ioe);
			}
		}
	}

	@Override
	public Record<? extends Blockette> next() {
		return nextRecord();
	}

	public Record<? extends Blockette> nextRecord() {
		if (!hasNext()) {
			throw new NoSuchElementException("No more records");
		}
		final Record<? extends Blockette> currentRecord = cachedRecord;
		cachedRecord = null;
		return currentRecord;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove unsupported on RecordIterator");
	}

	@Override
	public void close() throws IOException {
		finished = true;
		cachedRecord = null;
		if (seedRecordInputStream != null) {
			seedRecordInputStream.close();
		}

	}
}
