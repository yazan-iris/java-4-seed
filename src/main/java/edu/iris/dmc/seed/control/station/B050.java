package edu.iris.dmc.seed.control.station;

import java.util.ArrayList;
import java.util.List;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.AbstractBlockette;
import edu.iris.dmc.seed.BTime;
import edu.iris.dmc.seed.SeedException;

public class B050 extends AbstractBlockette implements StationBlockette {

	private String stationCode;
	private double latitude;

	private double longitude;

	private double elevation;

	private int numberOfChannels;

	private int numberOfComments;

	private String siteName;

	private int networkIdentifierCode;
	private int bit32BitOrder;
	private int bit16BitOrder;
	private BTime startTime;
	private BTime endTime;
	private char updateFlag;
	private String networkCode;

	private List<B052> b052s = new ArrayList<>();
	private List<B051> b051s = new ArrayList<>();
	public B050() {
		this(null);
	}

	public B050(String text) {
		super(50, "Station Identifier Blockette");
		this.originalText = text;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
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

	public int getNumberOfChannels() {
		return numberOfChannels;
	}

	public void setNumberOfChannels(int numberOfChannels) {
		this.numberOfChannels = numberOfChannels;
	}

	public int getNumberOfComments() {
		return numberOfComments;
	}

	public void setNumberOfComments(int numberOfComments) {
		this.numberOfComments = numberOfComments;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public int getNetworkIdentifierCode() {
		return networkIdentifierCode;
	}

	public void setNetworkIdentifierCode(int networkIdentifierCode) {
		this.networkIdentifierCode = networkIdentifierCode;
	}

	public int getBit32BitOrder() {
		return bit32BitOrder;
	}

	public void setBit32BitOrder(int bit32BitOrder) {
		this.bit32BitOrder = bit32BitOrder;
	}

	public int getBit16BitOrder() {
		return bit16BitOrder;
	}

	public void setBit16BitOrder(int bit16BitOrder) {
		this.bit16BitOrder = bit16BitOrder;
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

	public String getNetworkCode() {
		return networkCode;
	}

	public void setNetworkCode(String networkCode) {
		this.networkCode = networkCode;
	}

	public List<B052> getB052s() {
		return b052s;
	}

	public void addAll(List<B052> b052s) {
		this.b052s.addAll(b052s);
	}

	public void add(B051 b051) {
		this.b051s.add(b051);
	}
	
	public void add(B052 b052) {
		this.b052s.add(b052);
	}

	public List<B051> getB051s() {
		return b051s;
	}

	public String toSeedString() throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.stationCode, 5);
		builder.appendLatitude(this.latitude);
		builder.appendLongitude(this.longitude);
		builder.appendElevation(this.elevation);
		builder.append(this.numberOfChannels, 4);
		builder.append(this.numberOfComments, 3);
		builder.append(this.siteName).append("~");
		builder.append(this.networkIdentifierCode, 3);
		builder.append(this.bit32BitOrder, 4);
		builder.append(this.bit16BitOrder, 2);
		builder.append(this.startTime).append("~");
		if (this.endTime != null) {
			builder.append(this.endTime);
		}
		builder.append("~");
		builder.append(this.updateFlag);
		builder.append(this.networkCode,2);
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	@Override
	public String toString() {
		return "B050 [stationCode=" + stationCode + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", elevation=" + elevation + ", numberOfChannels=" + numberOfChannels + ", numberOfComments="
				+ numberOfComments + ", siteName=" + siteName + ", networkIdentifierCode=" + networkIdentifierCode
				+ ", bit32BitOrder=" + bit32BitOrder + ", bit16BitOrder=" + bit16BitOrder + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", updateFlag=" + updateFlag + ", networkCode=" + networkCode + "]";
	}

}
