package edu.iris.dmc.seed.control.station;

import java.util.ArrayList;
import java.util.List;

import edu.iris.dmc.io.SeedStringBuilder;

public class B062 extends AbstractResponseBlockette {

	private char transferFunctionType;
	private char approximationType;
	private char frequencyUnit;

	private Double lowerValidFrequencyBound;// F 12 �-#.#####E-##�
	private Double upperValidFrequencyBound;// F 12 �-#.#####E-##�
	private Double lowerBoundOfApproximation;// F 12 �-#.#####E-##�
	private Double upperBoundOfApproximation;// F 12 �-#.#####E-##�
	private Double maximumAbsoluteError;

	private List<Number> coefficients = new ArrayList<Number>();

	public B062() {
		super(62, "Response [Polynomial] Blockette");

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

	public List<Number> getCoefficients() {
		return coefficients;
	}

	public void setCoefficients(List<Number> coefficients) {
		this.coefficients = coefficients;
	}

	public void add(Number coefficient) {
		this.coefficients.add(coefficient);
	}

	public void addAll(List<Number> coefficients) {
		this.coefficients.addAll(coefficients);
	}

	@Override
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append("P");
		builder.append(this.getStageSequence(), 2);
		builder.append(this.getSignalInputUnit(), 3);
		builder.append(this.getSignalOutputUnit(), 3);
		builder.append(this.approximationType);
		builder.append(""+this.frequencyUnit);

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

}
