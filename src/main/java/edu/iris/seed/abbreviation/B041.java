package edu.iris.seed.abbreviation;

import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;
import edu.iris.seed.station.ResponseDictionaryBlockette;

public class B041 extends AbstractAbbreviationBlockette<B041>
		implements AbbreviationBlockette, ResponseDictionaryBlockette {

	private String name;
	private char symetryCode;
	private int signalInputUnit;
	private int signalOutputUnit;
	// @Size(min = 0, max = 9999)

	private List<Double> coefficients = new ArrayList<Double>();

	public B041() {
		super(41, "FIR Dictionary Blockette");
	}

	public String getResponseName() {
		return name;
	}

	public void setResponseName(String name) {
		this.name = name;
	}

	public char getSymetryCode() {
		return symetryCode;
	}

	public void setSymetryCode(char symetryCode) {
		this.symetryCode = symetryCode;
	}

	public int getSignalInputUnit() {
		return signalInputUnit;
	}

	public void setSignalInputUnit(int signalInputUnit) {
		this.signalInputUnit = signalInputUnit;
	}

	public int getSignalOutputUnit() {
		return signalOutputUnit;
	}

	public void setSignalOutputUnit(int signalOutputUnit) {
		this.signalOutputUnit = signalOutputUnit;
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

	@Override
	public String toSeedString() throws SeedException {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");

		builder.append(this.getLookupKey(), 4);
		builder.append(this.name.substring(0, Math.min(this.name.length(), 25))).append("~");

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

	@Override
	public int getStageNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	public BlocketteBuilder<B041> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B041> {

		public Builder() {
			super(41);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		private B041 buildFromValues() throws SeedException {
			B041 b = new B041();
			return b;
		}

		private B041 buildFromBytes() throws SeedException {
			int offset = 7;
			B041 b = new B041();

			b.setLookupKey(SeedStrings.parseInt(bytes, offset, 4));
			offset = offset + 4;

			int i = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == (byte) '~') {
					break;
				}
			}
			b.setResponseName(new String(bytes, i, offset - i));
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

		public B041 build() throws SeedException {
			if (bytes != null) {
				return buildFromBytes();
			} else {
				return buildFromValues();
			}
		}
	}
}
