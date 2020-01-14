package edu.iris.seed.data;

import java.io.IOException;

import edu.iris.seed.SeedException;

public class B405 extends AbstractDataBlockette{

	public B405() {
		super(405, "Beam Delay Blockette");
	}
	@Override
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");

		return builder.toString();
	}
	public static B405 build(byte[] bytes, boolean swap) throws IOException {
		B405 b = new B405();
		return b;
	}
}
