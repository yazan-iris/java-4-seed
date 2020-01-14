package edu.iris.seed;

public class SeedField {
	private int number;
	private String description;
	private String type;
	private String length;
	private String mask;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	@Override
	public String toString() {
		return "Field [number=" + number + ", description=" + description + ", type=" + type + ", length=" + length
				+ ", mask=" + mask + "]";
	}

}
