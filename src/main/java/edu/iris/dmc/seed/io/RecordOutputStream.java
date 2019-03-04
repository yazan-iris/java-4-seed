package edu.iris.dmc.seed.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RecordOutputStream extends FilterOutputStream {

	protected byte[] buf;

	protected int sequence;
	/**
	 * The total number of bytes inside the byte array {@code buf}.
	 */
	protected int count;

	public RecordOutputStream(OutputStream out) {
		this(out, 4096);
	}

	public RecordOutputStream(OutputStream out, int size) {
		super(out);
		if (size < 256 || size > 32768) {
			throw new IllegalArgumentException(
					"Logical record lengths can be from 256 bytes to 32,768 bytes. 4096 bytes is preferred.");
		}
		buf = new byte[size];
	}

	/**
	 * Flushes this stream to ensure all pending data is written out to the
	 * target stream. In addition, the target stream is flushed.
	 *
	 * @throws IOException
	 *             if an error occurs attempting to flush this stream.
	 */
	@Override
	public synchronized void flush() throws IOException {
		checkNotClosed();
		flushInternal();
		out.flush();
	}

	private void checkNotClosed() throws IOException {
		if (buf == null) {
			throw new IOException("BufferedOutputStream is closed");
		}
	}

	/**
	 * Writes {@code count} bytes from the byte array {@code buffer} starting at
	 * {@code offset} to this stream. If there is room in the buffer to hold the
	 * bytes, they are copied in. If not, the buffered bytes plus the bytes in
	 * {@code buffer} are written to the target stream, the target is flushed,
	 * and the buffer is cleared.
	 *
	 * @param buffer
	 *            the buffer to be written.
	 * @param offset
	 *            the start position in {@code buffer} from where to get bytes.
	 * @param length
	 *            the number of bytes from {@code buffer} to write to this
	 *            stream.
	 * @throws IndexOutOfBoundsException
	 *             if {@code offset < 0} or {@code length < 0}, or if
	 *             {@code offset + length} is greater than the size of
	 *             {@code buffer}.
	 * @throws IOException
	 *             if an error occurs attempting to write to this stream.
	 * @throws NullPointerException
	 *             if {@code buffer} is {@code null}.
	 * @throws ArrayIndexOutOfBoundsException
	 *             If offset or count is outside of bounds.
	 */
	@Override
	public synchronized void write(byte[] buffer, int offset, int length) throws IOException {
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

	@Override
	public synchronized void close() throws IOException {
		if (buf == null) {
			return;
		}
		try {
			super.close();
		} finally {
			buf = null;
		}
	}

	/**
	 * Writes one byte to this stream. Only the low order byte of the integer
	 * {@code oneByte} is written. If there is room in the buffer, the byte is
	 * copied into the buffer and the count incremented. Otherwise, the buffer
	 * plus {@code oneByte} are written to the target stream, the target is
	 * flushed, and the buffer is reset.
	 *
	 * @param oneByte
	 *            the byte to be written.
	 * @throws IOException
	 *             if an error occurs attempting to write to this stream.
	 */
	@Override
	public synchronized void write(int oneByte) throws IOException {
		checkNotClosed();
		if (count == buf.length) {
			out.write(buf, 0, count);
			count = 0;
		}
		buf[count++] = (byte) oneByte;
	}

	/**
	 * Flushes only internal buffer.
	 */
	private void flushInternal() throws IOException {
		if (count > 0) {
			out.write(buf, 0, count);
			count = 0;
		}
	}

	private void checkOffsetAndCount(long arrayLength, long offset, long count) {
		if ((offset | count) < 0 || offset > arrayLength || arrayLength - offset < count) {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

}
