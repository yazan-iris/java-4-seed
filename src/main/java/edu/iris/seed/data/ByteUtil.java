package edu.iris.seed.data;

import java.util.Arrays;

public class ByteUtil {

	public static float fourBytesToFloat(byte[] bytes, int from, int length) {
		return fourBytesToFloat(Arrays.copyOfRange(bytes, from, from + length));
	}

	public static float fourBytesToFloat(byte[] bytes) {
		int i = bytes[0] << 24 | (bytes[1] & 0xFF) << 16 | (bytes[2] & 0xFF) << 8 | (bytes[3] & 0xFF);
		return Float.intBitsToFloat(i);
	}

	public static int readUnsignedShort(byte[] buf, int begin, int length) {
		byte[] b = new byte[length];
		for (int i = 0; i < length; i++) {
			b[i] = buf[begin + i];
		}
		return ((b[0] << 8) & 0x0000ff00) | (b[1] & 0x000000ff);
	}

	public static int readUnsignedByte(byte b) {
		return b & 0xFF;
	}

	public static int readInt(byte[] buf, int begin, int length) {
		if (length < 4) {
			// throw new EOFException();
		}

		byte[] b = new byte[length];
		for (int i = 0; i < length; i++) {
			b[i] = buf[begin + i];
		}

		int i = b[0] << 24 | (b[1] & 0xFF) << 16 | (b[2] & 0xFF) << 8 | (b[3] & 0xFF);
		return i;
	}
}
