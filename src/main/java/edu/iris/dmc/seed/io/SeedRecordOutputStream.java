package edu.iris.dmc.seed.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.Record;
import edu.iris.dmc.seed.RecordFactory;
import edu.iris.dmc.seed.SeedException;
import edu.iris.dmc.seed.control.dictionary.DictionaryBlockette;
import edu.iris.dmc.seed.control.index.IndexBlockette;
import edu.iris.dmc.seed.control.station.B050;
import edu.iris.dmc.seed.control.station.ResponseBlockette;
import edu.iris.dmc.seed.control.station.StationBlockette;

public class SeedRecordOutputStream implements Closeable, Flushable {
	private final Logger logger = Logger.getLogger(SeedRecordOutputStream.class.getName());
	private int sequence = 1;
	private OutputStream out;
	private Record record;

	private int size;

	public SeedRecordOutputStream(OutputStream out) throws SeedException {
		this(out, 4096);
	}

	public SeedRecordOutputStream(OutputStream out, int size) throws SeedException {
		Objects.requireNonNull(out, "OutputStream cannot be null.");
		this.out = out;
		if (size <= 0) {
			throw new IllegalArgumentException("Buffer size <= 0");
		}
		this.size = size;
		// this.record = RecordFactory.create(size, sequence, 'A', false);
	}

	public synchronized int append(Blockette b) throws SeedException, IOException {

		if (b instanceof IndexBlockette) {
			if (this.record == null) {
				record = RecordFactory.create(size, sequence++, 'V', false);
			} else {
				if (this.record.getType() != 'V') {
					this.write(this.record);
					record = RecordFactory.create(size, sequence++, 'V', false);
				}
			}
		} else if (b instanceof DictionaryBlockette) {
			if (this.record == null) {
				record = RecordFactory.create(size, sequence++, 'A', false);
			} else {
				if (this.record.getType() != 'A') {
					this.write(this.record);
					record = RecordFactory.create(size, sequence++, 'A', false);
				}
			}
		} else if (b instanceof StationBlockette) {
			if (this.record == null) {
				record = RecordFactory.create(size, sequence++, 'S', false);
			} else {
				if (b instanceof B050 || this.record.getType() != 'S') {
					this.write(this.record);
					record = RecordFactory.create(size, sequence++, 'S', false);
				}
			}
		} else if (b instanceof ResponseBlockette) {
			if (this.record == null) {
				record = RecordFactory.create(size, sequence++, 'S', false);
			} else {
				if (this.record.getType() != 'S') {
					this.write(this.record);
					record = RecordFactory.create(size, sequence++, 'S', false);
				}
			}
		} else {
			throw new IllegalArgumentException("Unsupported blockette type " + b.getType());
		}
		return write(b);
	}

	/**
	 * Append a blockette to the buffer, B050s start a new sequence
	 * 
	 * @param b
	 * @return the sequence of the record this blockette was added under
	 * @throws SeedException
	 * @throws IOException
	 */
	public synchronized int write(Blockette b) throws SeedException, IOException {
		String seed = b.toSeedString();
		logger.log(Level.INFO, "Writing " + seed);
		byte[] bytes = seed.getBytes();

		while (true) {
			bytes = this.record.add(bytes);
			if (bytes == null || bytes.length == 0) {
				break;
			} else {
				// output this record
				this.write(this.record);
				sequence++;
				this.record = RecordFactory.create(size, sequence, record.getType(), true);
			}
		}
		return sequence;
	}

	private void write(Record r) throws IOException, SeedException {
		byte[] bytes = r.getBytes();
		Arrays.fill(bytes, bytes.length - 1 - r.getAvailableBytes(), bytes.length, (byte) 32);
		this.out.write(bytes);
		flush();
	}

	public void write(byte[] bytes) throws IOException {
		flushRecord();
		this.out.write(bytes);
		flush();
	}

	public int getRecordSize() {
		return this.size;
	}

	private void flushRecord() throws IOException {
		try {
			if (this.record != null) {
				byte[] bytes;
				bytes = this.record.getBytes();

				Arrays.fill(bytes, bytes.length - 1 - this.record.getAvailableBytes(), bytes.length,
						(byte) 32);
				this.out.write(bytes);
				flush();
				this.record = null;
			}
		} catch (SeedException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void flush() throws IOException {
		if (out != null) {
			out.flush();
		}

	}

	@Override
	public void close() throws IOException {
		if (out != null) {
			out.close();
		}
	}
}
