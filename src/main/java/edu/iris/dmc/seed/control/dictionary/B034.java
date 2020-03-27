package edu.iris.dmc.seed.control.dictionary;

import edu.iris.dmc.io.SeedStringBuilder;

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
			if(this.name.length()>20) {
				this.name = this.name.substring(0, Math.min(this.name.length(), 20));
			}
			builder.append(this.name);
		}
		builder.append("~");
		if (this.description != null) {
			if(this.description.length()>50) {
				this.description = this.description.substring(0, Math.min(this.description.length(), 50));
			}
			builder.append(this.description);
		}
		builder.append("~");
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}
}
