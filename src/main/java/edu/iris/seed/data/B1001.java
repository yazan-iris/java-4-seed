package edu.iris.seed.data;

import java.io.IOException;

import edu.iris.seed.SeedException;

public class B1001 extends AbstractDataBlockette{

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
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");
		builder.append(this.timingQuality);
		builder.append(this.microSeconds);
		builder.append(this.reserved);
		builder.append(this.frameCount);

		return builder.toString();
	}

	public static B1001 build(byte[] bytes, boolean swap) throws IOException {

		// validate(bytes, 1001, 8);
		B1001 b = new B1001();
		b.setTimingQuality(bytes[4]);
		b.setMicroSeconds(bytes[5]);
		b.setFrameCount(bytes[7]);
		return b;
	}

}
