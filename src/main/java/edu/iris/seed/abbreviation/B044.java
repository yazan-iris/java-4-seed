package edu.iris.seed.abbreviation;

import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;
import edu.iris.seed.station.Number;
import edu.iris.seed.station.ResponseDictionaryBlockette;

public class B044 extends AbstractAbbreviationBlockette<B044>
		implements AbbreviationBlockette, ResponseDictionaryBlockette {

	private String responseName;
	private int signalInputUnit;
	private int signalOutputUnit;
	private char responseType;
	private List<edu.iris.seed.station.Number> numerators = new ArrayList<edu.iris.seed.station.Number>();
	private List<edu.iris.seed.station.Number> denominators = new ArrayList<edu.iris.seed.station.Number>();

	public B044() {
		super(44, "Response (Coefficients) Dictionary Blockette");

	}

	public String getResponseName() {
		return responseName;
	}

	public void setResponseName(String responseName) {
		this.responseName = responseName;
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

	public char getResponseType() {
		return responseType;
	}

	public void setResponseType(char responseType) {
		this.responseType = responseType;
	}

	public List<edu.iris.seed.station.Number> getNumerators() {
		return numerators;
	}

	public void setNumerators(List<edu.iris.seed.station.Number> numerators) {
		this.numerators = numerators;
	}

	public List<edu.iris.seed.station.Number> getDenominators() {
		return denominators;
	}

	public void setDenominators(List<edu.iris.seed.station.Number> denominators) {
		this.denominators = denominators;
	}

	public void addNumerator(edu.iris.seed.station.Number coefficient) {
		this.numerators.add(coefficient);
	}

	public void addDenominator(edu.iris.seed.station.Number coefficient) {
		this.denominators.add(coefficient);
	}

	@Override
	public String toSeedString() {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.getLookupKey(), 4);
		builder.append(this.responseName).append("~");
		builder.append(this.responseType);
		builder.append(this.getSignalInputUnit(), 3);
		builder.append(this.getSignalOutputUnit(), 3);

		builder.append(this.numerators.size(), 4);

		for (Number coefficient : this.numerators) {
			builder.append(coefficient.getValue(), "-0.00000E-00", 12);
			builder.append(coefficient.getError(), "-0.00000E-00", 12);
		}

		builder.append(this.denominators.size(), 4);

		for (Number coefficient : this.denominators) {
			builder.append(coefficient.getValue(), "-0.00000E-00", 12);
			builder.append(coefficient.getError(), "-0.00000E-00", 12);
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	@Override
	public int getStageNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	public BlocketteBuilder<B044> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B044> {

		public Builder() {
			super(44);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B044 build(boolean relax) throws SeedException {
			validate(44, 27, bytes);
			int offset = 7;
			B044 b = new B044();

			b.setLookupKey(SeedStrings.parseInt(bytes, offset, 4));
			offset = offset + 4;

			int i = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == (byte) '~') {
					break;
				}
			}
			String responseName = new String(bytes, i, offset - i);
			b.setResponseName(responseName);
			// skip ~
			offset++;

			b.setResponseType((char) bytes[offset]);
			offset++;

			b.setSignalInputUnit(SeedStrings.parseInt(bytes, offset, 3));
			offset = offset + 3;
			b.setSignalOutputUnit(SeedStrings.parseInt(bytes, offset, 3));
			offset = offset + 3;

			int numberOfNumerators = SeedStrings.parseInt(bytes, offset, 4);
			offset = offset + 4;

			for (i = 0; i < numberOfNumerators; i++) {
				float numerator = SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				float error = SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				b.addNumerator(new Number(numerator, error));
			}

			int numberOfDenominators = SeedStrings.parseInt(bytes, offset, 4);
			offset = offset + 4;
			for (i = 0; i < numberOfDenominators; i++) {
				float denominator = SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				float error = SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				b.addDenominator(new Number(denominator, error));
			}

			return b;
		}
	}
}
