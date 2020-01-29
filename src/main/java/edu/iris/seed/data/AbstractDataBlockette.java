package edu.iris.seed.data;

import edu.iris.seed.DataBlockette;
import edu.iris.seed.SeedBlockette;

public abstract class AbstractDataBlockette<T extends DataBlockette> extends SeedBlockette<T> implements DataBlockette {

	/*
	 * UWORD: Byte number of next blockette. (Calculate this as the byte offset from
	 * the beginning of the logical record — including the fixed section of the data
	 * header; use 0 if no more blockettes will follow.)
	 */
	private int nextBlocketteByteNumber;

	private DataBlockette nextBlockette;

	public AbstractDataBlockette(int type, String title) {
		super(type, title);
	}

	public void setNextBlocketteByteNumber(int nextBlocketteByteNumber) {
		this.nextBlocketteByteNumber = nextBlocketteByteNumber;
	}

	public int getNextBlocketteByteNumber() {
		return nextBlocketteByteNumber;
	}
}
