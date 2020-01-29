package edu.iris.seed.data;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedException;

public class B202 extends AbstractDataBlockette {

	public B202() {
		super(202, "Log-Z Event Detection Blockette");
	}

	@Override
	public byte[] toSeedBytes()throws SeedException{
		// TODO Auto-generated method stub
		return null;
	}

	public BlocketteBuilder<B202> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B202> {
		public Builder() {
			super(202);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B202 build() throws SeedException { 
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int type = ByteUtil.readUnsignedShort(bytes, 0, 2);
			if (202 != type) {
				throw new SeedException("Invalid blockette type {}", type);
			}
			ByteUtil.readUnsignedShort(bytes, 2, 2);
			B202 b = new B202();
			b.setNextBlocketteByteNumber(ByteUtil.readUnsignedShort(bytes, 2, 2));
			return b;
		}
	}
}
