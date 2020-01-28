package edu.iris.seed;


public interface DataBlockette extends Blockette {

	public int getNextBlocketteByteNumber();

	/**
	 * 
	 * @return return bytes in Big-Endian
	 * @throws SeedException
	 */
	public byte[] toSeedBytes() throws SeedException;
}
