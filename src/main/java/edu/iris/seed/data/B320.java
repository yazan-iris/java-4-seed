package edu.iris.seed.data;

import java.nio.ByteOrder;

import edu.iris.seed.BTime;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedByteArrayBuilder;
import edu.iris.seed.SeedException;

public class B320 extends AbstractDataBlockette {

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

	private float peakToPeakAmplitudeOfSteps;
	private String channelWithCalibrationInput;
	private byte reserved2;
	private long referenceAmplitude;
	private String coupling;
	private String rolloff;
	private String noiseType;

	public B320() {
		super(320, " Pseudo-random Calibration Blockette");
	}

	@Override
	public byte[] toSeedBytes()throws SeedException{
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(64).appendU16((short) 320);
		this.getNextBlocketteByteNumber();

		builder.append(this.beginningOfCalibrationTime).appendU8((byte) this.reserved1);
		builder.appendU8((byte) this.calibrationFlags);
		builder.appendLong(this.calibrationDuration);
		builder.appendFloat(peakToPeakAmplitudeOfSteps);
		builder.append(this.channelWithCalibrationInput, 3);
		builder.appendU8((byte) this.reserved2);
		builder.appendLong(this.referenceAmplitude);
		builder.append(this.coupling, 12).append(this.rolloff, 12).append(this.noiseType, 8);

		return builder.toBytes();
	}

	public BlocketteBuilder<B320> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B320> {
		public Builder() {
			super(320);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B320 build() throws SeedException { 
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int type = ByteUtil.readUnsignedShort(bytes, 0, 2);
			if (320 != type) {
				throw new SeedException("Invalid blockette type {}", type);
			}
			ByteUtil.readUnsignedShort(bytes, 2, 2);
			B320 b = new B320();
			b.setNextBlocketteByteNumber(ByteUtil.readUnsignedShort(bytes, 2, 2));
			return b;
		}
	}
}
