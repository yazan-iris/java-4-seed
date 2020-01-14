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
}
