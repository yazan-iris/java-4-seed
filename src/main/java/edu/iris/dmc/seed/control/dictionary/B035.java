package edu.iris.dmc.seed.control.dictionary;

import java.util.ArrayList;
import java.util.List;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.SeedException;



public class B035 extends AbstractDictionaryBlockette {

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

		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3)
				.append("####");

		builder.append(this.lookupKey, 3);

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
}
