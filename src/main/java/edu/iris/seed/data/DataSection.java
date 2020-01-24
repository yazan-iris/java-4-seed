package edu.iris.seed.data;

import edu.iris.seed.SeedException;

public class DataSection implements DataBlockette {

	private byte[] bytes;

	public void setData(byte[] bytes) {
		this.bytes = bytes;
	}

	public byte[] getData() {
		return this.bytes;
	}

	@Override
	public int getType() {
		return 0;
	}

	@Override
	public String toSeedString() throws SeedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNextBlocketteByteNumber() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public byte[] toSeedBytes() {
		// TODO Auto-generated method stub
		return null;
	}

}
