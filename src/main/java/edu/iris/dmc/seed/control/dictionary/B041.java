package edu.iris.dmc.seed.control.dictionary;

import java.util.ArrayList;
import java.util.List;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.control.station.ResponseBlockette;

public class B041 extends AbstractDictionaryBlockette implements ResponseBlockette {

	private String name;
	private char symetryCode;
	private int signalInputUnit;
	private int signalOutputUnit;
	// @Size(min = 0, max = 9999)
	private List<Double> coefficients = new ArrayList<Double>();

	public B041(String text) {
		super(41, "FIR Dictionary Blockette");
		this.originalText=text;
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
	public String toSeedString() {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");

		builder.append(this.getLookupKey(), 4);
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
				builder.append(coefficient, "#0.0000000E00", 14);
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
