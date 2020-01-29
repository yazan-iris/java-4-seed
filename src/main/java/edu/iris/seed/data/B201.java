package edu.iris.seed.data;

import edu.iris.seed.BTime;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedByteArrayBuilder;
import edu.iris.seed.SeedException;

public class B201 extends AbstractDataBlockette<B201> {

	private float signalAmplitude;
	private float signalPeriod;
	private float backgroundEstimate;
	private byte eventDetectionFlags;
	private byte reserved;
	private BTime signalOnsetTime;

	private byte[] signalToNoiseRatioValues;
	private byte lookbackValue;
	private byte pickAlgorithm;
	private String detectorName;

	public B201() {
		super(201, "Murdock Event Detection Blockette");
	}

	@Override
	public byte[] toSeedBytes()throws SeedException{
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(60).appendU16((short) 201);
		builder.appendU16(this.getNextBlocketteByteNumber());
		builder.appendFloat(this.signalAmplitude);
		builder.appendFloat(this.signalPeriod);
		builder.appendFloat(this.backgroundEstimate);
		builder.appendU8(this.eventDetectionFlags);

		builder.appendU8(this.reserved);
		builder.append(this.signalOnsetTime);

		builder.append(this.signalToNoiseRatioValues);
		builder.appendU8(this.lookbackValue);
		builder.appendU8(this.pickAlgorithm);

		builder.append(this.detectorName, 24);

		return builder.toBytes();
	}

	public BlocketteBuilder<B201> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B201> {

		public Builder() {
			super(201);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B201 build() throws SeedException { 
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int type = ByteUtil.readUnsignedShort(bytes, 0, 2);
			if (201 != type) {
				throw new SeedException("Invalid blockette type {}", type);
			}

			B201 b = new B201();
			b.setNextBlocketteByteNumber(ByteUtil.readUnsignedShort(bytes, 2, 2));
			return b;
		}
	}
}
