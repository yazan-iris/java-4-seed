package edu.iris.seed.abbreviation;

import edu.iris.seed.Blockette;
import edu.iris.seed.ControlBlockette;

public interface AbbreviationBlockette extends ControlBlockette {
	public int getLookupKey();

	public void setLookupKey(int lookupKey);
}
