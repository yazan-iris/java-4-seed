package edu.iris.seed.data;

import java.io.IOException;

import edu.iris.seed.SeedException;


public class B395 extends AbstractDataBlockette{

	public B395() {
		super(395, "Calibration Abort Blockette");
	}
	@Override
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");

		return builder.toString();
	}
	public static B395 build(byte[] bytes, boolean swap)
			throws IOException {
		// validate(bytes, 395, 16);
		B395 b = new B395();
		return b;
	}
}
