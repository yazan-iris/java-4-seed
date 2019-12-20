package edu.iris.dmc.seed.control.station;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.SeedException;
import edu.iris.dmc.seed.SeedStringBuilder;

public class B061 extends AbstractResponseBlockette implements OverFlowBlockette {

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

	@Override
	public OverFlowBlockette merge(OverFlowBlockette b) throws SeedException {
		Objects.requireNonNull(b, "Blockette cannot be null");
		if (!(b instanceof B061)) {
			throw new SeedException("Cannot merge with " + this.type + ".");
		}
		B061 m = (B061) b;
		this.getCoefficients().addAll(m.getCoefficients());
		return this;
	}

	@Override
	public List<Blockette> split() throws SeedException {
		List<Blockette> list = new ArrayList<>();
		B061 b061 = null;
		if (this.getSize() < this.getLength()) {
			list.add(this);
			return list;
		}

		for (Double n : this.coefficients) {
			if (b061 == null || b061.getSize() + 14 > this.getLength()) {
				b061 = new B061();
				b061.setId(this.id);
				b061.setName(this.getName());
				b061.setSymetryCode(this.symetryCode);
				b061.setStageSequence(this.getStageSequence());
				b061.setSignalInputUnit(this.getSignalInputUnit());
				b061.setSignalOutputUnit(this.getSignalOutputUnit());
				b061.setStageSequence(this.getStageSequence());
				list.add(b061);
			}
			b061.addCoefficient(n);
		}
		return list;
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
				builder.append(coefficient, "-0.0000000E-00", 14);
			}
		}
		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

}
