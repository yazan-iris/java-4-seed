package edu.iris.seed.abbreviation;

import java.util.Objects;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;

public class B032 extends AbstractAbbreviationBlockette<B032> implements AbbreviationBlockette {

	private String titleAuthor;
	private String publishedCatalog;
	private String publisherName;

	public B032(String text) {
		super(32, "Cited Source Dictionary Blockette");
	}

	public String getTitleAuthor() {
		return titleAuthor;
	}

	public void setTitleAuthor(String titleAuthor) {
		this.titleAuthor = titleAuthor;
	}

	public String getPublishedCatalog() {
		return publishedCatalog;
	}

	public void setPublishedCatalog(String publishedCatalog) {
		this.publishedCatalog = publishedCatalog;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(publishedCatalog, publisherName, titleAuthor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		B032 other = (B032) obj;
		return Objects.equals(publishedCatalog, other.publishedCatalog)
				&& Objects.equals(publisherName, other.publisherName) && Objects.equals(titleAuthor, other.titleAuthor);
	}

	@Override
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3).append("####");

		builder.append(this.getLookupKey(), 2);
		if (this.titleAuthor != null) {
			builder.append(this.titleAuthor.substring(0, Math.min(this.titleAuthor.length(), 70)));
		}
		builder.append("~");

		if (this.publishedCatalog != null) {
			builder.append(this.publishedCatalog.substring(0, Math.min(this.publishedCatalog.length(), 70)));
		}
		builder.append("~");

		if (this.publisherName != null) {
			builder.append(this.publisherName.substring(0, Math.min(this.publisherName.length(), 50)));
		}
		builder.append("~");

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public BlocketteBuilder<B032> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B032> {

		public Builder() {
			super(32);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B032 build() throws SeedException { 
			B032 b = new B032(new String(bytes));

			int offset = 7;

			b.setLookupKey(SeedStrings.parseInt(bytes, offset, 2));
			offset = offset + 2;

			int i = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.setTitleAuthor(new String(bytes, i, offset - i));
			offset++;

			i = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.setPublishedCatalog(new String(bytes, i, offset - i));
			offset++;

			i = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.setPublisherName(new String(bytes, i, offset - i));
			return b;
		}
	}
}
