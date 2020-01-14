package edu.iris.seed.data;

import java.io.IOException;

import edu.iris.seed.SeedException;


public class B320 extends AbstractDataBlockette{

	public B320() {
		super(320, " Pseudo-random Calibration Blockette");
	}
	@Override
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");

		return builder.toString();
	}
	public static B320 build(byte[] bytes, boolean swap)
			throws IOException {
		//validate(bytes, 320, 64);
		B320 b = new B320();
		return b;
	}
}
