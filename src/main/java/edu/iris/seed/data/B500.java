package edu.iris.seed.data;

import edu.iris.seed.BTime;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedByteArrayBuilder;
import edu.iris.seed.SeedException;

public class B500 extends AbstractDataBlockette<B500> {

	private float vocCorrection;
	private BTime timeOfException;
	private int clockTimeDownToTheMicrosecond;
	private byte receptionQuality;
	private long exceptionCount;
	private String exceptionType;
	private String clockModel;
	private String clockStatus;

	public B500() {
		super(500, "Timing Blockette");
	}

	@Override
	public byte[] toSeedBytes()throws SeedException{
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(200).appendU16((short) 500);

		builder.appendFloat(vocCorrection);
		builder.append(timeOfException);
		builder.append((byte) clockTimeDownToTheMicrosecond);
		builder.appendU8((byte) receptionQuality);
		builder.appendFloat(exceptionCount).append(exceptionType, 16).append(clockModel, 32).append(clockStatus, 128);
		return builder.toBytes();
	}

	public BlocketteBuilder<B500> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B500> {

		public Builder() {
			super(500);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B500 build() throws SeedException { 
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int type = ByteUtil.readUnsignedShort(bytes, 0, 2);
			if (500 != type) {
				throw new SeedException("Invalid blockette type {}", type);
			}
			ByteUtil.readUnsignedShort(bytes, 2, 2);
			B500 b = new B500();
			b.setNextBlocketteByteNumber(ByteUtil.readUnsignedShort(bytes, 2, 2));
			return b;
		}
	}

}
