package edu.iris.dmc.seed.control.dictionary;

public class Component {
	private String stationCode;
	private String location;
	private String channelCode;
	private int subChannelId;
	private double weight;

	public Component() {
	}

	public Component(String stationCode, String location,
			String channelCode, int subChannelId, double weight) {
		super();
		this.stationCode = stationCode;
		this.location = location;
		this.channelCode = channelCode;
		this.subChannelId = subChannelId;
		this.weight = weight;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public int getSubChannelId() {
		return subChannelId;
	}

	public void setSubChannelId(int subChannelId) {
		this.subChannelId = subChannelId;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
