package edu.iris.dmc.io;

import java.text.DecimalFormat;

import edu.iris.dmc.seed.BTime;
import edu.iris.dmc.seed.SeedException;
import edu.iris.dmc.seed.control.station.Pole;
import edu.iris.dmc.seed.control.station.Zero;

public class SeedStringBuilder {

	private StringBuilder builder = new StringBuilder();

	public SeedStringBuilder() {

	}

	public SeedStringBuilder(String s) {
		builder.append(s);
	}

	public SeedStringBuilder(int num, int length) {
		append(num, length);
	}

	public SeedStringBuilder append(String s) {
		if (s != null) {
			builder.append(s);
		}
		return this;
	}

	public SeedStringBuilder append(char c) {
		builder.append(c);
		return this;
	}

	public SeedStringBuilder append(String s, int length) throws SeedException {
		if (s == null || s.length() > length) {
			throw new SeedException("Invalid text " + s + " [" + length + "]");
		}
		if (s != null) {
			builder.append(String.format("%-" + length + "s", s));
		}
		return this;
	}

	public SeedStringBuilder append(int num, int length) {
		builder.append(String.format("%0" + length + "d", num));
		return this;
	}

	public SeedStringBuilder append(BTime time) {
		if (time == null) {
			append("");
			return this;
		}

		builder.append(time.getYear());

		append(",").leftPad(time.getDayOfYear(), 3, '0');

		append(",").leftPad(time.getHour(), 2, '0');

		append(":").leftPad(time.getMinute(), 2, '0');

		append(":").leftPad(time.getSecond(), 2, '0');

		append(".").leftPad(time.getTenthMilliSecond(), 4, '0');
		return this;
	}

	public SeedStringBuilder append(Float value, String format, int width) {
		boolean addSign = false;
		if (format.startsWith("#")) {
			format = format.substring(1);
			addSign = true;
		}
		DecimalFormat df = new DecimalFormat(format);
		// String s = df.format(value);
		String s = df.format(value);

		if (addSign && !s.startsWith("-")) {
			s = "+" + s;
		}

		if (!s.contains("E-")) {
			s = s.replace("E", "E+");
		}

		if (s.length() != width) {
			throw new NumberFormatException("Couldn't format number!" + value + "   " + s);
		}
		builder.append(s);
		return this;
	}

	public SeedStringBuilder append(Zero zero, String format, int width) {
		if (zero == null) {
			this.append(0, format, width);
			this.append(0, format, width);
			this.append(0, format, width);
			this.append(0, format, width);
			return this;
		}
		if (zero.getReal() == null) {
			this.append(0, format, width);
		} else {
			this.append(zero.getReal().getValue(), format, width);
		}
		if (zero.getImaginary() == null) {
			this.append(0, format, width);
		} else {
			this.append(zero.getImaginary().getValue(), format, width);
		}

		if (zero.getReal() == null) {
			this.append(0, format, width);
		} else {
			this.append(zero.getReal().getError(), format, width);
		}
		if (zero.getImaginary() == null) {
			this.append(0, format, width);
		} else {
			this.append(zero.getImaginary().getError(), format, width);
		}
		return this;
	}

	public SeedStringBuilder append(Pole pole, String format, int width) {
		if (pole == null) {
			this.append(0, format, width);
			this.append(0, format, width);
			this.append(0, format, width);
			this.append(0, format, width);
			return this;
		}
		if (pole.getReal() == null) {
			this.append(0, format, width);
		} else {
			this.append(pole.getReal().getValue(), format, width);
		}
		if (pole.getImaginary() == null) {
			this.append(0, format, width);
		} else {
			this.append(pole.getImaginary().getValue(), format, width);
		}

		if (pole.getReal() == null) {
			this.append(0, format, width);
		} else {
			this.append(pole.getReal().getError(), format, width);
		}
		if (pole.getImaginary() == null) {
			this.append(0, format, width);
		} else {
			this.append(pole.getImaginary().getError(), format, width);
		}
		return this;
	}

	public SeedStringBuilder appendLatitude(double value) {
		DecimalFormat df = new DecimalFormat("+#,#00.000000;-#");
		String text = df.format(value);
		if (text.startsWith("+")) {
			// text=text.replaceFirst("\\+", " ");
		}
		builder.append(text);
		return this;
	}

	public SeedStringBuilder appendLongitude(double value) {
		DecimalFormat df = new DecimalFormat("+#,#000.000000;-#");
		String text = df.format(value);
		if (text.startsWith("+")) {
			// text=text.replaceFirst("\\+", " ");
		}
		builder.append(text);
		return this;
	}

	public SeedStringBuilder appendElevation(double value) throws SeedException {
		DecimalFormat df = new DecimalFormat("+#,#0000.0;-#");
		if (value < -9999) {
			df = new DecimalFormat("+#000000;-#");
		}

		String text = df.format(value);
		if (text.length() > 7) {
			throw new SeedException("Value " + text + " too big");
		}
		builder.append(text);
		return this;
	}

