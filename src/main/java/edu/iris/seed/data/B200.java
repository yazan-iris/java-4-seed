package edu.iris.seed.data;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedByteArrayBuilder;
import edu.iris.seed.SeedException;

public class B200 extends AbstractDataBlockette {

	private float signalAmplitude;
	private float signalPeriod;
	private float backgroundEstimate;
	private byte eventDetectionFlags;
	private byte reserved;
	private byte[] signalOnsetTime;
	private String detectorName;

	public B200() {
		super(200, "Generic Event Detection Blockette");
	}

	@Override
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");

		return builder.toString();
	}

	@Override
	public byte[] toSeedBytes() {
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(52).appendU16((short) 200);
		this.getNextBlocketteByteNumber();
		builder.append(4, this.signalAmplitude);
		builder.append(8, this.signalPeriod);
		builder.append(12, this.backgroundEstimate);
		builder.append(this.eventDetectionFlags);

		builder.append(this.reserved);
		builder.append(this.signalOnsetTime);
		builder.append(this.detectorName, 24);

		return builder.toBytes();
	}

	public BlocketteBuilder<B200> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B200> {
		private byte[] bytes;

		public Builder() {
			super(200);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public static Builder newInstance(byte[] bytes) {
			return new Builder();
		}

		public B200 build() throws SeedException {
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int type = ByteUtil.readUnsignedShort(bytes, 0, 2);
			if (200 != type) {
				throw new SeedException("Invalid blockette type {}", type);
			}
			ByteUtil.readUnsignedShort(bytes, 2, 2);
			B200 b = new B200();
			b.setNextBlocketteByteNumber(ByteUtil.readUnsignedShort(bytes, 2, 2));
			return b;
		}
	}

}
