package edu.iris.seed.Identifier;

import java.util.Arrays;

import edu.iris.seed.BTime;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedBlockette;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;

public class B008 extends SeedBlockette<B008> implements IdentifierBlockette {

	public static final int TYPE = 8;
	public static final int MINUMUM_LENGTH = 25;
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
	public String toSeedString() throws SeedException {

		SeedStringBuilder builder = new SeedStringBuilder("00" + this.getType() + "####");
		if (this.version != null) {
			builder.append(this.version, 4);
		}
		builder.append(this.logicalRecordLength, 2);
		builder.append(this.stationCode, 5).append(this.locationCode, 2).append(this.channelCode, 3);

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

	public BlocketteBuilder<B008> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B008> {

		public Builder() {
			super(8);
		}
		public static Builder newInstance() {
			return new Builder();
		}

		public B008 build() throws SeedException {

			validate(TYPE, MINUMUM_LENGTH, bytes);
			B008 b = new B008();
			b.setVersion(new String(bytes, 7, 4));
			int recordLength = Integer.parseInt(new String(bytes, 11, 2));
			b.setLogicalRecordLength(recordLength);
			int offset = 13;

			b.setStationCode(new String(bytes, offset, 5));
			offset = offset + 5;
			b.setLocationCode(new String(bytes, offset, 2));
			offset = offset + 2;
			b.setChannelCode(new String(bytes, offset, 3));
			offset = offset + 3;
			int from = offset;
			for (;; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.setVolumeStartTime(BTime.valueOf(Arrays.copyOfRange(bytes, from, offset)));
			offset++;
			from = offset;
			for (;; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.setVolumeEndTime(BTime.valueOf(Arrays.copyOfRange(bytes, from, offset)));
			offset++;
			from = offset;
			for (;; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.setStationInformationEffectiveDate(BTime.valueOf(Arrays.copyOfRange(bytes, from, offset)));
			offset++;
			from = offset;
			for (;; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.setChannelInformationEffectiveDate(BTime.valueOf(Arrays.copyOfRange(bytes, from, offset)));
			offset++;
			b.setNetworkCode(new String(bytes, offset, 2));
			return b;
		}
	}
}
