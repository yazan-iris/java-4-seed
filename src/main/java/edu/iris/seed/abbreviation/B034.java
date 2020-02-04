package edu.iris.seed.abbreviation;

import java.util.Objects;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;
import lombok.Getter;

@Getter
public class B034 extends AbstractAbbreviationBlockette<B034> implements AbbreviationBlockette {

	private String name;
	private String description;

	public B034() {
		super(34, "Units Abbreviations Blockette");
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

		builder.append(this.getLookupKey(), 3);
		if (this.name != null) {
			builder.append(this.name.substring(0, Math.min(this.name.length(), 20)));
		}
		builder.append("~");
		if (this.description != null) {
			builder.append(this.description.substring(0, Math.min(this.description.length(), 50)));
		}
		builder.append("~");
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public BlocketteBuilder<B034> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B034> {

		private String name;
		private String description;
		private int lookupKey;

		public Builder() {
			super(34);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder lookupKey(int lookupKey) {
			this.lookupKey=lookupKey;
			return this;
		}

		public B034 build() throws SeedException {
			B034 b = new B034();
			if (bytes == null) {
				b.description = description;
				b.name = name;
				b.setLookupKey(lookupKey);
			} else {
				int offset = 7;

				b.setLookupKey(SeedStrings.parseInt(bytes, offset, 3));
				offset += 3;
				int i = offset;
				for (; offset < bytes.length; offset++) {
					if (bytes[offset] == (byte) '~') {
						break;
					}
				}

				b.setName(new String(bytes, i, offset - i));
				offset++;

				i = offset;
				for (; offset < bytes.length; offset++) {
					if (bytes[offset] == (byte) '~') {
						break;
					}
				}

				b.setDescription(new String(bytes, i, offset - i));
			}

			return b;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(description, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		B034 other = (B034) obj;
		return Objects.equals(description, other.description) && Objects.equals(name, other.name);
	}

}
