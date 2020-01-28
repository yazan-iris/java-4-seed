package edu.iris.seed;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import edu.iris.seed.data.ByteUtil;
import edu.iris.seed.lang.math.SeedNumbers;

public class SeedDataHeader implements SeedHeader, DataBlockette {

	private int sequence;
	private Type recordType;
	private char continuation;

	private String network;
	private String station;
	private String location;
	private String channel;
	private ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
	private BTime start;
	private int numberOfSamples;
	private int sampleRateMultiplier;
	private int activityFlag;
	private int ioClockFlag;
	private int dataQualityFlag;
	private int numberOfFollowingBlockettes;
	private int sampleRateFactor;
	private int timeCorrection;
	private int beginingOfData;
	private int firstDataBlockette;

	private SeedDataHeader(int sequence, Type recordType, char continuation) {
		this.sequence = sequence;
		this.recordType = recordType;
		this.continuation = continuation;
	}

	void updateSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getSequence() {
		return sequence;
	}

	public Type getRecordType() {
		return recordType;
	}

	public char getContinuation() {
		return continuation;
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

	public ByteOrder getByteOrder() {
		return this.byteOrder;
	}

	public BTime getStart() {
		return start;
	}

	public int getNumberOfSamples() {
		return numberOfSamples;
	}

	public int getSampleRateMultiplier() {
		return sampleRateMultiplier;
	}

	public int getActivityFlag() {
		return activityFlag;
	}

	public int getIoClockFlag() {
		return ioClockFlag;
	}

	public int getDataQualityFlag() {
		return dataQualityFlag;
	}

	public void setNumberOfFollowingBlockettes(int numberOfFollowingBlockettes) {
		this.numberOfFollowingBlockettes = numberOfFollowingBlockettes;
	}

	public int getNumberOfFollowingBlockettes() {
		return numberOfFollowingBlockettes;
	}

	public int getSampleRateFactor() {
		return sampleRateFactor;
	}

	public int getTimeCorrection() {
		return timeCorrection;
	}

	public void setBeginingOfData(int beginingOfData) {
		this.beginingOfData = beginingOfData;
	}

	public int getBeginingOfData() {
		return beginingOfData;
	}

	public int getFirstDataBlockette() {
		return firstDataBlockette;
	}

	@Override
	public int getType() {
		return 0;
	}

	@Override
	public int getNextBlocketteByteNumber() {
		return firstDataBlockette;
	}

	byte[] nextSequene(boolean continuation) {
		return String.format("%06d%c%c", sequence++, this.getRecordType().valueAsChar(), ' ').getBytes();
	}

	@Override
	public byte[] toSeedBytes() throws SeedException {
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(48);

		/// SeedByteArrayBuilder builder=new SeedByteArrayBuilder(48);
		// SeedString
		builder.appendSequence(this.sequence);
		builder.appendChar(this.recordType.valueAsChar());
		builder.appendChar(this.continuation);

		builder.appendAscii(station, 5).appendAscii(location, 2).appendAscii(channel, 3).appendAscii(network, 2);

		builder.append(start);

		builder.appendU16(this.numberOfSamples);

		builder.append16(this.sampleRateFactor);

		builder.append16(this.sampleRateMultiplier);

		builder.appendU8(this.activityFlag);

		builder.appendU8(this.ioClockFlag);

		builder.appendU8(this.dataQualityFlag);

		builder.appendU8(this.numberOfFollowingBlockettes);

		builder.appendFloat(this.timeCorrection);
		builder.appendU16(this.beginingOfData).appendU16(this.firstDataBlockette);
		return builder.toBytes();
	}

	@Override
	public String toString() {
		return "SeedDataHeader [sequence=" + sequence + ", quality=" + recordType + ", reserved=" + continuation
				+ ", network=" + network + ", station=" + station + ", location=" + location + ", channel=" + channel
				+ ", byteOrder=" + byteOrder + ", start=" + start + ", numberOfSamples=" + numberOfSamples
				+ ", sampleRateMultiplier=" + sampleRateMultiplier + ", activityFlag=" + activityFlag + ", ioClockFlag="
				+ ioClockFlag + ", dataQualityFlag=" + dataQualityFlag + ", numberOfFollowingBlockettes="
				+ numberOfFollowingBlockettes + ", sampleRateFactor=" + sampleRateFactor + ", timeCorrection="
				+ timeCorrection + ", beginingOfData=" + beginingOfData + ", firstDataBlockette=" + firstDataBlockette
				+ "]";
	}

	public static class Builder {

		private int sequence;
		private Type type;
		private char reserved;

		private String network;
		private String station;
		private String location;
		private String channel;
		private ByteOrder byteOrder = ByteOrder.BIG_ENDIAN;
		private BTime start;
		private int numberOfSamples;
		private int sampleRateMultiplier;
		private int activityFlag;
		private int ioClockFlag;
		private int dataQualityFlag;
		private int numberOfFollowingBlockettes;
		private int sampleRateFactor;
		private int timeCorrection;
		private int beginingOfData;
		private int firstDataBlockette;

		private byte[] bytes;

		private Builder() {
		}

		private Builder(int sequence, Type type, char reserved) {
			this.sequence = sequence;
			this.type = type;
			this.reserved = reserved;
		}

		public Builder network(String network) {
			this.network = network;
			return this;
		}

		public Builder station(String station) {
			this.station = station;
			return this;
		}

		public Builder location(String location) {
			this.location = location;
			return this;
		}

		public Builder channel(String channel) {
			this.channel = channel;
			return this;
		}

		public Builder start(BTime start) {
			this.start = start;
			return this;
		}

		public Builder numberOfSamples(int numberOfSamples) {
			this.numberOfSamples = numberOfSamples;
			return this;
		}

		public Builder sampleRateMultiplier(int sampleRateMultiplier) {
			this.sampleRateMultiplier = sampleRateMultiplier;
			return this;
		}

		public Builder activityFlag(int activityFlag) {
			this.activityFlag = activityFlag;
			return this;
		}

		public Builder ioClockFlag(int ioClockFlag) {
			this.ioClockFlag = ioClockFlag;
			return this;
		}

		public Builder dataQualityFlag(int dataQualityFlag) {
			this.dataQualityFlag = dataQualityFlag;
			return this;
		}

		public Builder numberOfFollowingBlockettes(int numberOfFollowingBlockettes) {
			this.numberOfFollowingBlockettes = numberOfFollowingBlockettes;
			return this;
		}

		public Builder sampleRateFactor(int sampleRateFactor) {
			this.sampleRateFactor = sampleRateFactor;
			return this;
		}

		public Builder timeCorrection(int timeCorrection) {
			this.timeCorrection = timeCorrection;
			return this;
		}

		public Builder beginingOfData(int beginingOfData) {
			this.beginingOfData = beginingOfData;
			return this;
		}

		public Builder firstDataBlockette(int firstDataBlockette) {
			this.firstDataBlockette = firstDataBlockette;
			return this;
		}

		public Builder byteOrder(ByteOrder byteOrder) {
			this.byteOrder = byteOrder;
			return this;
		}

		public static Builder newInstance(int sequence, Type type, char reserved) {
			return new Builder(sequence, type, reserved);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public Builder bytes(byte[] bytes) {
			this.bytes = bytes;
			return this;
		}

		public SeedDataHeader build() throws SeedException {
			if (bytes != null) {
				return buildFromBytes();
			} else {
				SeedDataHeader header = new SeedDataHeader(sequence, type, reserved);
				header.network = network;
				header.station = station;
				header.location = location;
				header.channel = channel;
				if (byteOrder != null) {
					header.byteOrder = byteOrder;
				}
				header.start = start;
				header.numberOfSamples = numberOfSamples;
				header.sampleRateMultiplier = sampleRateMultiplier;
				header.activityFlag = activityFlag;
				header.ioClockFlag = ioClockFlag;
				header.dataQualityFlag = dataQualityFlag;
				header.numberOfFollowingBlockettes = numberOfFollowingBlockettes;
				header.sampleRateFactor = sampleRateFactor;
				header.timeCorrection = timeCorrection;
				header.beginingOfData = beginingOfData;
				header.firstDataBlockette = firstDataBlockette;
				return header;
			}
		}

		private SeedDataHeader buildFromBytes() throws SeedException {
			if (bytes == null) {
				throw new SeedException("No data to read from buffer, NULL ");
			}

			if (bytes.length < 48) {
				throw new SeedException("Invalid bytes size, must be 48 ");
			}
			SeedDataHeader header = new SeedDataHeader(Integer.parseInt(new String(bytes, 0, 6)),
					Type.from((char) bytes[6]), (char) bytes[7]);

			int offset = 8;

			try {
				header.station = new String(bytes, offset, 5, "us-ascii").trim();

				offset += 5;
				header.location = new String(bytes, offset, 2, "us-ascii").trim();
				offset += 2;
				header.channel = new String(bytes, offset, 3, "us-ascii").trim();
				offset += 3;
				header.network = new String(bytes, offset, 2, "us-ascii").trim();
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
			if (result < 1900 || result > 2050) {
				header.byteOrder = ByteOrder.LITTLE_ENDIAN;
			}

			header.start = ByteBufferReader.readBTime(header.byteOrder, bytes, offset, 10);
			/*
			 * int year = ByteUtil.readUnsignedShort(header.byteOrder, bytes, offset);
			 * offset += 2; int day = SeedNumbers.readUnsignedShort(bytes, offset); offset
			 * += 2;
			 * 
			 * int hour = Byte.toUnsignedInt(bytes[offset++]); int minute =
			 * Byte.toUnsignedInt(bytes[offset++]); int second =
			 * Byte.toUnsignedInt(bytes[offset++]); // 27 unused offset++; int tenthSecond =
			 * SeedNumbers.readUnsignedShort(bytes, offset); offset += 2;
			 * 
			 * Calendar cal = GregorianCalendar.getInstance(TimeZone.getTimeZone("UTC"));
			 * cal.set(Calendar.MILLISECOND, tenthSecond / 10); // loose precision // here
			 * cal.set(Calendar.SECOND, second); cal.set(Calendar.MINUTE, minute);
			 * cal.set(Calendar.HOUR_OF_DAY, hour); cal.set(Calendar.DAY_OF_YEAR, day);
			 * cal.set(Calendar.YEAR, year); header.start = new BTime(year, day, hour,
			 * minute, second, tenthSecond);
			 */
			offset += 10;

			header.numberOfSamples = SeedNumbers.readUnsignedShort(bytes, offset);
			offset += 2;
			header.sampleRateFactor = SeedNumbers.readUnsignedShort(bytes, offset);
			offset += 2;

			header.sampleRateMultiplier = SeedNumbers.readUnsignedShort(bytes, offset);
			offset += 2;

			header.activityFlag = bytes[offset++];
			header.ioClockFlag = bytes[offset++];
			header.dataQualityFlag = bytes[offset++];

			header.numberOfFollowingBlockettes = bytes[offset++];

			header.timeCorrection = SeedNumbers.readInt(bytes, offset, 4);
			offset += 4;

			header.beginingOfData = SeedNumbers.readUnsignedShort(bytes, offset);
			offset += 2;
			header.firstDataBlockette = SeedNumbers.readUnsignedShort(bytes, offset);
			return header;
		}
	}

}
