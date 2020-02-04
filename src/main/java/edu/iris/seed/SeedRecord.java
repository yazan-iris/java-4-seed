package edu.iris.seed;

import edu.iris.seed.SeedHeader.Type;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class SeedRecord<T extends Blockette> implements Record<T> {
	private SeedHeader header;

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

	@Override
	public SeedHeader getHeader() {
		return header;
	}
}
