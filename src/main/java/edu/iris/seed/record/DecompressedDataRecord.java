package edu.iris.seed.record;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.TimeZone;

import edu.iris.seed.BTime;
import edu.iris.seed.SeedDataHeader;
import edu.iris.seed.data.B100;
import edu.iris.seed.data.B1000;
import edu.iris.seed.data.B1001;
import edu.iris.seed.data.EncodingFormat;
import edu.iris.seedcodec.CodecException;
import edu.iris.seedcodec.DecompressedData;
import edu.iris.timeseries.Util;

public class DecompressedDataRecord {

	// private Map<Integer, DataBlockette> map = new TreeMap<>();

	private final int numberOfSamples;
	private final float sampleRate;
	private final long startTime;
	private final long endTime;
	private final long expectedNextSampleTime;
	private EncodingFormat encodingFormat;
	private DecompressedData record;

	private DecompressedDataRecord(DecompressedData record, EncodingFormat encodingFormat, int numberOfSamples,
			float sampleRate, long startTime, long endTime, long expectedNextSampleTime) {
		this.numberOfSamples = numberOfSamples;
		this.sampleRate = sampleRate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.expectedNextSampleTime = expectedNextSampleTime;
		this.encodingFormat = encodingFormat;
		this.record = record;
	}

	public static DecompressedDataRecord decompress(DataRecord dataRecord, boolean reduce) throws CodecException {

		SeedDataHeader dataHeader = (SeedDataHeader) dataRecord.getHeader();
		B100 b100 = (B100) dataRecord.get(100);

		float sampleRate = dataHeader.getSampleRateFactor();
		if (b100 != null) {
			sampleRate = b100.getActualSampleRate();
		}

		int microseconds = 0;
		B1001 b1001 = (B1001) dataRecord.get(1001);
		if (b1001 != null) {
			microseconds = b1001.getMicroSeconds();
		}
		long startTime = Util.toLongTime(dataHeader.getStart(), dataHeader.getActivityFlag(),
				dataHeader.getTimeCorrection(), microseconds);

		double durationinInSeconds = ((dataHeader.getNumberOfSamples() - 1) / (double) sampleRate);

		long endTime = Util.addSecondsToLong(startTime, durationinInSeconds);

		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		long durationInMiliSecond = (long) ((dataHeader.getNumberOfSamples() - 1) / sampleRate) * 1000;
		cal.setTimeInMillis(startTime + durationInMiliSecond);

		double d = (dataHeader.getNumberOfSamples() / sampleRate) * 1000;
		cal.setTimeInMillis(startTime + (long) d);
		Timestamp ts = new Timestamp(cal.getTimeInMillis());
		long expectedNextSampleTime = ts.getTime();

		B1000 b1000 = (B1000) dataRecord.get(1000);
		if(b1000==null) {
			System.out.println("::::::::::");
		}
		DecompressedData decompressedData = DecompressedData.of(b1000.getEncodingFormat(), dataRecord.getData(),
				dataHeader.getNumberOfSamples(), reduce, false);
		return new DecompressedDataRecord(decompressedData, b1000.getEncodingFormat(), dataHeader.getNumberOfSamples(),
				sampleRate, startTime, endTime, expectedNextSampleTime);
	}

	public EncodingFormat getEncodingFormat() {
		return encodingFormat;
	}

	public int getNumberOfSamples() {
		return this.numberOfSamples;
	}

	public long expectedNextSampleTime() {
		double d = (getNumberOfSamples() / this.getSampleRate()) * 1000;
		Calendar cal = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("UTC"));
		long durationInMiliSecond = (long) ((getNumberOfSamples() - 1) / this.getSampleRate()) * 1000;
		long startTime = this.getStartTime();
		cal.setTimeInMillis(startTime + durationInMiliSecond);
		cal.setTimeInMillis(startTime + (long) d);
		Timestamp ts = new Timestamp(cal.getTimeInMillis());
		return ts.getTime();
	}

	public long getStartTime() {
		return this.startTime;
	}

	private long computeStartTime(BTime bTime, int activityFlags, int timeCorrection, int microseconds) {
		long totalNanoSeconds = (microseconds * 1000) + (bTime.getTenthMilliSecond() * 100000);
		Timestamp timeStamp = null;

		// If time correction is not applied, apply time correction
		if (activityFlags == 0) {
			long timeCorrectionInNano = timeCorrection * 100000l;
			totalNanoSeconds = totalNanoSeconds + timeCorrectionInNano;
		}

		int overflow = (int) (totalNanoSeconds / 1000000000);
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		cal.set(Calendar.YEAR, bTime.getYear());
		cal.set(Calendar.DAY_OF_YEAR, bTime.getDayOfYear());
		cal.set(Calendar.HOUR_OF_DAY, bTime.getHour());
		cal.set(Calendar.MINUTE, bTime.getMinute());

		if (overflow == 0) {
			cal.set(Calendar.SECOND, bTime.getSecond());
			timeStamp = new Timestamp(cal.getTime().getTime());
		} else {
			cal.set(Calendar.SECOND, bTime.getSecond() + overflow);
			timeStamp = new Timestamp(cal.getTime().getTime());
		}
		timeStamp.setNanos(0);

		int remainderNanos = (int) (totalNanoSeconds % 1000000000);

		if (remainderNanos == 0) {

		} else if (remainderNanos > 0) {
			timeStamp.setNanos(remainderNanos);
		} else {
			cal.set(Calendar.SECOND, cal.get(Calendar.SECOND) - 1);
			timeStamp = new Timestamp(cal.getTime().getTime());
			timeStamp.setNanos(1000000000 + remainderNanos);
		}
		return roundTime(timeStamp).getTime();

	}

	public long getEndTime() {
		return computeEndTime();
	}

	private long computeEndTime() {
		double durationinInSeconds = ((getNumberOfSamples() - 1) / (double) this.getSampleRate());

		return Util.addSecondsToLong(this.getStartTime(), durationinInSeconds);
	}

	public long getExpectedNextSampleTime() {
		return expectedNextSampleTime;
	}

	private Timestamp roundTime(Timestamp timestamp) {

		int nanos = timestamp.getNanos();

		nanos = (Math.round(nanos / 1000)) * 1000;

		if (nanos == 0) {
			// do nothing
		} else if (nanos > 0 && nanos < 1000000000) {
			timestamp.setNanos(nanos);
		} else {
			timestamp.setNanos(0);
			timestamp = new Timestamp(timestamp.getTime() + 1000);
		}
		return timestamp;
	}

	public DecompressedData getRecord() {
		return record;
	}

	public float getSampleRate() {
		return this.sampleRate;
	}

	public float getMinumumValue() {
		return this.record.getMin();
	}

	public float getMaximumValue() {
		return this.record.getMax();
	}
}
