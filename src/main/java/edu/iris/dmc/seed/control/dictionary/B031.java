package edu.iris.dmc.seed.control.dictionary;

import edu.iris.dmc.io.SeedStringBuilder;

public class B031 extends AbstractDictionaryBlockette {
	private char classCode;
	private String description;
	private Integer unitsOfCommentLevel = 0;

	public B031() {
		this(null);
	}

	public B031(String text) {
		super(31, "Comment Description Blockette");
		this.originalText = text;
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
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder(this.getType(), 3).append("####");

		builder.append(this.lookupKey, 4).append(this.classCode);
		if (this.description != null) {
			builder.append(this.description);
		}
		builder.append("~");
		builder.append(this.unitsOfCommentLevel, 3);

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}
}
