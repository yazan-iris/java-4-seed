package edu.iris.dmc.seed.control.dictionary;

import edu.iris.dmc.seed.SeedStringBuilder;
import edu.iris.dmc.seed.control.station.ResponseBlockette;

public class B047 extends AbstractDictionaryBlockette implements ResponseBlockette {

	private String responseName;
	private Double sampleRate;
	private Integer decimationFactor;
	private Integer decimationOffset;
	private Double estimatedDelay;
	private Double correction;

	public B047() {
		super(47, " Decimation Blockette");

	}

	public String getResponseName() {
		return responseName;
	}

	public void setResponseName(String responseName) {
		this.responseName = responseName;
	}

	public Double getSampleRate() {
		return sampleRate;
	}

	public void setSampleRate(Double sampleRate) {
		this.sampleRate = sampleRate;
	}

	public Integer getDecimationFactor() {
		return decimationFactor;
	}

	public void setDecimationFactor(Integer decimationFactor) {
		this.decimationFactor = decimationFactor;
	}

	public Integer getDecimationOffset() {
		return decimationOffset;
	}

	public void setDecimationOffset(Integer decimationOffset) {
		this.decimationOffset = decimationOffset;
	}

	public Double getEstimatedDelay() {
		return estimatedDelay;
	}

	public void setEstimatedDelay(Double estimatedDelay) {
		this.estimatedDelay = estimatedDelay;
	}

	public Double getCorrection() {
		return correction;
	}

	public void setCorrection(Double correction) {
		this.correction = correction;
	}
	@Override
	public String toSeedString() {

		SeedStringBuilder builder = new SeedStringBuilder("0" + this.getType() + "####");
		builder.append(this.getLookupKey(), 4);
		builder.append(this.responseName);
		builder.append(this.getSampleRate(), "0.0000E-00", 10);
		builder.append(this.getDecimationFactor(), 5);
		builder.append(this.getDecimationOffset(), 5);

		builder.append(this.getEstimatedDelay(), "-0.0000E-00", 11);
		builder.append(this.getCorrection(), "-0.0000E-00", 11);

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	@Override
	public int getStageSequence() {
		// TODO Auto-generated method stub
		return 0;
	}
}
