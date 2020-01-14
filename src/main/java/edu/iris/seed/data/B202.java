package edu.iris.seed.data;

import edu.iris.seed.SeedException;

public class B202 extends AbstractDataBlockette{

	public B202() {
		super(202, "Log-Z Event Detection Blockette");
	}
	@Override
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");

		return builder.toString();
	}
}
