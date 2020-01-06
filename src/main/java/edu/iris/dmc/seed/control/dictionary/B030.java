package edu.iris.dmc.seed.control.dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.iris.dmc.io.SeedStringBuilder;

public class B030 extends AbstractDictionaryBlockette {

	private String name;
	private int dataFamilyType;
	private List<String> decoderKeys = new ArrayList<String>();

	public B030() {
		this(null);
	}

	public B030(String text) {
		super(30, "Data Format Dictionary Blockette");
		this.originalText = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public int getDataFamilyType() {
		return dataFamilyType;
	}

	public void setDataFamilyType(int dataFamilyType) {
		this.dataFamilyType = dataFamilyType;
	}

	public List<String> getDecoderKeys() {
		return decoderKeys;
	}

	public void setDecoderKeys(List<String> decoderKeys) {
		this.decoderKeys = decoderKeys;
	}

	public void addKey(String key) {
		this.decoderKeys.add(key);
	}

	@Override
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3).append("####");

		if (this.name != null) {
			builder.append(this.name);
		}
		builder.append("~");
		builder.append(this.lookupKey, 4).append(this.dataFamilyType, 3);

		int numberOfKeys = 0;
		if (this.decoderKeys != null) {
			numberOfKeys = this.decoderKeys.size();
		}
		builder.append(numberOfKeys, 2);
		if (this.decoderKeys != null) {
			for (String s : this.decoderKeys) {
				builder.append(s).append("~");
			}
		}
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

}
