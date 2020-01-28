package edu.iris.seed.data;

import java.nio.ByteOrder;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedByteArrayBuilder;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedStringBuilder;

public class B100 extends AbstractDataBlockette {

	private float actualSampleRate;
	private byte flags;
	private byte[] reserved;

	private B100(float actualSampleRate) {
		super(100, "Sample Rate Blockette");
		this.actualSampleRate = actualSampleRate;
	}

	public float getActualSampleRate() {
		return actualSampleRate;
	}

	public void setActualSampleRate(float actualSampleRate) {
		this.actualSampleRate = actualSampleRate;
	}

	public byte getFlags() {
		return flags;
	}

	public void setFlags(byte flags) {
		this.flags = flags;
	}

	public byte[] getReserved() {
		return reserved;
	}

	public void setReserved(byte[] reserved) {
		this.reserved = reserved;
	}

	@Override
	public byte[] toSeedBytes()throws SeedException {
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(12).appendU16((short) 100);
		builder.appendU16((short) this.getNextBlocketteByteNumber());
		builder.appendFloat(this.actualSampleRate);
		builder.appendU8(this.flags);
		builder.appendU(new byte[3]);
		return builder.toBytes();
	}

	public BlocketteBuilder<B100> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B100> {

		private float actualSampleRate;

		private Builder() {
			super(100);
		}

		private Builder(float actualSampleRate) {
			super(100);
			this.actualSampleRate = actualSampleRate;
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public static Builder newInstance(float actualSampleRate) {
			return new Builder(actualSampleRate);
		}

		public B100 build() throws SeedException {
			B100 b = null;
			if (bytes != null) {
				b = new B100(ByteUtil.fourBytesToFloat(bytes, 4, 4));
				b.setNextBlocketteByteNumber(ByteUtil.readUnsignedShort(bytes, 2, 2));
			} else {
				b = new B100(actualSampleRate);
			}
			return b;
		}

	}
}
