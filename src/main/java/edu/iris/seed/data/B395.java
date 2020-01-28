package edu.iris.seed.data;

import java.nio.ByteOrder;

import edu.iris.seed.BTime;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedByteArrayBuilder;
import edu.iris.seed.SeedException;

public class B395 extends AbstractDataBlockette {

	private BTime endOfCalibrationTime;
	private byte reserved;

	public B395() {
		super(395, "Calibration Abort Blockette");
	}
	
	@Override
	public byte[] toSeedBytes()throws SeedException{
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(16).appendU16((short) 395);
		this.getNextBlocketteByteNumber();

		builder.append(this.endOfCalibrationTime).appendU8((byte) this.reserved);

		return builder.toBytes();
	}

	public BlocketteBuilder<B395> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B395> {
		public Builder() {
			super(395);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B395 build() throws SeedException { 
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int type = ByteUtil.readUnsignedShort(bytes, 0, 2);
			if (395 != type) {
				throw new SeedException("Invalid blockette type {}", type);
			}
			ByteUtil.readUnsignedShort(bytes, 2, 2);
			B395 b = new B395();
			b.setNextBlocketteByteNumber(ByteUtil.readUnsignedShort(bytes, 2, 2));
			return b;
		}
	}
}
