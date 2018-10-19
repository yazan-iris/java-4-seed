package edu.iris.dmc.seed.control.station;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.AbstractBlockette;
import edu.iris.dmc.seed.BTime;
import edu.iris.dmc.seed.SeedException;

public class B052 extends AbstractBlockette implements StationBlockette {

	private String locationCode;

	private String channelCode;

	private Integer subChannelCode;

	private Integer instrumentIdentifier;

	private String optionalComment;

	private Integer unitsOfSignalResponse;
	private Integer unitsOfCalibrationInput;

	private Double latitude;
	private Double longitude;
	private Double elevation;
	private Double localDepth;// meter
	private Double azimuth;// degrees
	private Double dip;// degrees
	private Integer dataFormatIdentifier;
	private Integer dataRecordLength;
	private Double sampleRate;// Hz
	private Double maxClockDrift;// seconds
	private Integer numberOfComments = 0;
	private String channelFlags;
	private BTime startTime;
	private BTime endTime;
	private char updateFlag;

	private List<B059> b059s = new ArrayList<>();
	private List<ResponseBlockette> response = new ArrayList<>();

	public B052() {
		this(null);
	}

	public B052(String text) {
		super(52, "Channel Identifier Blockette");
		this.originalText = text;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Integer getSubChannelCode() {
		return subChannelCode;
	}

	public void setSubChannelCode(Integer subChannelCode) {
		this.subChannelCode = subChannelCode;
	}

	public Integer getInstrumentIdentifier() {
		return instrumentIdentifier;
	}

	public void setInstrumentIdentifier(Integer instrumentIdentifier) {
		this.instrumentIdentifier = instrumentIdentifier;
	}

	public Integer getUnitsOfSignalResponse() {
		return unitsOfSignalResponse;
	}

	public void setUnitsOfSignalResponse(Integer unitsOfSignalResponse) {
		this.unitsOfSignalResponse = unitsOfSignalResponse;
	}

	public Integer getUnitsOfCalibrationInput() {
		return unitsOfCalibrationInput;
	}

	public void setUnitsOfCalibrationInput(Integer unitsOfCalibrationInput) {
		this.unitsOfCalibrationInput = unitsOfCalibrationInput;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getElevation() {
		return elevation;
	}

	public void setElevation(double elevation) {
		this.elevation = elevation;
	}

	public double getLocalDepth() {
		return localDepth;
	}

	public void setLocalDepth(double localDepth) {
		this.localDepth = localDepth;
	}

	public double getAzimuth() {
		return azimuth;
	}

	public void setAzimuth(double azimuth) {
		this.azimuth = azimuth;
	}

	public double getDip() {
		return dip;
	}

	public void setDip(double dip) {
		this.dip = dip;
	}

	public int getDataFormatIdentifier() {
		return dataFormatIdentifier;
	}

	public void setDataFormatIdentifier(int dataFormatIdentifier) {
		this.dataFormatIdentifier = dataFormatIdentifier;
	}

	public int getDataRecordLength() {
		return dataRecordLength;
	}

	public void setDataRecordLength(int dataRecordLength) {
		this.dataRecordLength = dataRecordLength;
	}

	public double getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(double sampleRate) {
		this.sampleRate = sampleRate;
	}

	public Double getMaxClockDrift() {
		return maxClockDrift;
	}

	public void setMaxClockDrift(Double maxClockDrift) {
		this.maxClockDrift = maxClockDrift;
	}

	public int getNumberOfComments() {
		return numberOfComments;
	}

	public void setNumberOfComments(int numberOfComments) {
		this.numberOfComments = numberOfComments;
	}

	public String getChannelFlags() {
		return channelFlags;
	}

	public void setChannelFlags(String channelFlags) {
		this.channelFlags = channelFlags;
	}

	public BTime getStartTime() {
		return startTime;
	}

	public void setStartTime(BTime startTime) {
		this.startTime = startTime;
	}

	public BTime getEndTime() {
		return endTime;
	}

	public void setEndTime(BTime endTime) {
		this.endTime = endTime;
	}

	public char getUpdateFlag() {
		return updateFlag;
	}

	public void setUpdateFlag(char updateFlag) {
		this.updateFlag = updateFlag;
	}

	public String getOptionalComment() {
		return optionalComment;
	}

	public void setOptionalComment(String optionalComment) {
		this.optionalComment = optionalComment;
	}

	public List<B059> getB059s() {
		return b059s;
	}

	public void add(B059 b059) {
		this.b059s.add(b059);
	}

	public void addAll(List<B059> b059s) {
		this.b059s.addAll(b059s);
	}

	public void add(ResponseBlockette blockette) {
		this.response.add(blockette);
	}

	public List<ResponseBlockette> getResponseBlockette() {
		return this.response;
	}

	@Override
	public String toSeedString() throws SeedException{
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.locationCode, 2);
		builder.append(this.channelCode, 3);
		int key = 0;
		if (this.subChannelCode != null) {
			key = this.subChannelCode;
		}
		builder.append(key, 4);
		key = 0;
		if (this.instrumentIdentifier != null) {
			key = this.instrumentIdentifier;
		}
		builder.append(key, 3);
		if (this.optionalComment != null) {
			builder.append(this.optionalComment);
		}
		builder.append("~");

		key = 0;
		if (this.unitsOfSignalResponse != null) {
			key = this.unitsOfSignalResponse;
		}
		builder.append(key, 3);

		key = 0;
		if (this.unitsOfCalibrationInput != null) {
			key = this.unitsOfCalibrationInput;
		}
		builder.append(key, 3);

		builder.appendLatitude(this.latitude);
		builder.appendLongitude(this.longitude);
		builder.appendElevation(this.elevation);

		builder.appendLocal(this.localDepth);
		builder.appendAzimuth(this.azimuth);
		builder.appendDip(this.dip);

		key = 0;
		if (this.dataFormatIdentifier != null) {
			key = this.dataFormatIdentifier;
		}
		builder.append(key, 4);
		key = 0;
		if (this.dataRecordLength != null) {
			key = this.dataRecordLength;
		}
		builder.append(key, 2);
		key = 0;
		builder.append(this.sampleRate, "0.0000E-00", 10);
		builder.append(this.maxClockDrift, "0.0000E-00", 10);

		builder.append(this.numberOfComments, 4);
		builder.append(this.channelFlags).append("~");
		builder.append(this.startTime).append("~");
		if (this.endTime != null) {
			builder.append(this.endTime);
		}
		builder.append("~");
		builder.append(this.updateFlag);

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	@Override
	public String toString() {
		return "B052 [locationCode=" + locationCode + ", channelCode=" + channelCode + ", subChannelCode="
				+ subChannelCode + ", instrumentIdentifier=" + instrumentIdentifier + ", optionalComment="
				+ optionalComment + ", unitsOfSignalResponse=" + unitsOfSignalResponse + ", unitsOfCalibrationInput="
				+ unitsOfCalibrationInput + ", latitude=" + latitude + ", longitude=" + longitude + ", elevation="
				+ elevation + ", localDepth=" + localDepth + ", azimuth=" + azimuth + ", dip=" + dip
				+ ", dataFormatIdentifier=" + dataFormatIdentifier + ", dataRecordLength=" + dataRecordLength
				+ ", sampleRate=" + sampleRate + ", maxClockDrift=" + maxClockDrift + ", numberOfComments="
				+ numberOfComments + ", channelFlags=" + channelFlags + ", startTime=" + startTime + ", endTime="
				+ endTime + ", updateFlag=" + updateFlag + "]";
	}

}
