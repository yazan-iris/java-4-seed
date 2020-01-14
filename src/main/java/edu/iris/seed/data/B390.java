package edu.iris.seed.data;

import java.io.IOException;

import edu.iris.seed.SeedException;

public class B390 extends AbstractDataBlockette{

	public B390() {
		super(390, "Generic Calibration Blockette ");
	}
	@Override
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");

		return builder.toString();
	}
	public static B390 build(byte[] bytes, boolean swap) throws IOException {
		// validate(bytes, 390, 28);
		B390 b = new B390();
		return b;
	}
}
