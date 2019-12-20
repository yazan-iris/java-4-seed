package edu.iris.dmc.seed.control.dictionary;

import java.util.ArrayList;
import java.util.List;

import edu.iris.dmc.seed.SeedException;
import edu.iris.dmc.seed.SeedStringBuilder;
import edu.iris.dmc.seed.control.station.Number;
import edu.iris.dmc.seed.control.station.ResponseBlockette;

public class B042 extends AbstractDictionaryBlockette implements ResponseBlockette {

	private String responseName;
	private char transferFunctionType;
	private char approximationType;
	private char frequencyUnit;

	private int signalInputUnit;
	private int signalOutputUnit;

	private double lowerValidFrequencyBound;// F 12 �-#.#####E-##�
	private double upperValidFrequencyBound;// F 12 �-#.#####E-##�
	private double lowerBoundOfApproximation;// F 12 �-#.#####E-##�
	private double upperBoundOfApproximation;// F 12 �-#.#####E-##�
	private double maximumAbsoluteError;

	private List<edu.iris.dmc.seed.control.station.Number> coefficients = new ArrayList<>();

	public B042() {
		super(42, "Response [Polynomial] Blockette");

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

	public List<edu.iris.dmc.seed.control.station.Number> getCoefficients() {
		return coefficients;
	}

	public void setCoefficients(List<edu.iris.dmc.seed.control.station.Number> coefficients) {
		this.coefficients = coefficients;
	}

	public void add(edu.iris.dmc.seed.control.station.Number coefficient) {
		this.coefficients.add(coefficient);
	}

	public void addAll(List<edu.iris.dmc.seed.control.station.Number> coefficients) {
		this.coefficients.addAll(coefficients);
	}

	@Override
	public String toSeedString() throws SeedException {

		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.getLookupKey(), 4);
		builder.append(this.responseName);
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
	public int getStageSequence() {
		// TODO Auto-generated method stub
		return 0;
	}
}
