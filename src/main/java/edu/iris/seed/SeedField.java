package edu.iris.seed;

public class SeedField {
	private int number;
	private String description;
	private String type;
	private int minumumLength;
	private int maximumLength;
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

	public int getMinumumLength() {
		return minumumLength;
	}

	public void setMinumumLength(int minumumLength) {
		this.minumumLength = minumumLength;
	}

	public int getMaximumLength() {
		return maximumLength;
	}

	public void setMaximumLength(int maximumLength) {
		this.maximumLength = maximumLength;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	@Override
	public String toString() {
		return "SeedField [number=" + number + ", description=" + description + ", type=" + type + ", minumumLength="
				+ minumumLength + ", maximumLength=" + maximumLength + ", mask=" + mask + "]";
	}

}
