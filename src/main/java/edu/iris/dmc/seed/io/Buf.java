package edu.iris.dmc.seed.io;

public class Buf {

	private byte[] bytes;
	private int count;

	private Buf(int size) {
		this.bytes = new byte[size];
	}

	public void append(byte[] b) {
		append(b, 0, b.length);
	}

	public void append(byte[] b, int offset, int numberOfBytes) {
		System.arraycopy(b, offset, bytes, count, numberOfBytes);
		count += b.length;
	}

	public void reset(byte[] bytes) {
		count = 0;
		append(bytes);
	}

	public int length() {
		return this.bytes.length;
	}

	public int available() {
		return bytes.length - count;
	}

	// public byte[] getBytes() {
	// return this.bytes;
	// }

	public byte[] flush() {
		for (int i = count; i < bytes.length; i++) {
			bytes[i] = ' ';
		}
		count = 0;
		return bytes;
	}

	public static Buf of(int size) {
		return new Buf(size);
	}
}
