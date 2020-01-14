package edu.iris.seed.data;

import java.io.IOException;

import edu.iris.seed.SeedException;


public class B500 extends AbstractDataBlockette{

	public B500() {
		super(500, "Timing Blockette");
	}

	@Override
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");
		return builder.toString();
	}
	
	public static B500 build(byte[] bytes, boolean swap)
			throws IOException {
		// validate(bytes, 500, 200);
		B500 b = new B500();
		return b;
	}

}
