package edu.iris.seed.data;

import java.io.IOException;

import edu.iris.seed.SeedException;

public class B200 extends AbstractDataBlockette {

	public B200() {
		super(200, "Generic Event Detection Blockette");
	}

	@Override
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");

		return builder.toString();
	}

	public static B200 build(byte[] bytes, boolean swap) throws IOException {

		// validate(bytes, 200, 52);
		B200 b = new B200();
		return b;
	}

}
