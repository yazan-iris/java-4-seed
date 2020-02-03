package edu.iris.seed.data;

import java.io.IOException;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedByteArrayBuilder;
import edu.iris.seed.SeedException;

public class B1001 extends AbstractDataBlockette<B1001> {

	private long positionOfNextBlockette;
	private int timingQuality;
	private int microSeconds;
	private byte reserved;
	private int frameCount;

	public B1001() {
		super(1001, "Data Extension Blockette");
	}

	public long getPositionOfNextBlockette() {
		return positionOfNextBlockette;
	}

	public int getTimingQuality() {
		return timingQuality;
	}

	public int getMicroSeconds() {
		return microSeconds;
	}

	public int getFrameCount() {
		return frameCount;
	}

	@Override
	public byte[] toSeedBytes() throws SeedException {
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(12).appendU16((short) 1001);
		builder.appendU16((this.getNextBlocketteByteNumber()));
		builder.append((byte) this.timingQuality);
		builder.append((byte) (this.microSeconds));
		builder.append(this.reserved);
		builder.append((byte) this.frameCount);

		return builder.toBytes();
	}

	public BlocketteBuilder<B1001> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B1001> {
		private int timingQuality;
		private int microSeconds;
		private byte reserved;
		private int frameCount;

		public Builder() {
			super(1001);
		}

		public Builder timingQuality(int timingQuality) {
			this.timingQuality = timingQuality;
			return this;
		}

		public Builder microSeconds(int microSeconds) {
			this.microSeconds = microSeconds;
			return this;
		}

		public Builder frameCount(int frameCount) {
			this.frameCount = frameCount;
			return this;
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B1001 build() throws SeedException {
			if (bytes != null) {
				return buildFromBytes();
			} else {
				return buildFromValues();
			}

		}

		private B1001 buildFromValues() throws SeedException {
			B1001 b = new B1001();
			b.frameCount = frameCount;
			b.microSeconds = microSeconds;
			b.timingQuality = timingQuality;
			return b;
		}

		private B1001 buildFromBytes() throws SeedException {
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int type = ByteUtil.readUnsignedShort(bytes, 0, 2);
			if (1001 != type) {
				throw new SeedException("Invalid blockette type {}", type);
			}
			B1001 b = new B1001();
			b.timingQuality = bytes[4];
			b.microSeconds = bytes[5];
			b.frameCount = bytes[7];
			b.nextBlocketteByteNumber = ByteUtil.readUnsignedShort(bytes, 2, 2);
			return b;
		}
	}
}
