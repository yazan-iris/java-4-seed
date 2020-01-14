package edu.iris.seed.record;

import edu.iris.seed.Blockette;
import edu.iris.seed.Record;
import edu.iris.seed.record.Header.Type;

public abstract class SeedRecord<T extends Blockette> implements Record<T> {

	private Header header;
	protected byte[] bytes;

	public SeedRecord(Header header) {
		this.header = header;
	}

	public int getSequence() {
		return header.getSequence();
	}

	public Type getType() {
		return header.getType();
	}

	public boolean isContinuation() {
		return header.isContinuation();
	}

	void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public Header getHeader() {
		return header;
	}

	public byte[] getBytes() {
		return bytes;
	}
}
