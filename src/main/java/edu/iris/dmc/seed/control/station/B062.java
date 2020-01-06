package edu.iris.dmc.seed.control.station;

import java.util.ArrayList;
import java.util.List;

import edu.iris.dmc.io.SeedStringBuilder;

public class B062 extends AbstractResponseBlockette {

	private char transferFunctionType;
	private char approximationType;
	private char frequencyUnit;

	private double lowerValidFrequencyBound;// F 12 �-#.#####E-##�
	private double upperValidFrequencyBound;// F 12 �-#.#####E-##�
	private double lowerBoundOfApproximation;// F 12 �-#.#####E-##�
	private double upperBoundOfApproximation;// F 12 �-#.#####E-##�
	private double maximumAbsoluteError;

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

	public double getLowerValidFrequencyBound() {
		return lowerValidFrequencyBound;
	}

	public void setLowerValidFrequencyBound(double lowerValidFrequencyBound) {
		this.lowerValidFrequencyBound = lowerValidFrequencyBound;
	}

	public double getUpperValidFrequencyBound() {
		return upperValidFrequencyBound;
	}

	public void setUpperValidFrequencyBound(double upperValidFrequencyBound) {
		this.upperValidFrequencyBound = upperValidFrequencyBound;
	}

	public double getLowerBoundOfApproximation() {
		return lowerBoundOfApproximation;
	}

	public void setLowerBoundOfApproximation(double lowerBoundOfApproximation) {
		this.lowerBoundOfApproximation = lowerBoundOfApproximation;
	}

	public double getUpperBoundOfApproximation() {
		return upperBoundOfApproximation;
	}

	public void setUpperBoundOfApproximation(double upperBoundOfApproximation) {
		this.upperBoundOfApproximation = upperBoundOfApproximation;
	}

	public double getMaximumAbsoluteError() {
		return maximumAbsoluteError;
	}

	public void setMaximumAbsoluteError(double maximumAbsoluteError) {
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
