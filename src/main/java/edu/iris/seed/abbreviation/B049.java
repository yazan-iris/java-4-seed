package edu.iris.seed.abbreviation;

import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;
import edu.iris.seed.station.Number;
import edu.iris.seed.station.ResponseDictionaryBlockette;

public class B049 extends AbstractAbbreviationBlockette<B049> implements AbbreviationBlockette, ResponseDictionaryBlockette {

	private String responseName;
	private char transferFunctionType;
	private char approximationType;
	private char frequencyUnit;

	private int signalInputUnit;
	private int signalOutputUnit;

	private Double lowerValidFrequencyBound;// F 12 �-#.#####E-##�
	private Double upperValidFrequencyBound;// F 12 �-#.#####E-##�
	private Double lowerBoundOfApproximation;// F 12 �-#.#####E-##�
	private Double upperBoundOfApproximation;// F 12 �-#.#####E-##�
	private Double maximumAbsoluteError;

	private List<edu.iris.seed.station.Number> coefficients = new ArrayList<edu.iris.seed.station.Number>();

	public B049() {
		super(49, "Response [Polynomial] Blockette");

	}

	public String getResponseName() {
		return responseName;
	}

	public void setResponseName(String responseName) {
		this.responseName = responseName;
	}

	public char getTransferFunctionType() {
		return transferFunctionType;
	}

	public void setTransferFunctionType(char transferFunctionType) {
		this.transferFunctionType = transferFunctionType;
	}

	public char getApproximationType() {
		return approximationType;
	}

	public void setApproximationType(char approximationType) {
		this.approximationType = approximationType;
	}

	public char getFrequencyUnit() {
		return frequencyUnit;
	}

	public void setFrequencyUnit(char frequencyUnit) {
		this.frequencyUnit = frequencyUnit;
	}

	public Double getLowerValidFrequencyBound() {
		return lowerValidFrequencyBound;
	}

	public void setLowerValidFrequencyBound(Double lowerValidFrequencyBound) {
		this.lowerValidFrequencyBound = lowerValidFrequencyBound;
	}

	public Double getUpperValidFrequencyBound() {
		return upperValidFrequencyBound;
	}

	public void setUpperValidFrequencyBound(Double upperValidFrequencyBound) {
		this.upperValidFrequencyBound = upperValidFrequencyBound;
	}

	public Double getLowerBoundOfApproximation() {
		return lowerBoundOfApproximation;
	}

	public void setLowerBoundOfApproximation(Double lowerBoundOfApproximation) {
		this.lowerBoundOfApproximation = lowerBoundOfApproximation;
	}

	public Double getUpperBoundOfApproximation() {
		return upperBoundOfApproximation;
	}

	public void setUpperBoundOfApproximation(Double upperBoundOfApproximation) {
		this.upperBoundOfApproximation = upperBoundOfApproximation;
	}

	public Double getMaximumAbsoluteError() {
		return maximumAbsoluteError;
	}

	public void setMaximumAbsoluteError(Double maximumAbsoluteError) {
		this.maximumAbsoluteError = maximumAbsoluteError;
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

	public List<edu.iris.seed.station.Number> getCoefficients() {
		return coefficients;
	}

	public void setCoefficients(List<edu.iris.seed.station.Number> coefficients) {
		this.coefficients = coefficients;
	}

	public void add(edu.iris.seed.station.Number coefficient) {
		this.coefficients.add(coefficient);
	}

	public void addAll(List<edu.iris.seed.station.Number> coefficients) {
		this.coefficients.addAll(coefficients);
	}

	@Override
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.getLookupKey(), 4);
		builder.append(this.responseName).append("~");
		builder.append(this.transferFunctionType);

		builder.append(this.getSignalInputUnit(), 3);
		builder.append(this.getSignalOutputUnit(), 3);
		builder.append(this.approximationType);
		builder.append(this.frequencyUnit);

		builder.append(this.lowerValidFrequencyBound, "-0.00000E-00", 12);
		builder.append(this.upperValidFrequencyBound, "-0.00000E-00", 12);
		builder.append(this.lowerBoundOfApproximation, "-0.00000E-00", 12);
		builder.append(this.upperBoundOfApproximation, "-0.00000E-00", 12);
		builder.append(this.maximumAbsoluteError, "-0.00000E-00", 12);

		int size = 0;
		if (this.coefficients != null) {
			size = this.coefficients.size();
		}
		builder.append(size, 3);

		if (this.coefficients != null) {
			for (Number coefficient : this.coefficients) {
				builder.append(coefficient.getValue(), "-0.00000E-00", 12);
				builder.append(coefficient.getError(), "-0.00000E-00", 12);
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

	public BlocketteBuilder<B049> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B049> {

		public Builder() {
			super(49);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B049 build() throws SeedException {
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int offset = 7;
			B049 b = new B049();

			b.setTransferFunctionType((char) bytes[offset]);
			offset++;

			b.setLookupKey(SeedStrings.parseInt(bytes, offset, 4));
			offset = offset + 4;

			b.setSignalInputUnit(SeedStrings.parseInt(bytes, offset, 3));
			offset = offset + 3;

			b.setSignalOutputUnit(SeedStrings.parseInt(bytes, offset, 3));
			offset = offset + 3;

			b.setApproximationType((char) bytes[offset]);
			offset++;

			b.setFrequencyUnit((char) bytes[offset]);
			offset++;

			b.setLowerValidFrequencyBound(SeedStrings.parseDouble(bytes, offset, 12));
			offset = offset + 12;
			b.setUpperValidFrequencyBound(SeedStrings.parseDouble(bytes, offset, 12));
			offset = offset + 12;

			b.setLowerBoundOfApproximation(SeedStrings.parseDouble(bytes, offset, 12));
			offset = offset + 12;
			b.setUpperBoundOfApproximation(SeedStrings.parseDouble(bytes, offset, 12));
			offset = offset + 12;
			b.setMaximumAbsoluteError(SeedStrings.parseDouble(bytes, offset, 12));
			offset = offset + 12;

			int numberOfCoefficients = SeedStrings.parseInt(bytes, offset, 3);
			offset = offset + 3;

			for (int i = 0; i < numberOfCoefficients; i++) {
				Float value = SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				Float error = SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				b.add(new Number(value, error));
			}
			return b;
		}
	}
}
