package edu.iris.seed.abbreviation;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;
import edu.iris.seed.lang.SeedStrings;
import edu.iris.seed.station.ResponseDictionaryBlockette;

public class B047 extends AbstractAbbreviationBlockette<B047> implements AbbreviationBlockette, ResponseDictionaryBlockette {

	private String responseName;
	private Double sampleRate;
	private Integer decimationFactor;
	private Integer decimationOffset;
	private Double estimatedDelay;
	private Double correction;

	public B047() {
		super(47, "Decimation Blockette");

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
		builder.append(this.responseName).append("~");
		builder.append(this.getSampleRate(), "0.0000E-00", 10);
		builder.append(this.getDecimationFactor(), 5);
		builder.append(this.getDecimationOffset(), 5);

		builder.append(this.getEstimatedDelay(), "-0.0000E-00", 11);
		builder.append(this.getCorrection(), "-0.0000E-00", 11);

		builder.replace(3, 7, builder.length(), "####");
		return builder.toString();
	}

	@Override
	public int getStageNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	public BlocketteBuilder<B047> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B047> {

		public Builder() {
			super(47);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B047 build(boolean relax) throws SeedException {
			int offset = 7;
			B047 b = new B047();

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
}
