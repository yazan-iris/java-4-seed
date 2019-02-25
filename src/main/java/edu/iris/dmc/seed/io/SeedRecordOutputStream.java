package edu.iris.dmc.seed.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Objects;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.Record;
import edu.iris.dmc.seed.RecordFactory;
import edu.iris.dmc.seed.SeedException;
import edu.iris.dmc.seed.control.dictionary.DictionaryBlockette;
import edu.iris.dmc.seed.control.index.IndexBlockette;
import edu.iris.dmc.seed.control.station.B050;

public class SeedRecordOutputStream implements Closeable, Flushable {

	private int sequence;
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

	/**
	 * Append a blockette to the buffer, B050s start a new sequence
	 * 
	 * @param b
	 * @return the sequence of the record this blockette was added under
	 * @throws SeedException
	 * @throws IOException
	 */
	public synchronized int append(Blockette b) throws SeedException, IOException {

		if (b instanceof B050) {
			if (this.record != null) {
				// output this record
				this.out.write(this.record.getBytes());
				this.write(this.record);
			}
			// always start a new record when B050
			sequence++;
			record = RecordFactory.create(size, sequence, 'S', false);
			// record.add(b.toSeedString().getBytes());
		} else {
			if (this.record == null) {
				if (b instanceof DictionaryBlockette) {
					record = RecordFactory.create(size, sequence, 'A', false);
				} else if (b instanceof IndexBlockette) {
					record = RecordFactory.create(size, sequence, 'V', false);
				} else {
					throw new SeedException("Expected record but record was null when appending blockette:"+b.getType());
				}
			}
		}
		byte[] bytes = b.toSeedString().getBytes();
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
		this.out.write(bytes);
		flush();
	}

	public int getRecordSize() {
		return this.size;
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
