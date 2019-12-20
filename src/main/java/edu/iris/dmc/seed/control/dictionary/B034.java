package edu.iris.dmc.seed.control.dictionary;

import edu.iris.dmc.seed.SeedStringBuilder;

public class B034 extends AbstractDictionaryBlockette {

	private String name;
	private String description;

	public B034() {
		this(null);
	}

	public B034(String text) {
		super(34, "Units Abbreviations Blockette");
		this.originalText = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3).append("####");

		builder.append(this.lookupKey, 3);
		if (this.name != null) {
			builder.append(this.name);
		}
		builder.append("~");
		if (this.description != null) {
			builder.append(this.description);
		}
		builder.append("~");
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}
}
