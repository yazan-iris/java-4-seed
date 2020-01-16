package edu.iris.seed.abbreviation;

import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;

public class B035 extends AbstractAbbreviationBlockette implements AbbreviationBlockette {

	private List<Component> components = new ArrayList<Component>();

	public B035() {
		super(35, "Beam Configuration Blockette");
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	public void add(Component comp) {
		this.components.add(comp);
	}


	@Override
	public String toSeedString() throws SeedException {

		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3).append("####");

		builder.append(this.getLookupKey(), 3);

		builder.append(this.components.size(), 4);

		for (Component component : this.components) {
			builder.append(component.getStationCode(), 5);
			builder.append(component.getLocation(), 2);
			builder.append(component.getChannelCode(), 3);
			builder.append(component.getSubChannelId(), 4);
			builder.append("" + component.getWeight(), 5);
		}
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public static B035 build(byte[] bytes) throws SeedException {
		validate(35, 14, bytes);
		int offset = 7;
		B035 b = new B035();

		b.setLookupKey(SeedStrings.parseInt(bytes, offset, 3));
		offset += 3;

		int numberOfComponents = SeedStrings.parseInt(bytes, offset, 4);
		offset += 4;

		for (int i = 0; i < numberOfComponents; i++) {
			String stationCode = new String(bytes, offset, 5);
			offset = offset + 5;
			String locationCode = new String(bytes, offset, 2);
			offset = offset + 2;
			String channelCode = new String(bytes, offset, 3);
			offset = offset + 3;

			int subChannelId = SeedStrings.parseInt(bytes, offset, 4);
			offset = offset + 4;
			double weight = SeedStrings.parseDouble(bytes, offset, 5);
			offset = offset + 5;
			b.add(b.new Component(stationCode, locationCode, channelCode, subChannelId, weight));
		}

		return b;
	}

	class Component {
		private String stationCode;
		private String location;
		private String channelCode;
		private int subChannelId;
		private double weight;

		public Component() {
		}

		public Component(String stationCode, String location, String channelCode, int subChannelId, double weight) {
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

}
