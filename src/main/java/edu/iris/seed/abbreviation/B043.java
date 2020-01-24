package edu.iris.seed.abbreviation;

import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;
import edu.iris.seed.station.Pole;
import edu.iris.seed.station.ResponseDictionaryBlockette;
import edu.iris.seed.station.Zero;

public class B043 extends AbstractAbbreviationBlockette<B043> implements AbbreviationBlockette, ResponseDictionaryBlockette {

	private String responseName;
	private char transferFunctionType;
	private double normalizationFactor = 1;
	private double normalizationFrequency;// Hz

	private int signalInputUnit;
	private int signalOutputUnit;

	private List<Zero> zeros = new ArrayList<Zero>();
	private List<Pole> poles = new ArrayList<Pole>();

	public B043() {
		super(43, "Response (Poles & Zeros) Dictionary Blockette");

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

	@Override
	public String toSeedString() {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.getLookupKey(), 4);
		builder.append(this.responseName).append("~");
		builder.append(this.transferFunctionType);
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
			builder.append(pole.getReal().getValue(), "-0.00000E-00", 12);
			builder.append(pole.getImaginary().getValue(), "-0.00000E-00", 12);
			builder.append(pole.getReal().getError(), "-0.00000E-00", 12);
			builder.append(pole.getImaginary().getError(), "-0.00000E-00", 12);
		}

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	@Override
	public int getStageNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	public BlocketteBuilder<B043> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B043> {

		public Builder() {
			super(43);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B043 build(boolean relax) throws SeedException {
			int offset = 7;
			B043 b = new B043();

			b.setLookupKey(SeedStrings.parseInt(bytes, offset, 4));
			offset = offset + 4;

			int i = offset;
			for (; offset < bytes.length; offset++) {
				if (bytes[offset] == (byte) '~') {
					break;
				}
			}
			String responseName = new String(bytes, i, offset - i);
			b.setResponseName(responseName);
			// skip ~
			offset++;

			b.setTransferFunctionType((char) bytes[offset]);
			offset++;

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

			for (i = 0; i < numberOfZeros; i++) {
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
			for (i = 0; i < numberOfPoles; i++) {
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
