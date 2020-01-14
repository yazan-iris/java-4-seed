package edu.iris.seed.station;

import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;

public class B057 extends AbstractResponseBlockette {

	private double sampleRate;
	private int decimationFactor;
	private int decimationOffset;
	private double estimatedDelay;
	private double correction;

	public B057() {
		super(57, " Decimation Blockette");

	}

	public double getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(double sampleRate) {
		this.sampleRate = sampleRate;
	}

	public int getDecimationFactor() {
		return decimationFactor;
	}

	public void setDecimationFactor(int decimationFactor) {
		this.decimationFactor = decimationFactor;
	}

	public int getDecimationOffset() {
		return decimationOffset;
	}

	public void setDecimationOffset(int decimationOffset) {
		this.decimationOffset = decimationOffset;
	}

	public double getEstimatedDelay() {
		return estimatedDelay;
	}

	public void setEstimatedDelay(double estimatedDelay) {
		this.estimatedDelay = estimatedDelay;
	}

	public double getCorrection() {
		return correction;
	}

	public void setCorrection(double correction) {
		this.correction = correction;
	}

	@Override
	public String toSeedString() {
		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.getStageNumber(), 2);
		builder.append(this.getSampleRate(), "0.0000E-00", 10);
		builder.append(this.getDecimationFactor(), 5);
		builder.append(this.getDecimationOffset(), 5);

		builder.append(this.getEstimatedDelay(), "-0.0000E-00", 11);
		builder.append(this.getCorrection(), "-0.0000E-00", 11);

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	public static B057 build(byte[] bytes) throws SeedException {
		int offset = 7;
		B057 b = new B057();
		b.setStageNumber(SeedStrings.parseInt(bytes, offset, 2));
		offset = offset + 2;

		double sampleRate = SeedStrings.parseDouble(bytes, offset, 10);
		b.setSampleRate(sampleRate);
		offset = offset + 10;
		b.setDecimationFactor(SeedStrings.parseInt(bytes, offset, 5));
		offset = offset + 5;
		b.setDecimationOffset(SeedStrings.parseInt(bytes, offset, 5));
		offset = offset + 5;

		b.setEstimatedDelay(SeedStrings.parseDouble(bytes, offset, 11));
		offset = offset + 11;
		b.setCorrection(SeedStrings.parseDouble(bytes, offset, 11));

		return b;
	}
}
