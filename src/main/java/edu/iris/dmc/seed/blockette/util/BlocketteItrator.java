package edu.iris.dmc.seed.blockette.util;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.BlocketteFactory;
import edu.iris.dmc.seed.IncompleteBlockette;
import edu.iris.dmc.seed.Record;
import edu.iris.dmc.seed.SeedException;
import edu.iris.dmc.seed.builder.Builder;
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
	private Builder<Blockette> builder;
	private Record record;

	private Queue<Blockette> q = new LinkedList<Blockette>();

	public BlocketteItrator(RecordInputStream recordInputStream, Builder<Blockette> builder) {
		this.recordInputStream = recordInputStream;
		this.builder = builder;
	}

	/*
	 * public BlocketteItrator(Record record) { this.record = record; }
	 */

	@Override
	public boolean hasNext() {
		if (q.isEmpty()) {
			try {
				if (this.recordInputStream == null) {
					return false;
				}
				this.record = this.recordInputStream.next();
				if (this.record == null || this.record instanceof EmptyRecord) {
					return false;
				}
				while (true) {
					Blockette blockette = this.record.next();
					
					if (blockette == null) {
						break;
					}
					if (blockette instanceof IncompleteBlockette) {
						blockette = completeBlockette((IncompleteBlockette) blockette);
					}
					
					q.add(blockette);
				}
			} catch (IOException | SeedException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		return !q.isEmpty();
	}

	private Blockette completeBlockette(IncompleteBlockette b) throws IOException, SeedException {
		while (b.remainingBytes() > 0) {
			this.record = this.recordInputStream.next();
			if (!record.isContinuation()) {
				throw new SeedException("Expected a continuation record but did not find one!");
			}
			byte[] bytes = this.record.get(b.remainingBytes());
			b.append(bytes);
		}
		return BlocketteFactory.create(b.getBytes());
	}

	@Override
	public Blockette next() {
		return q.poll();
	}

	@Override
	public void close() throws IOException {
		if (recordInputStream != null) {
			recordInputStream.close();
		}

	}

}
