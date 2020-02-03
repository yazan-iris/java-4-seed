package edu.iris.seed.station;

import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class B054 extends AbstractResponseBlockette<B054> implements Splittable, Appendable<B054> {

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

	private boolean shouldSplit(B054 blockette) {
		if (blockette.getNumerators().size() > 9999 || blockette.getDenominators().size() > 9999
				|| (24 + (24 * (blockette.getNumerators().size() + blockette.getDenominators().size())) > 9999)) {
			return true;
		}
		return false;
	}

	@Override
	public B054 append(B054 b054) {
		if (log.isDebugEnabled()) {
			log.debug("Appending {}", b054.getType());
		}
		this.denominators.addAll(b054.getDenominators());
		this.numerators.addAll(b054.getNumerators());
		return this;
	}

	@Override
	public List<B054> split() {
		List<B054> list = new ArrayList<>();
		if (shouldSplit(this)) {
			B054 b = null;
			int i = 0;
			for (; i < this.numerators.size(); i++) {
				if (i % 415 == 0) {
					b = new B054();
					b.setStageNumber(this.getStageNumber());
					b.setResponseType(this.responseType);
					b.setSignalInputUnit(this.getSignalInputUnit());
					b.setSignalOutputUnit(this.getSignalOutputUnit());
					list.add(b);
				}
				b.getNumerators().add(this.numerators.get(i));
			}
			for (int x = 0; x < this.denominators.size(); x++, i++) {
				if (i % 415 == 0) {
					b = new B054();
					b.setStageNumber(this.getStageNumber());
					b.setResponseType(this.responseType);
					b.setSignalInputUnit(this.getSignalInputUnit());
					b.setSignalOutputUnit(this.getSignalOutputUnit());
					list.add(b);
				}
				b.getDenominators().add(this.denominators.get(x));
			}
		} else {
			list.add(this);
		}
		return list;
	}

	@Override
	public String toSeedString() {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.responseType);
		builder.append(this.getStageNumber(), 2);
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

	public BlocketteBuilder<B054> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B054> {

		public Builder() {
			super(54);
			// TODO Auto-generated constructor stub
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B054 build() throws SeedException {
			int offset = 7;
			B054 b = new B054();

			b.setResponseType((char) bytes[offset]);
			offset++;
			b.setStageNumber(SeedStrings.parseInt(bytes, offset, 2));
			offset = offset + 2;
			b.setSignalInputUnit(SeedStrings.parseInt(bytes, offset, 3));
			offset = offset + 3;
			b.setSignalOutputUnit(SeedStrings.parseInt(bytes, offset, 3));
			offset = offset + 3;

			int numberOfNumerators = SeedStrings.parseInt(bytes, offset, 4);
			offset = offset + 4;

			for (int i = 0; i < numberOfNumerators; i++) {
				Double numerator = (double) SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				Double error = (double) SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				b.addNumerator(new Number(numerator, error));
			}

			int numberOfDenominators = SeedStrings.parseInt(bytes, offset, 4);
			offset = offset + 4;
			for (int i = 0; i < numberOfDenominators; i++) {
				Double denominator = (double) SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				Double error = (double) SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				b.addDenominator(new Number(denominator, error));
			}

			return b;
		}
	}
}
