package edu.iris.seed;

import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.Identifier.IdentifierBlockette;
import edu.iris.seed.abbreviation.AbbreviationBlockette;
import edu.iris.seed.station.StationBlockette;

public class IncompleteBlockette implements IdentifierBlockette, AbbreviationBlockette, StationBlockette {

	private int type;
	private String title;
	private List<byte[]> list = new ArrayList<>();
	private int size;

	private int expected;
	private int actual;

	public IncompleteBlockette(int type, byte[] bytes, int expected) {
		this.type = type;
		this.title = "Incomplete Blockette";
		this.size = Integer.parseInt(new String(bytes, 3, 4).trim());
		this.list.add(bytes);
		this.actual += bytes.length;
		this.expected = expected;
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public int getLookupKey() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLookupKey(int lookupKey) {
		// TODO Auto-generated method stub

	}

	public void append(byte[] bytes) {
		this.actual += bytes.length;
		this.list.add(bytes);
	}

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

	public int numberOfRequiredBytesToComplete() {
		return size - actual;
	}

	public int getExpected() {
		return expected;
	}

	@Override
	public String toSeedString() {
		return "INCOMPLETE:" + this.getType() + ":" + actual + ":" + size;
	}

	public BlocketteBuilder<IncompleteBlockette> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<IncompleteBlockette> {

		public Builder() {
			super(0);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public IncompleteBlockette build() throws SeedException {
			return null;
		}
	}

}
