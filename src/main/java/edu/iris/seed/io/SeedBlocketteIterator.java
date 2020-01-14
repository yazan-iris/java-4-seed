package edu.iris.seed.io;

import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.iris.seed.Blockette;
import edu.iris.seed.Record;
import edu.iris.seed.SeedBlockette;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedRecordInputStream;
import edu.iris.seed.record.EmptyRecord;
import edu.iris.seed.station.IncompleteBlockette;

public class SeedBlocketteIterator implements Iterator<Blockette>, AutoCloseable {

	private SeedRecordIterator sri;
	private Iterator<? extends Blockette> sbi;
	private Blockette cachedBlockette;
	private boolean finished;

	public SeedBlocketteIterator(SeedRecordInputStream inputStream) {
		this.sri = new SeedRecordIterator(inputStream);
	}

	private int index = 0;

	@Override
	public boolean hasNext() {
		if (cachedBlockette != null) {
			return true;
		} else if (finished) {
			return false;
		} else {
			if (sbi == null) {
				sbi = nextBlocketteIterator();
				if (sbi == null) {
					finished = true;
					return false;
				}
			}
			if (sbi.hasNext()) {
				Blockette blockette = sbi.next();
				if (blockette == null) {
					if (sri.hasNext()) {
						Record<? extends Blockette> record = sri.next();
						index += record.getBytes().length;
						sbi = new SeedRecordBlocketteIterator(record);
						return hasNext();
					} else {
						finished = true;
						return false;
					}
				} else {
					if (blockette instanceof IncompleteBlockette) {
						IncompleteBlockette incompleteBlockette = (IncompleteBlockette) blockette;
						while (incompleteBlockette.numberOfRequiredBytesToComplete() > 0) {
							Record<? extends Blockette> r = sri.next();
							if (r == null) {
								throw new RuntimeException(
										"Error completing blockette of type " + incompleteBlockette.getType()
												+ " received " + incompleteBlockette.getBytes().length + ",expected "
												+ incompleteBlockette.getExpected()
												+ ", expected a new record but was null. Index " + index);
							} else if (!r.isContinuation()) {
								throw new RuntimeException("Expected a continuation record but received a new record.");
							} else {
								index += r.getBytes().length;
								sbi = new SeedRecordBlocketteIterator(r);
								byte[] bytes;
								try {
									bytes = ((SeedRecordBlocketteIterator) sbi)
											.skip(incompleteBlockette.numberOfRequiredBytesToComplete());
								} catch (SeedException e) {
									throw new RuntimeException("Error completing blockette!");
								}
								incompleteBlockette.append(bytes);
							}
						}
						try {
							blockette = SeedBlockette.Builder.newInstance().build(incompleteBlockette.getBytes());
						} catch (SeedException e) {
							throw new RuntimeException(e);
						}
					}
					cachedBlockette = blockette;
					return true;
				}
			} else {
				if (sri.hasNext()) {
					Record<? extends Blockette> record = sri.next();
					sbi = new SeedRecordBlocketteIterator(record);
					return hasNext();
				} else {
					finished = true;
					return false;
				}
			}
		}
	}

	@Override
	public Blockette next() {
		if (!hasNext()) {
			throw new NoSuchElementException("No more records");
		}
		final Blockette currentBlockette = cachedBlockette;
		cachedBlockette = null;
		return currentBlockette;
	}

	private Iterator<? extends Blockette> nextBlocketteIterator() {
		if (this.sri == null) {
			return null;
		}
		if (this.sri.hasNext()) {
			final Record<? extends Blockette> record = this.sri.next();
			if (record instanceof EmptyRecord) {
				nextBlocketteIterator();
			}
			return new SeedRecordBlocketteIterator(record);
		} else {
			return null;
		}
	}

	@Override
	public void close() throws Exception {
		if (sri != null) {
			try {
				sri.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}
}
