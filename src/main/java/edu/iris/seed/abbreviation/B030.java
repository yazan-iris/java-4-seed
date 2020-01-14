package edu.iris.seed.abbreviation;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;

public class B030 extends AbstractAbbreviationBlockette implements AbbreviationBlockette {

	private String name;
	private int dataFamilyType;

	private List<String> decoderKeys = new ArrayList<String>();

	public B030() {
		this(null);
	}

	public B030(String text) {
		super(30, "Data Format Dictionary Blockette");
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
	public int hashCode() {
		return Objects.hash(dataFamilyType, decoderKeys, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		B030 other = (B030) obj;
		return dataFamilyType == other.dataFamilyType && Objects.equals(decoderKeys, other.decoderKeys)
				&& Objects.equals(name, other.name);
	}

	@Override
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3).append("####");

		if (this.name != null) {
			builder.append(this.name.substring(0, Math.min(this.name.length(), 50)));
		}
		builder.append("~");
		builder.append(this.getLookupKey(), 4).append(this.dataFamilyType, 3);

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

	public static B030 build(byte[] bytes) throws SeedException {
		B030 b = new B030(new String(bytes));

		int offset = 7;

		int from = offset;
		for (;; offset++) {
			if (bytes[offset] == '~') {
				break;
			}
		}

		b.setName(new String(bytes, from, offset - from));
		offset++;
		b.setLookupKey(SeedStrings.parseInt(bytes, offset, 4));
		offset = offset + 4;
		b.setDataFamilyType(SeedStrings.parseInt(bytes, offset, 3));
		offset = offset + 3;
		int numberOfKeys = SeedStrings.parseInt(bytes, offset, 2);
		offset = offset + 2;
		int i = offset;
		for (; offset < bytes.length; offset++) {
			if (bytes[offset] == '~') {
				b.addKey(new String(bytes, i, offset - i));
				i = offset + 1;
			}
		}

		return b;
	}


}
