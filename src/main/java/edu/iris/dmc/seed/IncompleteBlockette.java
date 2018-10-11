package edu.iris.dmc.seed;

import java.util.ArrayList;
import java.util.List;

public class IncompleteBlockette extends AbstractBlockette implements Blockette {

	private List<byte[]> list=new ArrayList<>();
	private int size;

	private int actual;

	public IncompleteBlockette(int type, byte[] bytes) {
		super(type, "");
		this.size = Integer.parseInt(new String(bytes, 3, 4).trim());
		this.list.add(bytes);
		this.actual += bytes.length;
	}

	public void append(byte[] bytes) {
		this.actual += bytes.length;
		this.list.add(bytes);
	}

	/*
	 * public void append(byte[] bytes) { byte[] array = new byte[this.bytes.length
	 * + bytes.length]; System.arraycopy(this.bytes, 0, array, 0,
	 * this.bytes.length); System.arraycopy(bytes, 0, array, this.bytes.length,
	 * bytes.length); this.bytes= array; }
	 */

	public byte[] getBytes() {
		int totalLength = 0;
		for (byte[] b : list) {
			totalLength += b.length;
		}
		byte[] array = new byte[totalLength];
		int destPos = 0;
		for (byte[] b : list) {
			System.arraycopy(b, 0, array, destPos, b.length);
			destPos += b.length;
		}
		return array;
	}

	public int remainingBytes() {
		return size - actual;
	}

	@Override
	public String toSeedString() {
		// TODO Auto-generated method stub
		return null;
	}

}
