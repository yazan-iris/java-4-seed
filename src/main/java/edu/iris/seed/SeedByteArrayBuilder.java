package edu.iris.seed;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;

public class SeedByteArrayBuilder {

	private ByteBuffer buffer;

	public SeedByteArrayBuilder(int length) {
		buffer = ByteBuffer.allocate(length).order(ByteOrder.BIG_ENDIAN);
	}

	public SeedByteArrayBuilder append(String text, int length) {
		byte[] bytes = text.getBytes(StandardCharsets.US_ASCII);
		int i = 0;
		for (; i < length || i < bytes.length; i++) {
			appendU(bytes[i]);
		}
		while (i < length) {
			appendU((byte) 0);
			i++;
		}
		return this;
	}

	public SeedByteArrayBuilder append(byte[] bytes) {
		for (byte b : bytes) {
			appendU(b);
		}
		return this;
	}

	public SeedByteArrayBuilder append(BTime time) {
		appendU16((short) time.getYear()).appendU16((short) time.getDayOfYear());
		appendU((byte) time.getHour()).appendU((byte) time.getMinute()).appendU((byte) time.getSecond())
				.append16((short) time.getTenthMilliSecond());

		return this;
	}

	public SeedByteArrayBuilder append(byte b) {
		buffer.put(b);
		return this;
	}

	public SeedByteArrayBuilder appendU(byte b) {
		buffer.put((byte) (b & 0xff));
		return this;
	}

	public SeedByteArrayBuilder append16(short s) {
		buffer.putShort(s);
		return this;
	}

	public SeedByteArrayBuilder appendU16(short s) {
		buffer.putShort((short) (s & 0xffff));
		return this;
	}

	public SeedByteArrayBuilder appendU(long l) {
		buffer.putLong((long) (l & 0x7fffffffffffffffL));
		return this;
	}

	public SeedByteArrayBuilder append(int index, float f) {
		buffer.putFloat(4, f);
		return this;
	}

	public SeedByteArrayBuilder append(float f) {
		buffer.putFloat(4, f);
		return this;
	}

	public SeedByteArrayBuilder appendU(byte[] bytes) {
		buffer.put(bytes);
		return this;
	}

	public byte[] toBytes() {
		return buffer.array();
	}
}
