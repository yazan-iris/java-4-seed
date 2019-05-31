package edu.iris.dmc.seed.control.station;

import java.util.ArrayList;
import java.util.List;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.SeedException;

public class B054 extends AbstractResponseBlockette implements OverFlowBlockette {

	private char responseType;
	private List<Number> numerators = new ArrayList<Number>();
	private List<Number> denominators = new ArrayList<Number>();

	public B054() {
		super(54, "Response (Coefficients) Blockette");

	}

	public char getResponseType() {
		return responseType;
	}

	public void setResponseType(char responseType) {
		this.responseType = responseType;
	}

	public List<Number> getNumerators() {
		return numerators;
	}

	public void setNumerators(List<Number> numerators) {
		this.numerators = numerators;
	}

	public List<Number> getDenominators() {
		return denominators;
	}

	public void setDenominators(List<Number> denominators) {
		this.denominators = denominators;
	}

	public void addNumerator(Number coefficient) {
		this.numerators.add(coefficient);
	}

	public void addDenominator(Number coefficient) {
		this.denominators.add(coefficient);
	}

	@Override
	public boolean isOverFlown() throws SeedException {
		if (super.isOverFlown()) {
			return true;
		}

		if ((this.numerators.size() + this.denominators.size()) > 415) {
			return true;
		}
		return false;
	}

	@Override
	public List<Blockette> split() {
		List<Blockette> list = new ArrayList<>();
		int cnt = 0;
		B054 b054 = null;

		for (edu.iris.dmc.seed.control.station.Number n : this.numerators) {
			if (cnt % 415 == 0) {
				b054 = new B054();
				b054.setId(this.id);
				b054.setStageSequence(this.getStageSequence());
				b054.setResponseType(this.responseType);
				b054.setSignalInputUnit(this.getSignalInputUnit());
				b054.setSignalOutputUnit(this.getSignalOutputUnit());
				b054.setStageSequence(this.getStageSequence());
				list.add(b054);
			}
			b054.addNumerator(n);
			cnt++;
		}

		cnt = 0;
		for (edu.iris.dmc.seed.control.station.Number n : this.denominators) {
			int mod = cnt % 415;
			if (mod == 0) {
				if (mod < list.size()&&(415 - b054.getNumerators().size()>0)) {
					b054 = (B054) list.get(mod);
				} else {
					b054 = new B054();
					b054.setId(this.id);
					b054.setStageSequence(this.getStageSequence());
					b054.setResponseType(this.responseType);
					b054.setSignalInputUnit(this.getSignalInputUnit());
					b054.setSignalOutputUnit(this.getSignalOutputUnit());
					b054.setStageSequence(this.getStageSequence());
					list.add(b054);
				}
			}
			b054.addDenominator(n);
			cnt++;
		}
		return list;
	}

	@Override
	public String toSeedString() {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.responseType);
		builder.append(this.getStageSequence(), 2);
		builder.append(this.getSignalInputUnit(), 3);
		builder.append(this.getSignalOutputUnit(), 3);

		builder.append(this.numerators.size(), 4);

		for (Number coefficient : this.numerators) {
			builder.append(coefficient.getValue(), "-0.00000E-00", 12);
			builder.append(coefficient.getError(), "-0.00000E-00", 12);
			builder.length();
		}

		builder.append(this.denominators.size(), 4);

		for (Number coefficient : this.denominators) {
			builder.append(coefficient.getValue(), "-0.00000E-00", 12);
			builder.append(coefficient.getError(), "-0.00000E-00", 12);
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

}
