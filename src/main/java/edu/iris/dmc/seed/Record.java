package edu.iris.dmc.seed;

public interface Record {

	public int getSequence();

	public char getType();

	public byte[] getBytes() throws SeedException;

	public int getAvailableBytes();

	public byte[] get(int length);

	public void setBytes(byte[] bytes);

	public Blockette next() throws SeedException;

	public boolean isContinuation();

	public int size();

	public byte[] add(byte[] bytes)throws SeedException;

}
