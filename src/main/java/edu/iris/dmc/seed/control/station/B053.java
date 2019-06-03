package edu.iris.dmc.seed.control.station;

import java.util.ArrayList;
import java.util.List;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.SeedException;

public class B053 extends AbstractResponseBlockette implements OverFlowBlockette {

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

	@Override
	public List<Blockette> split() throws SeedException {
		List<Blockette> list = new ArrayList<>();
		B053 b053 = null;
		if (this.getSize() < this.getLength()) {
			list.add(this);
			return list;
		}

		for (Zero z : this.zeros) {
			if (b053 == null || b053.getSize()+48 > this.getLength()) {
				b053 = new B053();
				b053.setId(this.id);
				b053.setStageSequence(this.getStageSequence());
				b053.setNormalizationFactor(this.normalizationFactor);
				b053.setNormalizationFrequency(this.normalizationFrequency);
				b053.setTransferFunctionType(transferFunctionType);
				b053.setStageSequence(this.getStageSequence());
				b053.setSignalInputUnit(this.getSignalInputUnit());
				b053.setSignalOutputUnit(this.getSignalOutputUnit());
				b053.setStageSequence(this.getStageSequence());
				list.add(b053);
			}
			b053.add(z);
		}

		for (Pole p : this.poles) {
			if (b053 == null || b053.getSize()+48 > this.getLength()) {
				b053 = new B053();
				b053.setId(this.id);
				b053.setStageSequence(this.getStageSequence());
				b053.setNormalizationFactor(this.normalizationFactor);
				b053.setNormalizationFrequency(this.normalizationFrequency);
				b053.setTransferFunctionType(transferFunctionType);
				b053.setStageSequence(this.getStageSequence());
				b053.setSignalInputUnit(this.getSignalInputUnit());
				b053.setSignalOutputUnit(this.getSignalOutputUnit());
				b053.setStageSequence(this.getStageSequence());
				list.add(b053);
			}
			b053.add(p);
		}
		return list;
	}

	@Override
	public String toSeedString() {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.transferFunctionType);
		builder.append(this.getStageSequence(), 2);
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
}
