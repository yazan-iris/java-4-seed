package edu.iris.seed.data;

import java.nio.ByteOrder;

import edu.iris.seed.DataBlockette;
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
	public int getNextBlocketteByteNumber() {
		return 0;
	}

	@Override
	public byte[] toSeedBytes()throws SeedException{
		// TODO Auto-generated method stub
		return null;
	}

}
