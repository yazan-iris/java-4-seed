package edu.iris.dmc.seed.control.dictionary;

import edu.iris.dmc.seed.AbstractBlockette;

public abstract class AbstractDictionaryBlockette extends AbstractBlockette implements DictionaryBlockette {

	public AbstractDictionaryBlockette(int type, String title) {
		super(type, title);
	}

	protected int lookupKey;

	@Override
	public int getLookupKey() {
		return this.lookupKey;
	}

	public void setLookupKey(int lookupKey) {
		this.lookupKey = lookupKey;
	}

}
