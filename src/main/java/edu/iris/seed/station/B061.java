package edu.iris.seed.station;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;

public class B061 extends AbstractResponseBlockette implements Splittable {

	private String name;
	private char symetryCode;

	private List<Double> coefficients = new ArrayList<>();

	public B061() {
		super(61, "FIR Response Blockette");

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getSymetryCode() {
		return symetryCode;
	}

	public void setSymetryCode(char symetryCode) {
		this.symetryCode = symetryCode;
	}

	public List<Double> getCoefficients() {
		return coefficients;
	}

	public void addCoefficient(Double c) {
		if (c == null) {
			return;
		}
		this.coefficients.add(c);
	}

	private boolean shouldSplit(B061 blockette) {
		int minumumLength = 1;
		if (this.name != null) {
			minumumLength = this.name.getBytes(StandardCharsets.US_ASCII).length;
		}
		minumumLength += 20;
		if (blockette.getCoefficients().size() > 9999
				|| (minumumLength + (14 * (blockette.getCoefficients().size())) > 9999)) {
			return true;
		}
		return false;
	}

	@Override
	public List<B061> split() throws SeedException {

		List<B061> list = new ArrayList<>();
		if (shouldSplit(this)) {
			int minumumLength = 1;
			if (this.name != null) {
				minumumLength = this.name.getBytes(StandardCharsets.US_ASCII).length;
			}
			minumumLength += 20;

			int mod = (9999 - minumumLength) / 14;
			B061 b = null;
			int i = 0;
			for (; i < this.coefficients.size(); i++) {
				if (i % mod == 0) {
					b = new B061();
					b.setStageNumber(this.getStageNumber());
					b.setName(this.getName());
					b.setSymetryCode(this.symetryCode);
					b.setSignalInputUnit(this.getSignalInputUnit());
					b.setSignalOutputUnit(this.getSignalOutputUnit());
					list.add(b);
				}
				b.getCoefficients().add(this.coefficients.get(i));
			}
		} else {
			list.add(this);
		}
		return list;
	}

	@Override
	public String toSeedString() {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");

		builder.append(this.getStageNumber(), 2);
		builder.append(this.name).append("~");
		builder.append(this.symetryCode);
		builder.append(this.getSignalInputUnit(), 3);
		builder.append(this.getSignalOutputUnit(), 3);

		int size = 0;
		if (this.coefficients != null) {
			size = this.coefficients.size();
		}
		builder.append(size, 4);

		if (this.coefficients != null) {
			for (Double coefficient : this.coefficients) {
				builder.append(coefficient, "-0.0000000E-00", 14);
			}
		}
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public BlocketteBuilder<B061> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B061> {

		public Builder() {
			super(61);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B061 build() throws SeedException { 
			int offset = 7;
			B061 b = new B061();

			b.setStageNumber(SeedStrings.parseInt(bytes, offset, 2));
			offset = offset + 2;

			int i = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == (byte) '~') {
					break;
				}
			}
			b.setName(new String(bytes, i, offset - i));
			offset++;

			b.setSymetryCode((char) bytes[offset]);
			offset++;
			b.setSignalInputUnit(SeedStrings.parseInt(bytes, offset, 3));
			offset = offset + 3;
			b.setSignalOutputUnit(SeedStrings.parseInt(bytes, offset, 3));
			offset = offset + 3;

			int numberOfCoefficients = SeedStrings.parseInt(bytes, offset, 4);
			offset = offset + 4;

			for (i = 0; i < numberOfCoefficients; i++) {
				b.addCoefficient(SeedStrings.parseDouble(bytes, offset, 14));
				offset = offset + 14;
			}
			return b;
		}
	}
}
