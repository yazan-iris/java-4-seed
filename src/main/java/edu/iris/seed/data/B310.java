package edu.iris.seed.data;

import java.io.IOException;

import edu.iris.seed.SeedException;


public class B310 extends AbstractDataBlockette{

	public B310() {
		super(310, "Sine Calibration Blockette ");
	}
	@Override
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");

		return builder.toString();
	}
	public static B310 build(byte[] bytes, boolean swap)
			throws IOException {
		//validate(bytes, 310, 60);
		B310 b = new B310();
		return b;
	}

}
