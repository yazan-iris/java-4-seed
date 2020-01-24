package edu.iris.seed;

import java.nio.charset.StandardCharsets;

import edu.iris.seed.data.ByteUtil;

public abstract class BlocketteBuilder<T extends Blockette> {

	private int type;
	protected int index;
	protected byte[] bytes;

	public BlocketteBuilder(int type) {
		this.type = type;
	}

	public abstract T build(boolean relax) throws SeedException;

	public T build() throws SeedException {
		return build(false);
	}

	public BlocketteBuilder<T> fromString(String s) throws SeedException {
		return fromBytes(0, s.getBytes(StandardCharsets.US_ASCII));
	}

	public BlocketteBuilder<T> fromBytes(byte[] bytes) throws SeedException {
		return fromBytes(0, bytes);
	}

	public BlocketteBuilder<T> fromBytes(int index, byte[] bytes) throws SeedException {
		if (bytes == null) {
			throw new SeedException("bytes cannot be null");
		}
		if (this.type < 100) {
			if (bytes.length - index < 7) {
				throw new SeedException("Building {}, byte array is too short, expected at least 2 but received {}",
						this.type, bytes.length - index);
			}
			int type = Integer.parseInt(new String(bytes, index, 3).trim());
			if (this.type != type) {
				throw new SeedException("Invalid blockette type, expected {} but was {}", this.type, type);
			}
			int length = Integer.parseInt(new String(bytes, index + 3, 4).trim());
			if (bytes.length - index < length) {
				throw new SeedException("Building {}, byte array is too short, expected {} but received {}", type,
						length, bytes.length - index);
			}
		} else {
			if (bytes.length < 2) {
				throw new SeedException("Building {}, byte array is too short.", this.type);
			}
			int type = ByteUtil.readUnsignedShort(bytes, index, 2);
			if (this.type != type) {
				throw new SeedException("Invalid blockette type, expected {} but was {}", this.type, type);
			}
		}
		this.index = index;
		this.bytes = bytes;
		return this;
	}
}
