package edu.iris.seed;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iris.seed.SeedHeader.Type;

public abstract class SeedRecord<T extends Blockette> implements Record<T> {
	private static final Logger logger = LoggerFactory.getLogger(SeedRecord.class.getName());

	private SeedHeader header;
	protected byte[] bytes;

	public SeedRecord(SeedHeader header) {
		this.header = header;
	}

	public int getSequence() {
		return header.getSequence();
	}

	public Type getType() {
		return header.getRecordType();
	}

	public boolean isContinuation() {
		return header.isContinuation();
	}

	void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	@Override
	public SeedHeader getHeader() {
		return header;
	}

	public byte[] getBytes() {
		return bytes;
	}

}
