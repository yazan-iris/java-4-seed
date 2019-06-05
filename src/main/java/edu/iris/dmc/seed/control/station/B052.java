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

	private int subChannelCode;

	private int instrumentIdentifier;

	private String optionalComment;

	private int unitsOfSignalResponse;
	private int unitsOfCalibrationInput;

	private double latitude;
	private double longitude;
	private double elevation;
	private double localDepth;// meter
	private double azimuth;// degrees
	private double dip;// degrees
	private int dataFormatIdentifier;
	private int dataRecordLength;
	private double sampleRate;// Hz
	private double maxClockDrift;// seconds
	private int numberOfComments = 0;
	private String channelFlags;
	private BTime startTime;
	private BTime endTime;
	private char updateFlag;

	private List<B059> b059s = new ArrayList<>();
	private Map<Integer, SeedResponseStage> stages = new TreeMap<>(new Comparator<Integer>() {

		@Override
		public int compare(Integer o1, Integer o2) {
			if (o1 == 0) {
				return 1;
			}
			if (o2 == 0) {
				return -1;
			}
			return Integer.compare(o1, o2);
		}
	});

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

	public void add(ResponseBlockette blockette) throws SeedException {
		int sequence = blockette.getStageSequence();
		SeedResponseStage stage = this.stages.get(sequence);
		if (stage == null) {
			stage = new SeedResponseStage(sequence);
			this.stages.put(sequence, stage);
		}
		stage.add(blockette);
	}

	public SeedResponseStage getResponseStage(int sequence) {
		return this.stages.get(sequence);
	}

	public List<SeedResponseStage> getResponseStages() {
		return new ArrayList<>(this.stages.values());
	}

	@Override
	public String toSeedString() throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.locationCode, 2);
		builder.append(this.channelCode, 3);

		builder.append(this.subChannelCode, 4);
		builder.append(this.instrumentIdentifier, 3);
		if (this.optionalComment != null) {
			builder.append(this.optionalComment);
		}
		builder.append("~");

		builder.append(this.unitsOfSignalResponse, 3);

		builder.append(this.unitsOfCalibrationInput, 3);

		builder.appendLatitude(this.latitude);
		builder.appendLongitude(this.longitude);
		builder.appendElevation(this.elevation);

		builder.appendLocalDepth(this.localDepth);
		builder.appendAzimuth(this.azimuth);
		builder.appendDip(this.dip);

		builder.append(this.dataFormatIdentifier, 4);
		builder.append(this.dataRecordLength, 2);

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
