package edu.iris.dmc.seed;

public interface Record {

	public int getSequence();

	public char getType();

	public byte[] getBytes();

	public byte[] get(int length);

	public void setBytes(byte[] bytes);

	public Blockette next() throws SeedException;

	public boolean isContinuation();

	public int size();

	public void add(Blockette blockette);

	/**
	 * 
	 * @param bytes
	 * @return array of bytes that were not added
	 */
	public byte[] add(byte[] bytes);

}
