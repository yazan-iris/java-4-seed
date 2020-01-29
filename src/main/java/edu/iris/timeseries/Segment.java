package edu.iris.timeseries;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import edu.iris.seed.data.EncodingFormat;

public class Segment implements Serializable, Comparable<Segment> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6055844360696344359L;

	private final EncodingFormat encodingFormat;
	private final float samplerate; /* Nominal sample rate (Hz) */

	private long startTime = Long.MAX_VALUE;
	private long endTime = Long.MIN_VALUE;
	private long expectedNextSampleTime;

	private int totalNumberOfSamples = 0; /*
											 * Number of samples in trace segment
											 */

	private Timeseries timeseries;
	private DataList data = new DataList(2);

	private Segment(EncodingFormat encodingFormat, float sampleRate) {
		this.encodingFormat = encodingFormat;
		this.samplerate = sampleRate;
	}

	public static Segment from(EncodingFormat type, float sampleRate) {
		return new Segment(type, sampleRate);
	}

	public int add(DecompressedDataRecord decompressedDataRecord) {
		if (!this.encodingFormat.equals(decompressedDataRecord.getEncodingFormat())) {
			throw new IllegalArgumentException("Type does not match, expecting " + this.encodingFormat.name() + " but found "
					+ decompressedDataRecord.getEncodingFormat().name());
		} /*
			 * if (!Util.isSampleRateTolerable(this.samplerate,
			 * decompressedDataRecord.getSampleRate())) { throw new
			 * IllegalArgumentException("intolerable sample rate variation."); }
			 */
		this.totalNumberOfSamples += decompressedDataRecord.getNumberOfSamples();

		if (this.startTime > decompressedDataRecord.getStartTime()) {
			this.startTime = decompressedDataRecord.getStartTime();
		}

		if (this.endTime < decompressedDataRecord.getEndTime()) {
			this.endTime = decompressedDataRecord.getEndTime();
			this.expectedNextSampleTime = decompressedDataRecord.getExpectedNextSampleTime();
		}

		return this.data.add(decompressedDataRecord);
	}

	public List<DecompressedDataRecord> getData() {
		return this.data.get();
	}

	public Timestamp getExpectedNextSampleTime() {
		return new Timestamp(expectedNextSampleTime);
	}

	public long getExpectedNextSampleTimeAsLong() {
		return expectedNextSampleTime;
	}

	public void setTimeseries(Timeseries ts) {
		this.timeseries = ts;
	}

	public long getStartTimeAsLong() {
		return startTime;
	}

	public Timestamp getStartTime() {
		return new Timestamp(startTime);
	}

	public Timestamp getEndTime() {
		return new Timestamp(endTime);
	}

	public long getEndTimeAsLong() {
		return endTime;
	}

	public float getSamplerate() {
		return samplerate;
	}

	public int getTotalNumberOfSamples() {
		return this.totalNumberOfSamples;
	}

	public EncodingFormat getEncodingFormat() {
		return encodingFormat;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Long.hashCode(startTime);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Segment other = (Segment) obj;
		if (startTime != other.startTime) {
			return false;
		}
		return true;
	}

	public int compareTo(Segment ts) {
		if (ts == null) {
			return -1;
		}
		if (this.startTime > ts.getStartTimeAsLong()) {
			return 1;
		} else if (this.startTime < ts.getStartTimeAsLong()) {
			return -1;
		} else {
			return 0;
		}
	}

	@Override
	public String toString() {
		return "Segment [type=" + encodingFormat + ", samplerate=" + samplerate + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", expectedNextSampleTime="
				+ expectedNextSampleTime + ", totalNumberOfSamples=" + totalNumberOfSamples
				+ ", timeseries=" + timeseries + "]";
	}

}
