package edu.iris.seed;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import edu.iris.seed.lang.math.SeedNumbers;

public class SeedDataHeader implements SeedHeader {

	private int sequence;
	private char quality;
	private byte reserved;

	private String network;
	private String station;
	private String location;
	private String channel;
	private boolean swap;
	private BTime start;
	private BTime end;
	private int numberOfSamples;
	private int sampleRateMultiplier;
	private byte activityFlag;
	private byte ioClockFlag;
	private byte dataQualityFlag;
	private int numberOfFollowingBlockettes;
	private int sampleRateFactor;
	private long timeCorrection;
	private long beginingOfData;
	private int firstDataBlockette;

	private SeedDataHeader(int sequence, char quality, byte reserved) {
		this.sequence = sequence;
		this.quality = quality;
		this.reserved = reserved;
	}

	public int getSequence() {
		return sequence;
	}

	public char getType() {
		return quality;
	}

	public char getQuality() {
		return quality;
	}

	public byte getReserved() {
		return reserved;
	}

	public boolean isContinuation() {
		return false;
	}

	public String getNetwork() {
		return network;
	}

	public String getStation() {
		return station;
	}

	public String getLocation() {
		return location;
	}

	public String getChannel() {
		return channel;
	}

	public boolean isSwap() {
		return swap;
	}

	public BTime getStart() {
		return start;
	}

	public BTime getEnd() {
		return end;
	}

	public int getNumberOfSamples() {
		return numberOfSamples;
	}

	public int getSampleRateMultiplier() {
		return sampleRateMultiplier;
	}

	public byte getActivityFlag() {
		return activityFlag;
	}

	public byte getIoClockFlag() {
		return ioClockFlag;
	}

	public byte getDataQualityFlag() {
		return dataQualityFlag;
	}

	public int getNumberOfFollowingBlockettes() {
		return numberOfFollowingBlockettes;
	}

	public int getSampleRateFactor() {
		return sampleRateFactor;
	}

	public long getTimeCorrection() {
		return timeCorrection;
	}

	public long getBeginingOfData() {
		return beginingOfData;
	}

	public int getFirstDataBlockette() {
		return firstDataBlockette;
	}

	public static class Builder {

		private Builder() {

		}

		public static Builder newInstance() {
			return new Builder();
		}

		public SeedDataHeader build(byte[] bytes) throws SeedException {
			if (bytes == null) {
				throw new SeedException("No data to read from buffer, NULL ");
			}

			if (bytes.length != 48) {
				throw new SeedException("Invalid bytes size, must be 48 ");
			}
			SeedDataHeader header = new SeedDataHeader(Integer.parseInt(new String(bytes, 0, 6)), (char) bytes[7],
					bytes[6]);
			String dataToParse = new String(bytes, 0, 20);

			int offset = 8;

			try {
				header.station = new String(bytes, offset, 5, "us-ascii");

				offset += 5;
				header.location = new String(bytes, offset, 2, "us-ascii");
				offset += 2;
				header.channel = new String(bytes, offset, 3, "us-ascii");
				offset += 3;
				header.network = new String(bytes, offset, 2, "us-ascii");
				offset += 2;
			} catch (UnsupportedEncodingException e) {
				throw new SeedException(e);
			}
			ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4);
			// by choosing big endian, high order bytes must be put
			// to the buffer before low order bytes
			byteBuffer.order(ByteOrder.BIG_ENDIAN);
			// since ints are 4 bytes (32 bit), you need to put all 4, so put 0
			// for the high order bytes
			byteBuffer.put((byte) 0x00);
			byteBuffer.put((byte) 0x00);
			byteBuffer.put(bytes[offset]);
			byteBuffer.put(bytes[offset + 1]);

			byteBuffer.flip();
			int result = byteBuffer.getInt();
			header.swap = (result < 1900 || result > 2050);

			int year = SeedNumbers.readUnsignedShort(bytes, offset);
			offset += 2;
			int day = SeedNumbers.readUnsignedShort(bytes, offset);
			offset += 2;

			int hour = Byte.toUnsignedInt(bytes[offset++]);
			int minute = Byte.toUnsignedInt(bytes[offset++]);
			int second = Byte.toUnsignedInt(bytes[offset++]);
			// 27 unused
			offset++;
			int tenthSecond = SeedNumbers.readUnsignedShort(bytes, offset);
			offset += 2;

			Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
			cal.set(Calendar.MILLISECOND, tenthSecond / 10); // loose precision
																// here
			cal.set(Calendar.SECOND, second);
			cal.set(Calendar.MINUTE, minute);
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.DAY_OF_YEAR, day);
			cal.set(Calendar.YEAR, year);
			new BTime(year, day, hour, minute, second, tenthSecond);

			header.numberOfSamples = SeedNumbers.readUnsignedShort(bytes, offset);
			offset += 2;

			header.sampleRateFactor = SeedNumbers.readUnsignedShort(bytes, offset);
			offset += 2;

			header.sampleRateMultiplier = SeedNumbers.readUnsignedShort(bytes, offset);
			offset += 2;

			header.activityFlag = bytes[offset++];
			header.ioClockFlag = bytes[offset++];
			header.dataQualityFlag = bytes[offset++];

			header.numberOfFollowingBlockettes = Byte.toUnsignedInt(bytes[offset++]);

			header.timeCorrection = SeedNumbers.readInt(bytes, offset, 4);
			offset += 4;

			header.beginingOfData = SeedNumbers.readUnsignedShort(bytes, offset);
			offset += 2;
			header.firstDataBlockette = SeedNumbers.readUnsignedShort(bytes, offset);
			return header;
		}
	}
}
