package edu.iris.seed.io;

import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.iris.seed.Blockette;
import edu.iris.seed.IncompleteBlockette;
import edu.iris.seed.SeedBlockette;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedInputStream;
import edu.iris.seed.Identifier.IdentifierBlockette;
import edu.iris.seed.abbreviation.AbbreviationBlockette;
import edu.iris.seed.station.StationBlockette;
import edu.iris.seed.timespan.TimeSpanBlockette;

public class SeedBlocketteIterator implements Iterator<Blockette>, AutoCloseable {

	private SeedInputStream sri;
	private Iterator<? extends Blockette> sbi;
	private Blockette cachedBlockette;
	private boolean finished;
	private boolean relax;

	public SeedBlocketteIterator(SeedInputStream inputStream) {
		this(inputStream, false);
	}

	public SeedBlocketteIterator(SeedInputStream inputStream, boolean relax) {
		this.sri = inputStream;
		this.relax = relax;
	}

	private int index = 0;

	@Override
	public boolean hasNext() {
		try {
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
						byte[] bytes = sri.read();
						if (bytes == null) {
							finished = true;
							return false;
						} else {
							index += bytes.length;
							sbi = create(0, bytes, relax);
							return hasNext();
						}
					} else {
						if (blockette instanceof IncompleteBlockette) {
							IncompleteBlockette incompleteBlockette = (IncompleteBlockette) blockette;
							while (incompleteBlockette.numberOfRequiredBytesToComplete() > 0) {
								byte[] bytes = sri.read();
								if (bytes == null) {
									throw new RuntimeException(
											"Error completing blockette of type " + incompleteBlockette.getType()
													+ " received " + incompleteBlockette.getBytes().length
													+ ",expected " + incompleteBlockette.getExpected()
													+ ", expected a new record but was null. Index " + index);
								} else if (!isContinuation(bytes)) {
									throw new RuntimeException(
											"Expected a continuation record but received a new record.");
								} else {
									index += bytes.length;

									int numberOfRequiredBytes = incompleteBlockette.numberOfRequiredBytesToComplete();
									numberOfRequiredBytes = numberOfRequiredBytes < bytes.length - 8
											? numberOfRequiredBytes
											: bytes.length - 8;
									byte[] required = new byte[numberOfRequiredBytes];
									System.arraycopy(bytes, 8, required, 0, numberOfRequiredBytes);
									incompleteBlockette.append(required);
									if (numberOfRequiredBytes + 8 < bytes.length) {
										sbi = create(numberOfRequiredBytes + 8, bytes, relax);
									}
								}
							}
							try {
								blockette = SeedBlockette.builder(incompleteBlockette.getType())
										.fromBytes(incompleteBlockette.getBytes()).build();
							} catch (SeedException e) {
								throw new RuntimeException(e);
							}
						}
						cachedBlockette = blockette;
						return true;
					}
				} else {
					byte[] bytes = sri.read();
					if (bytes == null) {
						finished = true;
						return false;
					} else {
						sbi = create(0, bytes, relax);
						return hasNext();
					}
				}
			}
		} catch (IOException | SeedException e) {
			throw new RuntimeException(e);
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

	private Iterator<? extends Blockette> nextBlocketteIterator() throws SeedException, IOException {
		if (this.sri == null) {
			return null;
		}
		final byte[] bytes = this.sri.read();
		if (bytes == null) {
			return null;
		}
		return create(0, bytes, relax);
	}

	private boolean isContinuation(byte[] bytes) {
		return ((char) bytes[7]) == '*';
	}

	@Override
	public void close() {
		if (sri != null) {
			try {
				sri.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	private Iterator<? extends Blockette> create(int i, byte[] bytes, boolean relax) throws SeedException {
		char type = (char) bytes[6];
		int index = i > 8 ? i : 8;

		switch (type) {
		case 'V':
			return new BlocketteIterator<IdentifierBlockette>(index, bytes, false);
		case 'A':
			return new BlocketteIterator<AbbreviationBlockette>(index, bytes, relax);
		case 'S':
			return new BlocketteIterator<StationBlockette>(index, bytes, relax);
		case 'T':
			return new BlocketteIterator<TimeSpanBlockette>(index, bytes, false);
		case 'D':
		case 'R':
		case 'M':
		case 'Q':
			return new DataBlocketteIterator(48, bytes);
		case 'E':
		case ' ':
			return null;
		default:
			return null;
		}
	}
}
