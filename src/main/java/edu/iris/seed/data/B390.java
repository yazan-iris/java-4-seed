package edu.iris.seed.data;

import java.nio.ByteOrder;

import edu.iris.seed.BTime;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedByteArrayBuilder;
import edu.iris.seed.SeedException;

public class B390 extends AbstractDataBlockette {

	private BTime beginningOfCalibrationTime;
	private byte reserved1;
	/**
	 * Calibration flags: [Bit 0] — If set: first pulse is positive [Bit 1] — If
	 * set: calibration’s alternate sign [Bit 2] — If set: calibration was
	 * automatic; if unset: manual [Bit 3] — If set: calibration continued from
	 * previous record(s) [Other bits reserved and must be zero.]
	 */
	private byte calibrationFlags;
	private long calibrationDuration;
	private float calibrationSignalAmplitude;

	private String channelWithCalibrationInput;
	private byte reserved2;


	public B390() {
		super(390, "Generic Calibration Blockette ");
	}

	@Override
	public byte[] toSeedBytes()throws SeedException{
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(28).appendU16((short) 390);
		this.getNextBlocketteByteNumber();

		builder.append(this.beginningOfCalibrationTime).appendU8((byte) this.reserved1);
		builder.appendU8((byte) this.calibrationFlags);
		builder.appendLong(this.calibrationDuration);

		builder.appendFloat(calibrationSignalAmplitude);
		builder.append(this.channelWithCalibrationInput, 3);
		builder.appendU8((byte) this.reserved2);

		return builder.toBytes();
	}

	public BlocketteBuilder<B390> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B390> {
		public Builder() {
			super(390);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B390 build() throws SeedException { 
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int type = ByteUtil.readUnsignedShort(bytes, 0, 2);
			if (390 != type) {
				throw new SeedException("Invalid blockette type {}", type);
			}
			ByteUtil.readUnsignedShort(bytes, 2, 2);
			B390 b = new B390();
			b.setNextBlocketteByteNumber(ByteUtil.readUnsignedShort(bytes, 2, 2));
			return b;
		}
	}
}
