package edu.iris.seed.data;

import java.nio.ByteOrder;

import edu.iris.seed.BTime;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedByteArrayBuilder;
import edu.iris.seed.SeedException;

public class B300 extends AbstractDataBlockette {
	private BTime beginningOfCalibrationTime;
	private int numberOfStepCalibrationsInSequence;
	/**
	 * Calibration flags: [Bit 0] — If set: first pulse is positive [Bit 1] — If
	 * set: calibration’s alternate sign [Bit 2] — If set: calibration was
	 * automatic; if unset: manual [Bit 3] — If set: calibration continued from
	 * previous record(s) [Other bits reserved and must be zero.]
	 */
	private byte calibrationFlags;
	private long stepDuration;
	private long intervalDuration;
	private float calibrationSignalAmplitude;
	private String channelWithCalibrationInput;
	private byte reserved;
	private long referenceAmplitude;
	private String coupling;
	private String rolloff;

	public B300() {
		super(300, "Step Calibration Blockette");
	}

	@Override
	public byte[] toSeedBytes()throws SeedException{
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(60).appendU16((short) 300);
		this.getNextBlocketteByteNumber();

		builder.append(this.beginningOfCalibrationTime).appendU8((byte) this.numberOfStepCalibrationsInSequence);
		builder.appendU8((byte) this.calibrationFlags);
		builder.appendLong(this.stepDuration);

		builder.appendFloat(this.intervalDuration);
		builder.appendFloat(this.calibrationSignalAmplitude);
		builder.appendU8((byte) this.reserved);
		builder.append(this.channelWithCalibrationInput, 3);
		builder.appendLong(this.referenceAmplitude).append(this.coupling, 12).append(this.rolloff, 12);

		return builder.toBytes();
	}

	public BlocketteBuilder<B300> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B300> {
		public Builder() {
			super(300);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B300 build() throws SeedException { 
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int type = ByteUtil.readUnsignedShort(bytes, 0, 2);
			if (300 != type) {
				throw new SeedException("Invalid blockette type {}", type);
			}
			ByteUtil.readUnsignedShort(bytes, 2, 2);
			B300 b = new B300();
			b.setNextBlocketteByteNumber(ByteUtil.readUnsignedShort(bytes, 2, 2));
			return b;
		}
	}

}
