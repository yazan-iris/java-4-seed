package edu.iris.seed.data;

import java.util.HashMap;
import java.util.Map;

public enum EncodingFormat {


	ASCII(0), SHORT(1), INT24(2), INTEGER(3), FLOAT(4), DOUBLE(5), STEIM_1(10), STEIM_2(11), CDSN(16), SRO(30), DWWSSN(
			32);
	private int value;

	private static Map<Integer, EncodingFormat> map = new HashMap<Integer, EncodingFormat>();
	static {
		for (EncodingFormat format : EncodingFormat.values()) {
			map.put(format.value, format);
		}
	}

	EncodingFormat(int value) {
		this.value = value;
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

}
