package edu.iris.seed.lang.math;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;

import edu.iris.seed.SeedException;

public class SeedNumbers {

	public static short readUnsignedShort(byte[] bytes, int offset) {
		return (short) (((bytes[offset] << 8) & 0x0000ff00) | (bytes[offset + 1] & 0x000000ff));
	}

	public static int readInt(byte[] buf, int begin, int length) throws SeedException {
		if (length < 4) {
			throw new SeedException("Expected {} bytes but found {}", 4, length);
		}

		byte[] b = new byte[length];
		for (int i = 0; i < length; i++) {
			b[i] = buf[begin + i];
		}

		int i = b[0] << 24 | (b[1] & 0xFF) << 16 | (b[2] & 0xFF) << 8 | (b[3] & 0xFF);
		return i;
	}

	static public String formatScientific(String pattern, String positive, Double value) {
		ScientificDecimalFormat formatter = new ScientificDecimalFormat(pattern);

		formatter.setPositivePrefix(positive);
		if (value == null) {
			return formatter.format(0);
		}
		return formatter.format(value);
	}

	private static class ScientificDecimalFormat extends NumberFormat {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1838009550163390117L;
		private final DecimalFormat decimal;

		public ScientificDecimalFormat(String pattern) {
			decimal = new DecimalFormat(pattern);
			decimal.setRoundingMode(RoundingMode.HALF_UP);
		}

		public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition pos) {
			StringBuffer sb = new StringBuffer();
			sb.append(modified(number, Math.abs(number) >= 1.0, decimal.format(number, toAppendTo, pos).toString()));
			return sb;
		}

		private String modified(double num, boolean large, String s) {
			if (large) {
				return s.replace("E", "E+");
			} else {
				if (num == 0) {
					return s.replace("E", "E+");
				} else {
					return s;
				}
			}
		}

		public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition pos) {
			StringBuffer sb = new StringBuffer();
			sb.append(modified(number, true, decimal.format(number, toAppendTo, pos).toString()));
			return sb;
		}

		public Number parse(String source, ParsePosition parsePosition) {
			return decimal.parse(source, parsePosition);
		}

		public void setPositivePrefix(String newValue) {
			decimal.setPositivePrefix(newValue);
		}
	}

}
