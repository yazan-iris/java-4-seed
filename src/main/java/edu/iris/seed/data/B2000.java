package edu.iris.seed.data;

import java.io.IOException;

import edu.iris.seed.SeedException;


public class B2000 extends AbstractDataBlockette{

	public B2000() {
		super(2000, "Variable Length Opaque Data Blockette");
	}
	@Override
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");

		return builder.toString();
	}
	public static B2000 build(byte[] bytes, boolean swap)
			throws IOException {
		//validate(bytes, 2000, -1);
		B2000 b = new B2000();
		return b;
	}

}
