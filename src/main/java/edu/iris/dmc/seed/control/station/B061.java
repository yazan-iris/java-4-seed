package edu.iris.dmc.seed.control.station;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.Blockette;

public class B061 extends AbstractResponseBlockette {

	private String name;
	private char symetryCode;
	// @Size(min = 0, max = 9999)
	private List<Double> coefficients = new ArrayList<Double>();

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

	@Override
	public String toSeedString() {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");

		builder.append(this.getStageSequence(), 2);
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
}
