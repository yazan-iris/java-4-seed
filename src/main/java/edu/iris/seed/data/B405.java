package edu.iris.seed.data;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedByteArrayBuilder;
import edu.iris.seed.SeedException;

public class B405 extends AbstractDataBlockette {

	private short arrayOfDelayValues;

	public B405() {
		super(405, "Beam Delay Blockette");
	}

	@Override
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");

		return builder.toString();
	}

	@Override
	public byte[] toSeedBytes() {
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(6).appendU16((short) 405);
		this.getNextBlocketteByteNumber();

		builder.appendU16(this.arrayOfDelayValues);

		return builder.toBytes();
	}

	public BlocketteBuilder<B405> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B405> {

		public Builder() {
			super(405);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B405 build() throws SeedException { 
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int type = ByteUtil.readUnsignedShort(bytes, 0, 2);
			if (405 != type) {
				throw new SeedException("Invalid blockette type {}", type);
			}
			ByteUtil.readUnsignedShort(bytes, 2, 2);
			B405 b = new B405();
			b.setNextBlocketteByteNumber(ByteUtil.readUnsignedShort(bytes, 2, 2));
			return b;
		}
	}
}
