package edu.iris.seed.data;

import edu.iris.seed.Blockette;

public interface DataBlockette extends Blockette {

	public int getNextBlocketteByteNumber();

	public byte[] toSeedBytes();
}
