package edu.iris.seed.data;

import java.util.HashMap;
import java.util.Map;

public enum EncodingFormat {

	STEIM_1(10, ""), STEIM_2(11, "");
	private int value;
	private String description;

	private static Map<Integer, EncodingFormat> map = new HashMap<Integer, EncodingFormat>();
	static {
		for (EncodingFormat format : EncodingFormat.values()) {
			map.put(format.value, format);
		}
	}

	EncodingFormat(int value, String description) {
		this.value = value;
		this.description = description;
	}

	public static EncodingFormat valueOf(int format) {
		return map.get(format);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
