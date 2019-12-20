package edu.iris.dmc.seed.blockette.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Queue;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.BlocketteFactory;
import edu.iris.dmc.seed.IncompleteBlockette;
import edu.iris.dmc.seed.Record;
import edu.iris.dmc.seed.SeedException;
import edu.iris.dmc.seed.io.RecordInputStream;
import edu.iris.dmc.seed.record.EmptyRecord;

/**
 * Iterate the inputstream for records, returning blockette in the order they
 * are read.
 * 
 * @author Suleiman
 *
 */
public class BlocketteItrator implements Iterator<Blockette>, Closeable {

	private RecordInputStream recordInputStream;
	private Record record;

	private Queue<Blockette> q = new LinkedList<>();

	public BlocketteItrator(RecordInputStream recordInputStream) {
		this.recordInputStream = recordInputStream;
	}

	private boolean end = false;

	@Override
	public boolean hasNext() {
		if (!end && q.isEmpty()) {
			try {
				if (this.recordInputStream == null) {
					return false;
				}
				this.record = this.recordInputStream.next();
				if (this.record == null || this.record instanceof EmptyRecord) {
					end = true;
					return false;
				}
				Blockette blockette = null;
				while ((blockette = this.record.next()) != null) {
					if (blockette instanceof IncompleteBlockette) {
						blockette = completeBlockette((IncompleteBlockette) blockette);
					}
					q.add(blockette);
				}
			} catch (IOException | SeedException e) {
				throw new RuntimeException(e);
			}
		}
		return !q.isEmpty();
	}

	private Blockette completeBlockette(IncompleteBlockette b) throws IOException, SeedException {
		while (b.numberOfRequiredBytesToComplete() > 0) {
			this.record = this.recordInputStream.next();
			if (record == null || !record.isContinuation()) {
				throw new SeedException("Expected a continuation record but did not find one!");
			}
			byte[] bytes = this.record.get(b.numberOfRequiredBytesToComplete());
			b.append(bytes);
		}
		return BlocketteFactory.create(b.getBytes());
	}

	@Override
	public Blockette next() {
		if (q.isEmpty()) {
			throw new NoSuchElementException();
		}
		return q.poll();
	}

	@Override
	public void close() throws IOException {
		if (recordInputStream != null) {
			recordInputStream.close();
		}

	}

}
