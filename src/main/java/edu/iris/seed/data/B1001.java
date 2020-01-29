package edu.iris.seed.data;

import java.io.IOException;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedByteArrayBuilder;
import edu.iris.seed.SeedException;

public class B1001 extends AbstractDataBlockette {

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

	public void setPositionOfNextBlockette(long positionOfNextBlockette) {
		this.positionOfNextBlockette = positionOfNextBlockette;
	}

	public int getTimingQuality() {
		return timingQuality;
	}

	public void setTimingQuality(int timingQuality) {
		this.timingQuality = timingQuality;
	}

	public int getMicroSeconds() {
		return microSeconds;
	}

	public void setMicroSeconds(int microSeconds) {
		this.microSeconds = microSeconds;
	}

	public int getFrameCount() {
		return frameCount;
	}

	public void setFrameCount(int frameCount) {
		this.frameCount = frameCount;
	}


	@Override
	public byte[] toSeedBytes()throws SeedException{
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(12).appendU16((short) 1001);
		builder.appendU16((this.getNextBlocketteByteNumber()));
		builder.append((byte) this.timingQuality);
		builder.append((byte) (this.microSeconds));
		builder.append(this.reserved);
		builder.append((byte) this.frameCount);

		return builder.toBytes();
	}

	public static B1001 build(byte[] bytes, boolean swap) throws IOException {

		// validate(bytes, 1001, 8);
		B1001 b = new B1001();
		b.setTimingQuality(bytes[4]);
		b.setMicroSeconds(bytes[5]);
		b.setFrameCount(bytes[7]);
		return b;
	}

	public BlocketteBuilder<B1001> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B1001> {
		public Builder() {
			super(1001);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B1001 build() throws SeedException { 
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int type = ByteUtil.readUnsignedShort(bytes, 0, 2);
			if (1001 != type) {
				throw new SeedException("Invalid blockette type {}", type);
			}
			B1001 b = new B1001();
			b.setTimingQuality(bytes[4]);
			b.setMicroSeconds(bytes[5]);
			b.setFrameCount(bytes[7]);
			b.setNextBlocketteByteNumber(ByteUtil.readUnsignedShort(bytes, 2, 2));
			return b;
		}
	}
}
