package edu.iris.seed.data;


import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedByteArrayBuilder;
import edu.iris.seed.SeedException;

/**
 * This blockette is used to specify how the beam indicated by the corresponding 
 * Beam Configuration Blockette [35] was formed for this data record. 
 * For beams formed by non-plane waves, the Beam Delay Blockette [405] 
 * should be used to determine the beam delay for each component referred 
 * to in the Beam Configuration Blockette [35].
 * @author Suleiman
 *
 */
public class B400 extends AbstractDataBlockette<B400> {

	private float beamAzimuthInDegrees;
	private float beamSlownessInSecDegree;
	private short beamConfiguration;
	private short reserved;

	public B400() {
		super(400, "Beam Blockette");
	}

	@Override
	public byte[] toSeedBytes()throws SeedException{
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(16).appendU16((short) 400);
		this.getNextBlocketteByteNumber();

		builder.appendFloat(this.beamAzimuthInDegrees).appendFloat(this.beamSlownessInSecDegree).appendU16(this.beamConfiguration)
				.appendU16(this.reserved);

		return builder.toBytes();
	}

	public BlocketteBuilder<B400> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B400> {
		public Builder() {
			super(400);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B400 build() throws SeedException { 
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int type = ByteUtil.readUnsignedShort(bytes, 0, 2);
			if (400 != type) {
				throw new SeedException("Invalid blockette type {}", type);
			}
			ByteUtil.readUnsignedShort(bytes, 2, 2);
			B400 b = new B400();
			b.setNextBlocketteByteNumber(ByteUtil.readUnsignedShort(bytes, 2, 2));
			return b;
		}
	}

}
