package edu.iris.seed.data;

import java.io.IOException;

import edu.iris.seed.SeedException;


public class B400 extends AbstractDataBlockette{

	public B400() {
		super(400, "Beam Blockette");
	}
	@Override
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");

		return builder.toString();
	}
	public static B400 build(byte[] bytes, boolean swap)
			throws IOException {
		//validate(bytes, 400, 16);
		B400 b = new B400();
		return b;
	}

}
