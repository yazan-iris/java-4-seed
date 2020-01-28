package edu.iris.seed;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class ByteBufferReader {

	public static ByteOrder determineOrder() {
		return null;
	}

	public static BTime readBTime(ByteOrder byteOrder, byte[] array, int offset, int length) {
		int year = readUnsignedShort(byteOrder, array, offset);
		// offset += 2;
		int day = readUnsignedShort(byteOrder, array, offset + 2);
		// offset += 2;

		int hour = Byte.toUnsignedInt(array[offset + 4]);
		int minute = Byte.toUnsignedInt(array[offset + 5]);
		int second = Byte.toUnsignedInt(array[offset + 6]);
		// 27 unused
		// offset++;
		int tenthSecond = readUnsignedShort(byteOrder, array, offset + 8);

		return new BTime(year, day, hour, minute, second, tenthSecond);
	}

	public static short readShort(ByteOrder byteOrder, byte[] array, int offset, int length) {
		ByteBuffer buffer = ByteBuffer.wrap(array, offset, length).order(byteOrder);
		return buffer.getShort();
	}

	public static short readUnsignedShort(ByteOrder byteOrder, byte[] array, int offset) {
		return (short) (readShort(byteOrder, array, offset, 2) & 0xFFFF);
	}

	public static short twoBytesToUnsignedShort(byte[] buf) {
		int index = 0;
		int firstByte = (0x000000FF & ((int) buf[index]));
		index++;

		firstByte = (0x000000FF & ((int) buf[index]));
		int secondByte = (0x000000FF & ((int) buf[index + 1]));
		char anUnsignedShort = (char) (firstByte << 8 | secondByte);
		return (short) anUnsignedShort;
	}

	public static int fourBytesToUnsignedInt(byte[] buf) {
		int index = 0;
		int firstByte = (0x000000FF & ((int) buf[index]));
		int secondByte = (0x000000FF & ((int) buf[index + 1]));
		int thirdByte = (0x000000FF & ((int) buf[index + 2]));
		int fourthByte = (0x000000FF & ((int) buf[index + 3]));
		index = index + 4;
		long anUnsignedInt = ((long) (firstByte << 24 | secondByte << 16 | thirdByte << 8 | fourthByte)) & 0xFFFFFFFFL;
		return (int) anUnsignedInt;
	}

	public static int readInt(ByteOrder byteOrder, byte[] array, int offset, int length) {
		ByteBuffer buffer = ByteBuffer.wrap(array, offset, length).order(byteOrder);
		return buffer.getInt();
	}

	public static int readUnsignedInt(ByteOrder byteOrder, byte[] array, int offset) {
		return (readInt(byteOrder, array, offset, 4) & 0xFFFF);
	}

	public static float readFloat(ByteOrder byteOrder, byte[] array, int offset, int length) {
		ByteBuffer buffer = ByteBuffer.wrap(array, offset, length).order(byteOrder);
		return buffer.getFloat();
	}
}
