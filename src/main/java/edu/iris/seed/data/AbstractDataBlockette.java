package edu.iris.seed.data;

import edu.iris.seed.SeedBlockette;

public abstract class AbstractDataBlockette extends SeedBlockette implements DataBlockette {

	public AbstractDataBlockette(int type, String title) {
		super(type, title);
	}

	public int nextBlocketteIndex() {
		return 0;
	}
}
