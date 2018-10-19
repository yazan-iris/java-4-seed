package edu.iris.dmc.seed.record;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.iris.dmc.io.SeedFormatter;
import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.BlocketteFactory;
import edu.iris.dmc.seed.IncompleteBlockette;
import edu.iris.dmc.seed.Record;
import edu.iris.dmc.seed.SeedException;

abstract class AbstractRecord implements Record {

	private int sequence;
	private char type;
	private boolean continuation;
	private byte[] bytes;

	private int length;
	private int index;

	public AbstractRecord(int sequence, char type, boolean continuation) throws SeedException {
		this(sequence, type, continuation, 4096);
	}

	public AbstractRecord(int sequence, char type, boolean continuation, int size) throws SeedException {
		this.length = size;
		this.sequence = sequence;
		this.type = type;
		this.continuation = continuation;
		this.bytes = new byte[size];

		StringBuilder sb = new StringBuilder(SeedFormatter.format(this.sequence, 6));
		sb.append(type);
		if (continuation) {
			sb.append('*');
		} else {
			sb.append(' ');
		}

		byte[] s = buildSequence();
		System.arraycopy(s, 0, this.bytes, 0, s.length);
	}

	protected byte[] buildSequence() throws SeedException {
		StringBuilder sb = new StringBuilder(SeedFormatter.format(this.sequence, 6));
		sb.append(type);
		if (continuation) {
			sb.append('*');
		} else {
			sb.append(' ');
		}

		byte[] bytes= sb.toString().getBytes();
		index = bytes.length;
		return bytes;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public char getType() {
		return type;
	}

	public void setType(char type) {
		this.type = type;
	}

	public void setContinuation(boolean continuation) {
		this.continuation = continuation;
	}

	public boolean isContinuation() {
		return continuation;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public byte[] getBytes() throws SeedException {
		return bytes;
	}

	public byte[] get(int length) {
		if (length > this.bytes.length - 8) {
			length = this.bytes.length - 8;
		}
		byte[] bytes = new byte[length];
		System.arraycopy(this.bytes, index, bytes, 0, length);
		index += length;
		return bytes;
	}

	public int index() {
		return this.index;
	}

	public int size() {
		return this.length;
	}

	@Override
	public void add(Blockette blockette) {
		// TODO Auto-generated method stub

	}

	/**
	 * 
	 * @param src
	 * @param blocketteIndex
	 * @return number of rejected bytes
	 */
	public byte[] add(byte[] src) {
		int availableBytes = this.getAvailableBytes();

		if (availableBytes < 8) {
			return src;
		}
		if (availableBytes >= src.length) {
			System.arraycopy(src, 0, this.bytes, this.index, src.length);
			this.index += src.length;
			return null;
		} else {
			System.arraycopy(src, 0, this.bytes, this.index, availableBytes);
			byte[] remaining = new byte[src.length - availableBytes];
			System.arraycopy(src, availableBytes, remaining, 0, remaining.length);
			return remaining;
		}
	}

	public int getAvailableBytes() {
		return this.length - this.index;
	}

	public Blockette next() throws SeedException {
		if (this.bytes == null || index >= bytes.length - 1) {
			return null;
		}
		if (index + 3 >= bytes.length) {
			return null;
		}

		if ("".equals(new String(bytes, index, 3).trim())) {
			return null;
		}
		int type = Integer.parseInt(new String(bytes, index, 3).trim());
		if (index + 3 >= bytes.length) {
			return null;
		}

		if (index + 7 > bytes.length || "".equals(new String(bytes, index + 3, 4).trim())) {
			return null;
		}
		int length = Integer.parseInt(new String(bytes, index + 3, 4).trim());
		if (length + index <= bytes.length) {
			byte[] b = new byte[length];
			System.arraycopy(bytes, index, b, 0, length);
			index += length;
			return BlocketteFactory.createBlockette(type, b);
		} else {
			// create incomplete blockette
			byte[] bb = new byte[bytes.length - index];
			System.arraycopy(bytes, index, bb, 0, bb.length);
			IncompleteBlockette b = new IncompleteBlockette(length, bb);
			index = bb.length;//bytes.length;
			return b;
		}
	}

	public List<Object[]> iterate() throws SeedException, IOException {

		List<Object[]> list = new ArrayList<>();
		int index = 0;

		while (true) {
			if (index + 7 >= bytes.length) {
				break;
			}
			String typeString = new String(bytes, index + 3, 4);
			if (typeString.trim().isEmpty()) {
				break;
			}
			int length = Integer.parseInt(new String(bytes, index + 3, 4));
			int end = length + index;
			if (end > bytes.length) {
				end = bytes.length;
			}

			list.add(new Object[] { length, Arrays.copyOfRange(bytes, index, end) });
			index += length;
		}
		return list;

	}

}
