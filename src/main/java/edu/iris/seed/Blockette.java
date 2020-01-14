package edu.iris.seed;

public interface Blockette {

	public int getType();

	//public int getLength();

	public String toSeedString() throws SeedException;
}
