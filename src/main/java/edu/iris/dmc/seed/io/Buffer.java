package edu.iris.dmc.seed.io;

import java.util.Arrays;

import edu.iris.dmc.seed.SeedException;
import edu.iris.dmc.seed.BlocketteFormatter;

public class Buffer {

	private byte[] bytes;
	private int offset;
	private int sequence;
	private char type;

	public Buffer(int size) {
		bytes = new byte[size];
	}

	public void add(byte[] buffer) throws SeedException {
		for (int i = 0; i < buffer.length; i++) {
			if (offset == 0 || offset >= this.bytes.length) {
				flush();
				this.sequence++;
				byte[] s = (BlocketteFormatter.format(this.sequence, 6) + "" + type + '*').getBytes();
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

}
