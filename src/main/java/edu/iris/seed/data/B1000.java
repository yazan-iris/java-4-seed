package edu.iris.seed.data;

import java.nio.ByteOrder;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedByteArrayBuilder;
import edu.iris.seed.SeedException;

public class B1000 extends AbstractDataBlockette<B1000> {

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
	public byte[] toSeedBytes() throws SeedException {
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(12).appendU16((short) 1000);
		this.getNextBlocketteByteNumber();
		builder.append((byte) this.encodingFormat.getValue());
		builder.append((byte) (this.byteOrder == ByteOrder.LITTLE_ENDIAN ? 0 : 1));
		builder.append((byte) this.recordLengthExponent);
		builder.append(this.reserved);

		return builder.toBytes();
	}

	public BlocketteBuilder<B1000> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B1000> {

		private EncodingFormat encodingFormat;
		private int recordLengthExponent;
		private ByteOrder byteOrder;
		private byte reserved;

		public Builder() {
			super(1000);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		private B1000 buildFromValues() {
			B1000 b = new B1000();

			if (encodingFormat == null) {

			}
			b.encodingFormat = encodingFormat;

			if (recordLengthExponent >= 8 && recordLengthExponent <= 256) {
				b.recordLengthExponent = recordLengthExponent;
			} else {
				b.recordLengthExponent = 8;
			}

			if (byteOrder == null) {
				b.byteOrder = ByteOrder.BIG_ENDIAN;
			} else {
				b.byteOrder = byteOrder;
			}
			return b;
		}

		private B1000 buildFromBytes() throws SeedException {
			B1000 b = new B1000();
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int type = ByteUtil.readUnsignedShort(bytes, 0, 2);
			if (1000 != type) {
				throw new SeedException("Invalid blockette type {}", type);
			}
			ByteUtil.readUnsignedShort(bytes, 2, 2);
			b.setNextBlocketteByteNumber(ByteUtil.readUnsignedShort(bytes, 2, 2));

			b.setEncodingFormat(EncodingFormat.valueOf(bytes[4]));
			int byteOrder = bytes[5];
			ByteOrder bo = ByteOrder.BIG_ENDIAN;
			if (byteOrder == 0) {
				bo = ByteOrder.LITTLE_ENDIAN;
			}
			b.setByteOrder(bo);
			b.setRecordLengthExponent(bytes[6]);
			// b.setReserved(bytes[7]);
			return b;
		}

		public Builder encodingFormat(EncodingFormat encodingFormat) {
			this.encodingFormat = encodingFormat;
			return this;
		}

		public Builder recordLengthExponent(int recordLengthExponent) {
			this.recordLengthExponent = recordLengthExponent;
			return this;
		}

		public Builder encodingFormat(ByteOrder byteOrder) {
			this.byteOrder = byteOrder;
			return this;
		}

		public B1000 build() throws SeedException {
			if (bytes != null) {
				return buildFromBytes();
			} else {
				return buildFromValues();
			}

		}
	}
}
