package edu.iris.seed.abbreviation;

import java.util.Objects;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;

public class B033 extends AbstractAbbreviationBlockette implements AbbreviationBlockette {

	private String description;

	public B033() {
		this(null);
	}

	public B033(String text) {
		super(33, "Generic Abbreviation Blockette");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(description);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		B033 other = (B033) obj;
		return Objects.equals(description, other.description);
	}

	@Override
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3).append("####");

		builder.append(this.getLookupKey(), 3);
		if (this.description != null) {
			builder.append(this.description.substring(0, Math.min(this.description.length(), 50)));
		}
		builder.append("~");
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public BlocketteBuilder<B033> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B033> {

		public Builder() {
			super(33);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B033 build() throws SeedException {
			int offset = 7;
			B033 b = new B033(new String(bytes));

			Integer lookupCode = SeedStrings.parseInt(bytes, offset, 3);
			b.setLookupKey(lookupCode);
			offset += 3;
			int i = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == (byte) '~') {
					break;
				}
			}
			b.setDescription(new String(bytes, i, offset - i));
			return b;
		}
	}

}
