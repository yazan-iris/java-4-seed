package edu.iris.seed.data;

import java.io.IOException;

import edu.iris.seed.SeedException;


public class B201 extends AbstractDataBlockette{

	public B201() {
		super(201, "Murdock Event Detection Blockette");
	}
	@Override
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");

		return builder.toString();
	}
	public static B201 build(byte[] bytes, boolean swap)
			throws IOException {
		// validate(bytes, 200, 60);
		B201 b = new B201();
		return b;
	}

}
