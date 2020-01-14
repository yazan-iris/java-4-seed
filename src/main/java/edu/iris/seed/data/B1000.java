package edu.iris.seed.data;

import java.nio.ByteOrder;

import edu.iris.seed.SeedException;

public class B1000 extends AbstractDataBlockette {

	private EncodingFormat encodingFormat;
	private int recordLengthExponent;
	private ByteOrder byteOrder;
	private byte reserved;

	public B1000() {
		super(1000, "Data Only SEED Blockette");
	}

	public EncodingFormat getEncodingFormat() {
		return encodingFormat;
	}

	public void setEncodingFormat(EncodingFormat encodingFormat) {
		this.encodingFormat = encodingFormat;
	}

	public int getRecordLengthExponent() {
		return recordLengthExponent;
	}

	public void setRecordLengthExponent(int recordLengthExponent) {
		this.recordLengthExponent = recordLengthExponent;
	}

	public int getRecordLength() {
		return (int) Math.pow(2, recordLengthExponent);
	}

	public ByteOrder getByteOrder() {
		return byteOrder;
	}

	public void setByteOrder(ByteOrder byteOrder) {
		this.byteOrder = byteOrder;
	}

	public static B1000 build(byte[] bytes, int index, boolean swap) throws SeedException {
		validate(1000, 8, bytes);
		// validate(bytes, 1000, 8);
		B1000 b = new B1000();

		b.setEncodingFormat(EncodingFormat.valueOf(bytes[4]));
		int byteOrder = bytes[5];
		ByteOrder bo = ByteOrder.BIG_ENDIAN;
		if (byteOrder == 0) {
			bo = ByteOrder.LITTLE_ENDIAN;
		}
		b.setByteOrder(bo);
		b.setRecordLengthExponent(bytes[6]);
		return b;
	}

	@Override
	public String toSeedString() throws SeedException {
		StringBuilder builder = new StringBuilder(this.getType());
		builder.append("####");
		builder.append(this.encodingFormat.getValue());
		builder.append(this.byteOrder);
		builder.append(this.recordLengthExponent);
		builder.append(this.reserved);
		return builder.toString();
	}

}
