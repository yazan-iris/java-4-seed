package edu.iris.dmc.seed.control.dictionary;

import edu.iris.dmc.seed.SeedStringBuilder;



public class B032 extends AbstractDictionaryBlockette {

	private String titleAuthor;
	private String publishedCatalog;
	private String publisherName;

	public B032(String text) {
		super(32, "Cited Source Dictionary Blockette");
		this.originalText=text;
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
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3)
				.append("####");

		builder.append(this.lookupKey, 2);
		if (this.titleAuthor != null) {
			builder.append(this.titleAuthor);
		}
		builder.append("~");

		if (this.publishedCatalog != null) {
			builder.append(this.publishedCatalog);
		}
		builder.append("~");

		if (this.publisherName != null) {
			builder.append(this.publisherName);
		}
		builder.append("~");

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

}
