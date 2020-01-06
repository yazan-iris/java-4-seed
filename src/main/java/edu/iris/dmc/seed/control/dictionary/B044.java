package edu.iris.dmc.seed.control.dictionary;

import java.util.ArrayList;
import java.util.List;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.control.station.Number;
import edu.iris.dmc.seed.control.station.ResponseBlockette;

public class B044 extends AbstractDictionaryBlockette implements ResponseBlockette {

	private String responseName;
	private int signalInputUnit;
	private int signalOutputUnit;
	private char responseType;
	private List<edu.iris.dmc.seed.control.station.Number> numerators = new ArrayList<edu.iris.dmc.seed.control.station.Number>();
	private List<edu.iris.dmc.seed.control.station.Number> denominators = new ArrayList<edu.iris.dmc.seed.control.station.Number>();

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

	public List<edu.iris.dmc.seed.control.station.Number> getNumerators() {
		return numerators;
	}

	public void setNumerators(List<edu.iris.dmc.seed.control.station.Number> numerators) {
		this.numerators = numerators;
	}

	public List<edu.iris.dmc.seed.control.station.Number> getDenominators() {
		return denominators;
	}

	public void setDenominators(List<edu.iris.dmc.seed.control.station.Number> denominators) {
		this.denominators = denominators;
	}

	public void addNumerator(edu.iris.dmc.seed.control.station.Number coefficient) {
		this.numerators.add(coefficient);
	}

	public void addDenominator(edu.iris.dmc.seed.control.station.Number coefficient) {
		this.denominators.add(coefficient);
	}

	@Override
	public String toSeedString() {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.getLookupKey(), 4);
		builder.append(this.responseName);
		builder.append(this.responseType);
		builder.append(this.getSignalInputUnit(), 3);
		builder.append(this.getSignalOutputUnit(), 3);

		builder.append(this.numerators.size(), 3);

		for (Number coefficient : this.numerators) {
			builder.append(coefficient.getValue(), "-0.00000E-00", 12);
			builder.append(coefficient.getError(), "-0.00000E-00", 12);
		}

		builder.append(this.denominators.size(), 3);

		for (Number coefficient : this.denominators) {
			builder.append(coefficient.getValue(), "-0.00000E-00", 12);
			builder.append(coefficient.getError(), "-0.00000E-00", 12);
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
