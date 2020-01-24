package edu.iris.seed.data;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedByteArrayBuilder;
import edu.iris.seed.SeedException;

public class B400 extends AbstractDataBlockette {

	private float beamAzimuthInDegrees;
	private float beamSlownessInSecDegree;
	private short beamConfiguration;
	private short reserved;

	public B400() {
		super(400, "Beam Blockette");
	}

	@Override
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");

		return builder.toString();
	}

	@Override
	public byte[] toSeedBytes() {
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(16).appendU16((short) 400);
		this.getNextBlocketteByteNumber();

		builder.append(this.beamAzimuthInDegrees).append(this.beamSlownessInSecDegree).appendU16(this.beamConfiguration)
				.appendU16(this.reserved);

		return builder.toBytes();
	}

	public BlocketteBuilder<B400> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B400> {
		public Builder() {
			super(400);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B400 build() throws SeedException {
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int type = ByteUtil.readUnsignedShort(bytes, 0, 2);
			if (400 != type) {
				throw new SeedException("Invalid blockette type {}", type);
			}
			ByteUtil.readUnsignedShort(bytes, 2, 2);
			B400 b = new B400();
			b.setNextBlocketteByteNumber(ByteUtil.readUnsignedShort(bytes, 2, 2));
			return b;
		}
	}

}
