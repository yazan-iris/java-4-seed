package edu.iris.timeseries;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

import edu.iris.seed.SeedDataHeader;
import edu.iris.seed.SeedException;
import edu.iris.seed.data.B100;
import edu.iris.seed.data.B1000;
import edu.iris.seed.data.B1001;
import edu.iris.seed.data.EncodingFormat;
import edu.iris.seed.record.DataRecord;
import edu.iris.seedcodec.CodecException;
import edu.iris.seedcodec.DecompressedData;

public class DecompressedDataRecord {

	private final int numberOfSamples;
	private final float sampleRate;
	private final long startTime;
	private final long endTime;
	private final long expectedNextSampleTime;

	private DecompressedData record;

	private DecompressedDataRecord(DataRecord dataRecord, boolean reduce) throws CodecException, SeedException {
		SeedDataHeader dataHeader = (SeedDataHeader) dataRecord.getHeader();
		this.numberOfSamples = dataHeader.getNumberOfSamples();
		B100 b100 = (B100) dataRecord.get(100);
		if (b100 != null) {
			this.sampleRate = b100.getActualSampleRate();
		} else {
			this.sampleRate = dataHeader.getSampleRateFactor();
		}

		int microseconds = 0;
		B1001 b1001 = (B1001) dataRecord.get(1001);
		if (b1001 != null) {
			microseconds = b1001.getMicroSeconds();
		}
		this.startTime = Util.toLongTime(dataHeader.getStart(), dataHeader.getActivityFlag(),
				dataHeader.getTimeCorrection(), microseconds);

		double durationinInSeconds = ((dataHeader.getNumberOfSamples() - 1) / (double) this.sampleRate);

		this.endTime = Util.addSecondsToLong(this.startTime, durationinInSeconds);

		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		long durationInMiliSecond = (long) ((this.numberOfSamples - 1) / this.sampleRate) * 1000;
		cal.setTimeInMillis(this.startTime + durationInMiliSecond);

		double d = (dataHeader.getNumberOfSamples() / this.sampleRate) * 1000;
		cal.setTimeInMillis(this.startTime + (long) d);
		Timestamp ts = new Timestamp(cal.getTimeInMillis());
		this.expectedNextSampleTime = ts.getTime();
		B1000 b1000 = (B1000) dataRecord.get(1000);
		this.record = DecompressedData.of(b1000.getEncodingFormat(), dataRecord.getData(), this.numberOfSamples, reduce,
				false);
	}

	public static DecompressedDataRecord from(DataRecord dataRecord) throws SeedException, CodecException {
		return DecompressedDataRecord.from(dataRecord, false);
	}

	public static DecompressedDataRecord from(DataRecord dataRecord, boolean reduce)
			throws SeedException, CodecException {
		return new DecompressedDataRecord(dataRecord, reduce);
	}

	public int getNumberOfSamples() {
		return numberOfSamples;
	}

	public float getSampleRate() {
		return sampleRate;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public long getExpectedNextSampleTime() {
		return expectedNextSampleTime;
	}

	public DecompressedData getRecord() {
		return record;
	}

	public float getMinumumValue() {
		return this.record.getMin();
	}

	public float getMaximumValue() {
		return this.record.getMax();
	}
	
	public EncodingFormat getEncodingFormat() {
		return this.record.getEncodingFormat();
	}
}
