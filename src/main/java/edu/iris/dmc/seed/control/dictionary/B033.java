package edu.iris.dmc.seed.control.dictionary;

import edu.iris.dmc.io.SeedStringBuilder;

public class B033 extends AbstractDictionaryBlockette {

	private String description;

	public B033() {
		this(null);
	}

	public B033(String text) {
		super(33, "Generic Abbreviation Blockette");
		this.originalText = text;
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
