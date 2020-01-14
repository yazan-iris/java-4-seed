package edu.iris.seed.abbreviation;

import edu.iris.seed.SeedBlockette;

public abstract class AbstractAbbreviationBlockette extends SeedBlockette {
	public AbstractAbbreviationBlockette(int type, String title) {
		super(type, title);
	}

	private int lookupKey;

	public int getLookupKey() {
		return lookupKey;
	}

	public void setLookupKey(int lookupKey) {
		this.lookupKey = lookupKey;
	}
}
