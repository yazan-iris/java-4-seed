package edu.iris.seed.data;

import java.io.IOException;

import edu.iris.seed.SeedException;


public class B300 extends AbstractDataBlockette{

	public B300() {
		super(300, "Step Calibration Blockette");
	}
	@Override
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");

		return builder.toString();
	}
	public static B300 build(byte[] bytes, boolean swap)
			throws IOException {

		// validate(bytes, 300, 60);
		B300 b = new B300();
		return b;
	}

}
