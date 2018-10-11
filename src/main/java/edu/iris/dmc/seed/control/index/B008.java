package edu.iris.dmc.seed.control.index;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.AbstractBlockette;
import edu.iris.dmc.seed.BTime;


public class B008 extends AbstractBlockette implements IndexBlockette{

	private String version;
	private int logicalRecordLength;
	private BTime volumeStartTime;
	private BTime volumeEndTime;
	private String stationCode;
	private String locationCode;
	private String channelCode;

	private BTime stationInformationEffectiveDate;
	private BTime channelInformationEffectiveDate;
	private String networkCode;

	public B008() {
		super(8, "Telemetry Volume Identifier Blockette");
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getLogicalRecordLength() {
		return logicalRecordLength;
	}

	public void setLogicalRecordLength(int logicalRecordLength) {
		this.logicalRecordLength = logicalRecordLength;
	}

	public BTime getVolumeStartTime() {
		return volumeStartTime;
	}

	public void setVolumeStartTime(BTime startTime) {
		this.volumeStartTime = startTime;
	}

	public BTime getVolumeEndTime() {
		return volumeEndTime;
	}

	public void setVolumeEndTime(BTime endTime) {
		this.volumeEndTime = endTime;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
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

	public BTime getStationInformationEffectiveDate() {
		return stationInformationEffectiveDate;
	}

	public void setStationInformationEffectiveDate(BTime stationInformationEffectiveDate) {
		this.stationInformationEffectiveDate = stationInformationEffectiveDate;
	}

	public BTime getChannelInformationEffectiveDate() {
		return channelInformationEffectiveDate;
	}

	public void setChannelInformationEffectiveDate(BTime channelInformationEffectiveDate) {
		this.channelInformationEffectiveDate = channelInformationEffectiveDate;
	}

	public String getNetworkCode() {
		return networkCode;
	}

	public void setNetworkCode(String networkCode) {
		this.networkCode = networkCode;
	}
	@Override
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder("00" + this.getType()
				+ "####");
		if (this.version != null) {
			builder.append(this.version, 4);
		}
		builder.append(this.logicalRecordLength, 2);
		builder.append(this.stationCode, 5).append(this.locationCode, 2)
				.append(this.channelCode, 3);

		if (this.volumeStartTime != null) {
			builder.append(this.volumeStartTime);
		}
		builder.append("~");
		if (this.volumeEndTime != null) {
			builder.append(this.volumeEndTime);
		}
		builder.append("~");

		if (this.stationInformationEffectiveDate != null) {
			builder.append(this.stationInformationEffectiveDate);
		}
		builder.append("~");
		if (this.channelInformationEffectiveDate != null) {
			builder.append(this.channelInformationEffectiveDate);
		}
		builder.append("~");
		if (this.networkCode != null) {
			builder.append(this.networkCode, 2);
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

}
