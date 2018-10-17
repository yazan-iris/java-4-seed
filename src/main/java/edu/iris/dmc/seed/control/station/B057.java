package edu.iris.dmc.seed.control.station;


import edu.iris.dmc.io.SeedStringBuilder;

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
		builder.append(this.getStageSequence(), 2);
		builder.append(this.getSampleRate(), "0.0000E-00", 10);
		builder.append(this.getDecimationFactor(), 5);
		builder.append(this.getDecimationOffset(), 5);

		builder.append(this.getEstimatedDelay(), "-0.0000E-00", 11);
		builder.append(this.getCorrection(), "-0.0000E-00", 11);

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}
}
