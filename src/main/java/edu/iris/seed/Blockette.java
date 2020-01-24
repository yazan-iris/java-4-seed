package edu.iris.seed;

public interface Blockette extends SeedObject {

	public int getType();

	// public int getLength();

	public String toSeedString() throws SeedException;
}
