package edu.iris.seed.station;

import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;

public class B053 extends AbstractResponseBlockette implements Splittable {

	private char transferFunctionType;
	private double normalizationFactor = 1;
	private double normalizationFrequency;// Hz
	private List<Zero> zeros = new ArrayList<>();
	private List<Pole> poles = new ArrayList<>();

	public B053() {
		super(53, "Response (Poles & Zeros) Blockette");

	}

	public char getTransferFunctionType() {
		return transferFunctionType;
	}

	public void setTransferFunctionType(char transferFunctionType) {
		this.transferFunctionType = transferFunctionType;
	}

	public double getNormalizationFactor() {
		return normalizationFactor;
	}

	public void setNormalizationFactor(double normalizationFactor) {
		this.normalizationFactor = normalizationFactor;
	}

	public double getNormalizationFrequency() {
		return normalizationFrequency;
	}

	public void setNormalizationFrequency(double normalizationFrequency) {
		this.normalizationFrequency = normalizationFrequency;
	}

	public List<Zero> getZeros() {
		return zeros;
	}

	public void setZeros(List<Zero> zeros) {
		this.zeros = zeros;
	}

	public void add(Zero zero) {
		this.zeros.add(zero);
	}

	public List<Pole> getPoles() {
		return poles;
	}

	public void setPoles(List<Pole> poles) {
		this.poles = poles;
	}

	public void add(Pole pole) {
		this.poles.add(pole);
	}

	private boolean shouldSplit(B053 blockette) {
		if (blockette.getZeros().size() > 999 || blockette.getPoles().size() > 999
				|| (46 + (48 * (blockette.getZeros().size() + blockette.poles.size())) > 9999)) {
			return true;
		}
		return false;
	}

	@Override
	public List<B053> split() throws SeedException {
		List<B053> list = new ArrayList<>();
		if (shouldSplit(this)) {

			B053 b053 = null;
			int i = 0;
			for (; i < this.zeros.size(); i++) {
				if (i % 207 == 0) {
					b053 = new B053();
					b053.setStageNumber(this.getStageNumber());
					b053.setNormalizationFactor(this.normalizationFactor);
					b053.setNormalizationFrequency(this.normalizationFrequency);
					b053.setTransferFunctionType(transferFunctionType);
					b053.setSignalInputUnit(this.getSignalInputUnit());
					b053.setSignalOutputUnit(this.getSignalOutputUnit());
					list.add(b053);
				}
				b053.add(this.zeros.get(i));
			}
			for (int x = 0; x < this.poles.size(); x++, i++) {
				if (i % 207 == 0) {
					b053 = new B053();
					b053.setStageNumber(this.getStageNumber());
					b053.setNormalizationFactor(this.normalizationFactor);
					b053.setNormalizationFrequency(this.normalizationFrequency);
					b053.setTransferFunctionType(transferFunctionType);
					b053.setSignalInputUnit(this.getSignalInputUnit());
					b053.setSignalOutputUnit(this.getSignalOutputUnit());
					list.add(b053);
				}
				b053.add(this.poles.get(x));
			}
		} else {
			list.add(this);
		}
		return list;
	}

	@Override
	public String toSeedString() {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.transferFunctionType);
		builder.append(this.getStageNumber(), 2);
		builder.append(this.getSignalInputUnit(), 3);
		builder.append(this.getSignalOutputUnit(), 3);

		builder.append(this.normalizationFactor, "-0.00000E-00", 12);
		builder.append(this.normalizationFrequency, "-0.00000E-00", 12);

		builder.append(this.zeros.size(), 3);

		for (Zero zero : this.zeros) {
			builder.append(zero.getReal().getValue(), "-0.00000E-00", 12);
			builder.append(zero.getImaginary().getValue(), "-0.00000E-00", 12);
			builder.append(zero.getReal().getError(), "-0.00000E-00", 12);
			builder.append(zero.getImaginary().getError(), "-0.00000E-00", 12);
		}

		builder.append(this.poles.size(), 3);

		for (Pole pole : this.poles) {
			double realValue = 0;
			double realError = 0;
			double imaginaryValue = 0;
			double imaginaryError = 0;
			if (pole.getReal() != null) {
				realValue = pole.getReal().getValue();
				realError = pole.getReal().getError();
			}
			if (pole.getImaginary() != null) {
				imaginaryValue = pole.getImaginary().getValue();
				imaginaryError = pole.getImaginary().getError();
			}
			builder.append(realValue, "-0.00000E-00", 12);
			builder.append(imaginaryValue, "-0.00000E-00", 12);
			builder.append(realError, "-0.00000E-00", 12);
			builder.append(imaginaryError, "-0.00000E-00", 12);
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public BlocketteBuilder<B053> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B053> {

		public Builder() {
			super(53);
			// TODO Auto-generated constructor stub
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B053 build() throws SeedException { 
			int offset = 7;
			B053 b = new B053();

			b.setTransferFunctionType((char) bytes[offset]);
			offset++;

			b.setStageNumber(SeedStrings.parseInt(bytes, offset, 2));
			offset = offset + 2;
			b.setSignalInputUnit(SeedStrings.parseInt(bytes, offset, 3));
			offset = offset + 3;
			b.setSignalOutputUnit(SeedStrings.parseInt(bytes, offset, 3));
			offset = offset + 3;

			b.setNormalizationFactor(SeedStrings.parseFloat(bytes, offset, 12));
			offset = offset + 12;

			b.setNormalizationFrequency(SeedStrings.parseFloat(bytes, offset, 12));
			offset = offset + 12;

			int numberOfZeros = SeedStrings.parseInt(bytes, offset, 3);
			offset = offset + 3;

			for (int i = 0; i < numberOfZeros; i++) {
				float real = SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				float imaginary = SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				float realError = SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				float imaginaryError = SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				b.add(new Zero(real, realError, imaginary, imaginaryError));
			}

			int numberOfPoles = SeedStrings.parseInt(bytes, offset, 3);
			offset = offset + 3;
			for (int i = 0; i < numberOfPoles; i++) {
				float real = SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				float imaginary = SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				float realError = SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				float imaginaryError = SeedStrings.parseFloat(bytes, offset, 12);
				offset = offset + 12;
				b.add(new Pole(real, realError, imaginary, imaginaryError));
			}

			return b;
		}
	}
}
