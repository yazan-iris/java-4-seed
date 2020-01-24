package edu.iris.seed.abbreviation;

import java.util.Objects;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;

public class B031 extends AbstractAbbreviationBlockette<B031> implements AbbreviationBlockette {
	private char classCode;
	private String description;
	private Integer unitsOfCommentLevel = 0;

	public B031() {
		this(null);
	}

	public B031(String text) {
		super(31, "Comment Description Blockette");
	}

	public char getClassCode() {
		return classCode;
	}

	public void setClassCode(char classCode) {
		this.classCode = classCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getUnitsOfCommentLevel() {
		return unitsOfCommentLevel;
	}

	public void setUnitsOfCommentLevel(Integer unitsOfCommentLevel) {
		this.unitsOfCommentLevel = unitsOfCommentLevel;
	}

	@Override
	public int hashCode() {
		return Objects.hash(classCode, description, unitsOfCommentLevel);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		B031 other = (B031) obj;
		return classCode == other.classCode && Objects.equals(description, other.description)
				&& Objects.equals(unitsOfCommentLevel, other.unitsOfCommentLevel);
	}

	@Override
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3).append("####");

		builder.append(this.getLookupKey(), 4).append(this.classCode);
		if (this.description != null) {
			builder.append(this.description.substring(0, Math.min(this.description.length(), 70)));
		}
		builder.append("~");
		builder.append(this.unitsOfCommentLevel, 3);

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public BlocketteBuilder<B031> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B031> {

		public Builder() {
			super(31);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B031 build(boolean relax) throws SeedException {
			B031 b = new B031(new String(bytes));

			int offset = 7;

			b.setLookupKey(SeedStrings.parseInt(bytes, offset, 4));
			offset = offset + 4;
			b.setClassCode((char) bytes[offset]);
			offset++;

			int i = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.setDescription(new String(bytes, i, offset - i));
			offset++;
			b.setUnitsOfCommentLevel(SeedStrings.parseInt(bytes, offset, 3));
			return b;
		}
	}
}
