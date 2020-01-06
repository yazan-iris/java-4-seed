package edu.iris.dmc.seed.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.iris.dmc.io.SeedFormatter;
import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.Record;
import edu.iris.dmc.seed.SeedException;
import edu.iris.dmc.seed.control.station.B050;
import edu.iris.dmc.seed.control.station.OverFlowBlockette;

public class BlocketteOutputStream implements Closeable {

	private static final Logger LOG = Logger.getLogger(BlocketteOutputStream.class.getName());

	protected char currentType = ' ';
	protected byte[] buf;

	protected int sequence;
	/**
	 * The total number of bytes inside the byte array {@code buf}.
	 */
	protected int count;

	private OutputStream out;

	public BlocketteOutputStream(OutputStream out) {
		this(out, 4096);
	}

	public BlocketteOutputStream(OutputStream out, int size) {
		if (size < 256 || size > 32768) {
			throw new IllegalArgumentException(
					"Logical record lengths can be from 256 bytes to 32,768 bytes. 4096 bytes is preferred.");
		}
		if (!isPowerOf2(size)) {
			throw new IllegalArgumentException("Volume logical record length, expressed as a power of 2.");
		}
		this.out = out;
		buf = new byte[size];
	}

	public synchronized int write(Blockette b) throws IOException {
		Objects.requireNonNull(b, "blockette cannot be null");
		char type = checkType(b);
		int theSequence = sequence;
		if (availableBytesInBuffer() < 10 || count == 0 || b instanceof B050 || type != currentType) {
			startNewRecord(type, false);
		}
		try {
			String seedString = b.toSeedString();
			byte[] bytes = seedString.getBytes();
			boolean canFit = canFit(bytes);
			if (!canFit) {
				// LOG.log(Level.INFO, "blockette " + b.getType() + " does not
				// fit.");

				if (shouldSplitBlockette(b.getType(), bytes)) {
					int offset = 0;
					int remaining = bytes.length;
					int l = 0;
					theSequence = sequence;
					while (remaining > 0) {
						if (availableBytesInBuffer() == 0) {
							startNewRecord(type, true);
						}
						l = (remaining < availableBytesInBuffer()) ? remaining : availableBytesInBuffer();
						this.write(bytes, offset, l);
						offset = offset + l;
						remaining = remaining - l;
					}
					return theSequence;
				} else {
					startNewRecord(type, false);
				}
			}
			theSequence = sequence;
			this.write(bytes, 0, bytes.length);
			return theSequence;
		} catch (SeedException e) {
			throw new IOException(e);
		}
	}

	public synchronized void write(Record r) throws IOException {
		try {
			writeRaw(r.getBytes());
		} catch (SeedException e) {
			throw new IOException(e);
		}
	}

	public synchronized void writeRaw(byte[] bytes) throws IOException {
		flush();
		this.write(bytes, 0, bytes.length);
	}

	private synchronized void write(byte[] buffer, int offset, int length) throws IOException {
		checkNotClosed();
		if (buffer == null) {
			throw new NullPointerException("buffer == null");
		}
		byte[] internalBuffer = buf;
		if (length >= internalBuffer.length) {
			flushInternal();
			out.write(buffer, offset, length);
			return;
		}
		checkOffsetAndCount(buffer.length, offset, length);
		// flush the internal buffer first if we have not enough space left
		if (length > (internalBuffer.length - count)) {
			flushInternal();
		}
		System.arraycopy(buffer, offset, internalBuffer, count, length);
		count += length;
	}

	private void startNewRecord(char type, boolean continuation) throws IOException {
		// LOG.log(Level.INFO, "Starting a new " + (continuation ?
		// "continuation" : "") + " record of type " + type);
		if (count > 0) {
			flush();
		}

		try {
			byte[] sequence = getNextSequenceBytes(type, continuation);
			this.write(sequence, 0, sequence.length);
			currentType = type;
		} catch (SeedException e) {
			throw new IOException(e);
		}

	}

	public int getSequence() {
		return this.sequence;
	}

	protected byte[] getNextSequenceBytes(char type, boolean continuation) throws SeedException {
		sequence += 1;
		StringBuilder sb = new StringBuilder(SeedFormatter.format(this.sequence, 6));
		sb.append(type);
		if (continuation) {
			sb.append('*');
		} else {
			sb.append(' ');
		}

		return sb.toString().getBytes();
	}

	public synchronized void flush() throws IOException {
		checkNotClosed();
		flushInternal();
		out.flush();
	}

	/**
	 * Flushes only internal buffer.
	 */
	private void flushInternal() throws IOException {
		if (count > 0) {
			if (count < buf.length) {
				Arrays.fill(buf, count, buf.length, (byte) 32);
			}
			out.write(buf, 0, buf.length);
			count = 0;
		}
	}

	private char checkType(Blockette b) {
		if (b.getType() <= 12) {
			return 'V';
		} else if (b.getType() > 12 && b.getType() < 50) {
			return 'A';
		} else {
			return 'S';
		}
	}

	private int availableBytesInBuffer() {
		return buf.length - count;
	}

	private void checkOffsetAndCount(long arrayLength, long offset, long count) {
		if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	private void checkNotClosed() throws IOException {
		if (buf == null) {
			throw new IOException("BufferedOutputStream is closed");
		}
	}

	private boolean canFit(byte[] bytes) {
		if (buf.length - count >= bytes.length) {
			return true;
		}
		return false;
	}

	private boolean shouldSplitBlockette(int type, byte[] bytes) {
		if (bytes.length > (buf.length - 8)) {
			return true;
		}

		if (type >= 30 && type <= 40) {
			return false;
		}
		return availableBytesInBuffer() >= 7;
	}

	private boolean isPowerOf2(final int n) {
		if (n <= 0) {
			return false;
		}
		return (n & (n - 1)) == 0;
	}

	@Override
	public void close() throws IOException {
		if (out != null) {
			flush();
			// empty buffer
			out.flush();
			out.close();
			buf = null;
		}
	}
}