	public SeedStringBuilder appendLocalDepth(double value) {
		if (value > 99999 || value < -9999) {
			throw new NumberFormatException("Couldn't format number:" + value);
		}
		DecimalFormat df = null;
		if (value % 1 != 0) {
			if (value < 0) {
				if (value <= -100) {
					value = Math.round(value);
					df = new DecimalFormat("0000;");
				} else {
					df = new DecimalFormat("00.0");
				}

			} else {
				df = new DecimalFormat("000.0;");
			}
		} else {
			if (value < 0) {
				df = new DecimalFormat("0000;");
			} else {
				df = new DecimalFormat("00000;");
			}
		}

		String text = df.format(value);
		if (text.length() > 5) {
			throw new NumberFormatException(
					"Couldn't format number:" + value + "   " + text + " [" + 5 + "  " + text.length() + " ]");
		}
		builder.append(text);
		return this;
	}

	public SeedStringBuilder appendAzimuth(double value) {
		DecimalFormat df = new DecimalFormat("000.0");
		String text = df.format(value);
		if (text.length() > 5) {
			throw new NumberFormatException(
					"Couldn't format number!" + value + "   " + text + " [" + 5 + "  " + text.length() + " ]");
		}
		builder.append(text);
		return this;
	}

	public SeedStringBuilder appendDip(double value) {
		DecimalFormat df = new DecimalFormat("+#,#00.0;-#");
		String text = df.format(value);
		if (text.startsWith("+")) {
			// text=text.replaceFirst("\\+", " ");
		}
		if (text.length() > 5) {
			throw new NumberFormatException(
					"Couldn't format number!" + value + "   " + text + " [" + 5 + "  " + text.length() + " ]");
		}
		builder.append(text);
		return this;
	}

	public SeedStringBuilder append(double value, String format, int width) {
		String text = null;
		String pattern = format;
		boolean signed = false;
		if (pattern.startsWith("-")) {
			pattern = pattern.substring(1);
			signed = true;
		} else {
			if (value < 0) {
				throw new NumberFormatException("Invalid format " + format + " for negative number " + value);
			}
		}

		if (pattern.contains("E-")) {
			pattern = pattern.replace("E-", "E");
			DecimalFormat df = new DecimalFormat(pattern);
			text = df.format(value);
			if (!text.contains("E-")) {
				text = text.replaceAll("E", "E+");
			}
		}
		if (signed) {
			if (!text.startsWith("-") && !text.startsWith("+")) {
				if (value >= 0 && signed) {
					text = "+" + text;
				}
			}
		}

		if (text.length() != width) {
			throw new NumberFormatException("Couldn't format number, value: " + value + " format: " + format
					+ " pattern: " + pattern + " actual: " + text + " [" + width + "  " + text.length() + " ]");
		}
		builder.append(text);
		return this;
	}

	private String modified(boolean large, String s) {
		return large ? s.replace("E", "E+") : s;
	}

	public SeedStringBuilder replace(int start, int end, int num, String mask) {
		builder.replace(start, end, String.format("%0" + mask.length() + "d", num));
		return this;
	}

	public int length() {
		return builder.length();
	}

	public String toString() {
		return builder.toString();
	}

	public SeedStringBuilder leftPad(String string, int length, char kar) {

		int width = string.length();
		if (width >= length) {
			this.builder.append(string);
			return this;
		}
		int remainder = length - width;

		for (int i = 0; i < remainder; i++) {
			append(kar);
		}
		this.builder.append(string);
		return this;
	}

	public SeedStringBuilder leftPad(int value, int length, char kar) {
		int width = length(value);
		if (width >= length) {
			this.builder.append(value);
			return this;
		}
		int remainder = length - width;

		for (int i = 0; i < remainder; i++) {
			append(kar);
		}
		this.builder.append(value);
		return this;
	}

	private int length(int num) {
		if (num < 1) {
			return 1;
		}
		int i = 0;
		for (;; i++) {
			if (num < 1) {
				break;
			}
			num = num / 10;
		}
		return i;
	}

	public SeedStringBuilder rightPad(String string, int length, char kar) {

		int width = string.length();
		if (width >= length) {
			this.builder.append(string);
			return this;
		}
		this.builder.append(string);
		int remainder = length - width;

		for (int i = 0; i < remainder; i++) {
			append(kar);
		}

		return this;
	}

	public SeedStringBuilder rightPad(int num, int length, char kar) {
		int width = length(num);
		if (width >= length) {
			this.builder.append(num);
			return this;
		}
		this.builder.append(num);
		int remainder = length - width;

		for (int i = 0; i < remainder; i++) {
			append(kar);
		}

		return this;
	}
}
