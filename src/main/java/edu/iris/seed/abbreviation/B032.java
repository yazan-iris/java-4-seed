package edu.iris.seed.abbreviation;

import java.time.ZonedDateTime;

import edu.iris.seed.BTime;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;

public class B032 extends AbstractAbbreviationBlockette<B032> implements AbbreviationBlockette {

	private String title;
	private String author;
	private BTime datePublished;
	private String catalog;
	private String publisherName;

	public B032() {
		super(32, "Cited Source Dictionary Blockette");
	}

	public String getTitle() {
		return title;
	}

	public String getAuthor() {
		return author;
	}

	public String getCatalog() {
		return catalog;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public BTime getDatePublished() {
		return datePublished;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((catalog == null) ? 0 : catalog.hashCode());
		result = prime * result + ((datePublished == null) ? 0 : datePublished.hashCode());
		result = prime * result + ((publisherName == null) ? 0 : publisherName.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
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
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (catalog == null) {
			if (other.catalog != null)
				return false;
		} else if (!catalog.equals(other.catalog))
			return false;
		if (datePublished == null) {
			if (other.datePublished != null)
				return false;
		} else if (!datePublished.equals(other.datePublished))
			return false;
		if (publisherName == null) {
			if (other.publisherName != null)
				return false;
		} else if (!publisherName.equals(other.publisherName))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3).append("####");

		builder.append(this.getLookupKey(), 2);
		String titleAuthor = "";
		if (this.title != null) {
			titleAuthor = title;
		}
		if (this.author != null) {
			titleAuthor = titleAuthor + "," + author;
		}

		if (titleAuthor != null) {
			builder.append(titleAuthor.substring(0, Math.min(titleAuthor.length(), 70)));
		}
		builder.append("~");

		String publishedCatalog = "";
		if (this.catalog != null) {
			publishedCatalog = catalog;
		}
		if (this.datePublished != null) {
			publishedCatalog = publishedCatalog + "" + datePublished.toSeedString();
		}

		if (publishedCatalog != null) {
			builder.append(publishedCatalog.substring(0, Math.min(publishedCatalog.length(), 70)));
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

		private String title;
		private String author;
		private BTime btime;
		private String catalog;
		private String publisher;

		public Builder() {
			super(32);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder author(String author) {
			this.author = author;
			return this;
		}

		public Builder catalog(String catalog) {
			this.catalog = catalog;
			return this;
		}

		public Builder date(ZonedDateTime dateTime) {
			this.btime = BTime.from(dateTime);
			return this;
		}

		public Builder date(int year, int dayOfYear, int hour, int minute, int second, int millis) {
			this.btime = BTime.from(year, dayOfYear, hour, minute, second, millis);
			return this;
		}

		public Builder publisher(String publisher) {
			this.publisher = publisher;
			return this;
		}

		private B032 buildFromBytes() throws SeedException {
			B032 b = new B032();

			int offset = 7;

			b.setLookupKey(SeedStrings.parseInt(bytes, offset, 2));
			offset = offset + 2;

			int i = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.title = new String(bytes, i, offset - i);
			offset++;

			i = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.catalog = new String(bytes, i, offset - i);
			offset++;

			i = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == '~') {
					break;
				}
			}
			b.publisherName = new String(bytes, i, offset - i);
			return b;
		}

		private B032 buildFromValues() {
			B032 b = new B032();
			b.author = this.author;
			b.title = title;
			b.datePublished = this.btime;
			b.catalog = this.catalog;
			b.publisherName = this.publisher;
			return b;
		}

		public B032 build() throws SeedException {
			if (bytes != null) {
				return buildFromBytes();
			} else {
				return buildFromValues();
			}

		}
	}
}
