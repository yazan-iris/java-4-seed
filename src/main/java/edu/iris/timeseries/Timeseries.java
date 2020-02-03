package edu.iris.timeseries;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import edu.iris.seed.SeedHeader.Type;
import edu.iris.seed.SeedDataHeader;
import edu.iris.seed.SeedException;
import edu.iris.seed.data.B1000;
import edu.iris.seed.data.EncodingFormat;
import edu.iris.seed.record.DataRecord;
import edu.iris.seed.record.DecompressedDataRecord;
import edu.iris.seedcodec.CodecException;
import edu.iris.seedcodec.DecompressedData;
import edu.iris.seedcodec.UnsupportedCompressionType;

public class Timeseries implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7762713350314515989L;

	Logger logger = Logger.getLogger("edu.iris.dmc.ws.seed.Timeseries");

	private String networkCode; /* Network designation */
	private String stationCode; /* Station designation */
	private String location; /* Location designation */
	private String channelCode; /* Channel designation */
	private int totalNumberOfSamples;
	private int actualNumberOfSamples;

	private String channel;

	private Type type;
	private char quality;/* Data quality indicator */

	private EncodingFormat encodingFormat;
	private float min = Integer.MAX_VALUE;
	private float max = Integer.MIN_VALUE;
	private List<Segment> segments = new ArrayList<Segment>();

	private Timeseries(String networkCode, String stationCode, String location, String channelCode) {
		this.networkCode = networkCode.trim();
		this.stationCode = stationCode.trim();
		this.location = location;
		this.channelCode = channelCode;
	}

	public static Timeseries from(String networkCode, String stationCode, String location, String channelCode) {
		return new Timeseries(networkCode, stationCode, location, channelCode);
	}

	public void add(DataRecord dataRecord, boolean reduce)
			throws UnsupportedCompressionType, CodecException, SeedException {
		SeedDataHeader dataHeader = (SeedDataHeader) dataRecord.getHeader();
		char quality = (char) dataHeader.getDataQualityFlag();
		if (this.quality == '\u0000') {
			this.quality = quality;
		} else {
			if (this.quality != quality) {
				this.quality = 'M';
			}
		}
		B1000 b1000 = (B1000) dataRecord.get(1000);
		EncodingFormat type = b1000.getEncodingFormat();

		if (this.encodingFormat == null) {
			this.encodingFormat = type;
		} else {
			if (!this.encodingFormat.equals(type)) {
				throw new UnsupportedCompressionType(
						"Expected " + this.encodingFormat.name() + " but found " + type.name());
			}
		}
		DecompressedDataRecord decompressedDataRecord = dataRecord.decompress(reduce);

		// logger.info("Adding data record: " + reduce);
		this.totalNumberOfSamples += decompressedDataRecord.getNumberOfSamples();
		this.actualNumberOfSamples += decompressedDataRecord.getRecord().getData().length;
		if (min > decompressedDataRecord.getMinumumValue()) {
			min = decompressedDataRecord.getMinumumValue();
		}
		if (max < decompressedDataRecord.getMaximumValue()) {
			max = decompressedDataRecord.getMaximumValue();
		}

		if (this.segments.isEmpty()) {
			createAndAdd(decompressedDataRecord);
		} else {
			for (Segment segment : this.segments) {
				if (!Util.isSampleRateTolerable(segment.getSamplerate(), decompressedDataRecord.getSampleRate())) {
					// this.createAndAdd(decompressedDataRecord);
					break;
				} else {
					int index = segment.add(decompressedDataRecord);
					if (index > 0) {
						return;
					}
				}
			}
			this.createAndAdd(decompressedDataRecord);
		}
	}
	
	public void add1(DataRecord dataRecord, boolean reduce)
			throws UnsupportedCompressionType, CodecException, SeedException {
		SeedDataHeader dataHeader = (SeedDataHeader) dataRecord.getHeader();
		char quality = (char) dataHeader.getDataQualityFlag();
		if (this.quality == '\u0000') {
			this.quality = quality;
		} else {
			if (this.quality != quality) {
				this.quality = 'M';
			}
		}
		B1000 b1000 = (B1000) dataRecord.get(1000);
		EncodingFormat type = b1000.getEncodingFormat();

		if (this.encodingFormat == null) {
			this.encodingFormat = type;
		} else {
			if (!this.encodingFormat.equals(type)) {
				throw new UnsupportedCompressionType(
						"Expected " + this.encodingFormat.name() + " but found " + type.name());
			}
		}
		DecompressedDataRecord decompressedDataRecord = dataRecord.decompress(reduce);

		// logger.info("Adding data record: " + reduce);
		this.totalNumberOfSamples += decompressedDataRecord.getNumberOfSamples();
		this.actualNumberOfSamples += decompressedDataRecord.getRecord().getData().length;
		if (min > decompressedDataRecord.getMinumumValue()) {
			min = decompressedDataRecord.getMinumumValue();
		}
		if (max < decompressedDataRecord.getMaximumValue()) {
			max = decompressedDataRecord.getMaximumValue();
		}

		if (this.segments.isEmpty()) {
			createAndAdd(decompressedDataRecord);
		} else {
			for (Segment segment : this.segments) {
				if (!Util.isSampleRateTolerable(segment.getSamplerate(), decompressedDataRecord.getSampleRate())) {
					// this.createAndAdd(decompressedDataRecord);
					break;
				} else {
					int index = segment.add(decompressedDataRecord);
					if (index > 0) {
						return;
					}
				}
			}
			this.createAndAdd(decompressedDataRecord);
		}
	}

	private void createAndAdd(DecompressedDataRecord decompressedDataRecord) {
		Segment segment = Segment.from(encodingFormat, decompressedDataRecord.getSampleRate());
		segment.add(decompressedDataRecord);
		segment.setTimeseries(this);
		this.segments.add(segment);
	}

	public Type getType() {
		return this.type;
	}
	public long getStartTime() {
		Segment s = this.segments.get(0);
		return s.getStartTimeAsLong();
	}

	public long getEndTime() {
		Segment s = this.segments.get(this.segments.size() - 1);
		return s.getEndTimeAsLong();
	}

	public float getSampleRate() {
		Segment s = this.segments.get(0);
		return s.getSamplerate();
	}

	public long getTotalNumberOfSamples() {
		return this.totalNumberOfSamples;
	}

	public long getActualNumberOfSamples() {
		return this.actualNumberOfSamples;
	}

	public EncodingFormat getEncodingFormat() {
		return encodingFormat;
	}

	public float getMin() {
		return min;
	}

	public float getMax() {
		return max;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getNetworkCode() {
		return networkCode;
	}

	public String getStationCode() {
		return stationCode;
	}

	public String getLocation() {
		return location;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public char getDataQuality() {
		return this.quality;
	}

	public List<Segment> getSegments() {
		return segments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((channelCode == null) ? 0 : channelCode.hashCode());
		result = prime * result;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((networkCode == null) ? 0 : networkCode.hashCode());
		result = prime * result + ((stationCode == null) ? 0 : stationCode.hashCode());
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
		Timeseries other = (Timeseries) obj;
		if (channelCode == null) {
			if (other.channelCode != null)
				return false;
		} else if (!channelCode.equals(other.channelCode))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (networkCode == null) {
			if (other.networkCode != null)
				return false;
		} else if (!networkCode.equals(other.networkCode))
			return false;
		if (stationCode == null) {
			if (other.stationCode != null)
				return false;
		} else if (!stationCode.equals(other.stationCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Timeseries [networkCode=" + networkCode + ", stationCode=" + stationCode + ", location=" + location
				+ ", channelCode=" + channelCode + ", dataQuality=" + quality + ", numberOfSegments="
				+ this.segments.size() + "]";
	}

}
