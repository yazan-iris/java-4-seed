package edu.iris.dmc.seed.control.dictionary;

import java.util.ArrayList;
import java.util.List;

import edu.iris.dmc.io.SeedStringBuilder;
import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.control.station.Pole;
import edu.iris.dmc.seed.control.station.ResponseBlockette;
import edu.iris.dmc.seed.control.station.Zero;

public class B043 extends AbstractDictionaryBlockette implements ResponseBlockette {

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
		builder.append(this.responseName);
		builder.append(this.transferFunctionType);
		builder.append(this.getLookupKey(), 4);
		builder.append(this.getSignalInputUnit(), 3);
		builder.append(this.getSignalOutputUnit(), 3);

		builder.append(this.normalizationFactor, "#0.00000E00", 12);
		builder.append(this.normalizationFrequency, "#0.00000E00", 12);

		builder.append(this.zeros.size(), 3);

		for (Zero zero : this.zeros) {
			builder.append(zero.getReal().getValue(), "#0.00000E00", 12);
			builder.append(zero.getImaginary().getValue(), "#0.00000E00", 12);
			builder.append(zero.getReal().getError(), "#0.00000E00", 12);
			builder.append(zero.getImaginary().getError(), "#0.00000E00", 12);
		}

		builder.append(this.poles.size(), 3);

		for (Pole pole : this.poles) {
			builder.append(pole.getReal().getValue(), "#0.00000E00", 12);
			builder.append(pole.getImaginary().getValue(), "#0.00000E00", 12);
			builder.append(pole.getReal().getError(), "#0.00000E00", 12);
			builder.append(pole.getImaginary().getError(), "#0.00000E00", 12);
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
