package edu.iris.dmc.seed.io;

import java.util.Arrays;

import edu.iris.dmc.io.SeedFormatter;

public class Buffer {

	private byte[] bytes;
	private int offset;
	private int sequence;
	private char type;

	public Buffer(int size) {
		bytes = new byte[size];
	}

	public void add(byte[] buffer) {
		for (int i = 0; i < buffer.length; i++) {
			if (offset == 0 || offset >= this.bytes.length) {
				flush();
				this.sequence++;
				byte[] s = (SeedFormatter.format(this.sequence, 6) + "" + type + '*').getBytes();
				System.arraycopy(s, 0, this.bytes, offset, s.length);
				Arrays.copyOfRange(this.bytes, 0, s.length);
				offset = s.length;
			}
			bytes[offset++] = buffer[i];
		}
	}

	public void flush() {
		this.bytes = new byte[this.bytes.length];
		offset = 0;
	}

	public static void main(String[] args) {
		Buffer b = new Buffer(20);
		b.add("This is a test".getBytes());
		b.flush();
	}
}
