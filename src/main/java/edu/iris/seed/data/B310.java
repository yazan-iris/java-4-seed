package edu.iris.seed.data;

import edu.iris.seed.BTime;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedByteArrayBuilder;
import edu.iris.seed.SeedException;

public class B310 extends AbstractDataBlockette<B310> {

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
	private float periodOfSignalInSeconds;

	private float amptitudeOfSignal;
	private String channelWithCalibrationInput;
	private byte reserved2;
	private long referenceAmplitude;
	private String coupling;
	private String rolloff;

	public B310() {
		super(310, "Sine Calibration Blockette ");
	}

	@Override
	public byte[] toSeedBytes()throws SeedException{
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(60).appendU16((short) 310);
		this.getNextBlocketteByteNumber();

		builder.append(this.beginningOfCalibrationTime).appendU8((byte) this.reserved1);
		builder.appendU8((byte) this.calibrationFlags);
		builder.appendLong(this.calibrationDuration);

		builder.appendFloat(this.periodOfSignalInSeconds);
		builder.appendFloat(this.amptitudeOfSignal);
		builder.appendU8((byte) this.reserved2);
		builder.append(this.channelWithCalibrationInput, 3);
		builder.appendLong(this.referenceAmplitude).append(this.coupling, 12).append(this.rolloff, 12);

		return builder.toBytes();
	}

	public BlocketteBuilder<B310> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B310> {
		public Builder() {
			super(310);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B310 build() throws SeedException { 
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int type = ByteUtil.readUnsignedShort(bytes, 0, 2);
			if (310 != type) {
				throw new SeedException("Invalid blockette type {}", type);
			}
			ByteUtil.readUnsignedShort(bytes, 2, 2);
			B310 b = new B310();
			b.setNextBlocketteByteNumber(ByteUtil.readUnsignedShort(bytes, 2, 2));
			return b;
		}
	}
}
