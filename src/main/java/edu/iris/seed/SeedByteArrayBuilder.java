package edu.iris.seed;

import java.nio.charset.StandardCharsets;

public class SeedByteArrayBuilder {

	private byte[] buffer;

	private int position;

	public SeedByteArrayBuilder(int length) {
		// buffer = ByteBuffer.allocate(length).order(ByteOrder.BIG_ENDIAN);
		buffer = new byte[length];
	}

	public int position() {
		return position;
	}

	public SeedByteArrayBuilder append(byte b) {
		buffer[position++] = b;
		return this;
	}

	public SeedByteArrayBuilder append(byte[] array) {
		for (byte b : array) {
			append(b);
		}
		return this;
	}

	public SeedByteArrayBuilder appendSequence(int sequence) {
		byte[] bytes = String.format("%06d", sequence).getBytes(StandardCharsets.US_ASCII);

		for (int i = 0; i < bytes.length; i++) {
			append(bytes[i]);
		}
		return this;
	}

	public SeedByteArrayBuilder appendChar(char kar) {
		append((byte) kar);
		return this;
	}

	public SeedByteArrayBuilder appendAscii(String s, int length) throws SeedException {
		if (s == null || s.length() > length) {
			throw new SeedException("Invalid text " + s + " [" + length + "]");
		}
		byte[] bytes = String.format("%-" + length + "s", s).getBytes(StandardCharsets.US_ASCII);
		return append(bytes);
	}

	public SeedByteArrayBuilder append(String text, int length) {
		byte[] bytes = text.getBytes(StandardCharsets.US_ASCII);

		int i = 0;
		for (; i < length || i < bytes.length; i++) {
			appendU8(bytes[i]);
		}
		while (i < length) {
			position++;
			i++;
		}
		return this;
	}

	public SeedByteArrayBuilder appendU(byte[] bytes) {
		for (byte b : bytes) {
			appendU8(b);
		}
		return this;
	}

	public SeedByteArrayBuilder append(BTime time) {
		appendU16((short) time.getYear());
		appendU16((short) time.getDayOfYear());
		appendU8((byte) time.getHour());
		appendU8((byte) time.getMinute());
		appendU8((byte) time.getSecond());
		appendU8((byte) 0);
		appendU16((short) time.getTenthMilliSecond());
		return this;
	}

	public SeedByteArrayBuilder append8(byte b) {
		append(b);
		return this;
	}

	public SeedByteArrayBuilder appendU8(int b) {
		append((byte) b);
		return this;
	}
	
	public SeedByteArrayBuilder appendU8(byte b) {
		append((byte) b);
		return this;
	}

	public SeedByteArrayBuilder append16(int src) {
		append((byte) (src >>> 8));
		append((byte) (src & 0xff));
		return this;
	}

	public SeedByteArrayBuilder appendU16(int src) {
		short us = (short) (src & 0xffff);
		append((byte) (us >>> 8));
		append((byte) (us & 0xff));
		return this;
	}

	public SeedByteArrayBuilder appendLong(long lng) {
		append((byte) lng);
		append((byte) (lng >> 8));
		append((byte) (lng >> 16));
		append((byte) (lng >> 24));
		append((byte) (lng >> 32));
		append((byte) (lng >> 40));
		append((byte) (lng >> 48));
		append((byte) (lng >> 56));
		return this;
	}

	public SeedByteArrayBuilder appendFloat(float f) {
		int bits = Float.floatToIntBits(f);
		append((byte) (bits & 0xff));
		append((byte) ((bits >> 8) & 0xff));
		append((byte) ((bits >> 16) & 0xff));
		append((byte) ((bits >> 24) & 0xff));
		return this;
	}

	public byte[] toBytes() {
		return buffer;
	}
}
