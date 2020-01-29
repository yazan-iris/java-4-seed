package edu.iris.seed.data;

import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.SeedByteArrayBuilder;
import edu.iris.seed.SeedException;

public class B2000 extends AbstractDataBlockette<B2000> {

	private class OpaqueDataHeader {
		String recordType;
		String vendorType;
		String modelType;
		String software;
		String firmware;
	}

	private short totalBlocketteLengthInBytes;
	private short offsetToOpaqueData;
	private long recordNumber;
	private byte dataWordOrder;
	private byte opaqueDataFlags;
	private int numberOfOpaqueHeader;
	private String opaqueDataHeaderFields;
	private List<OpaqueDataHeader> opaqueDataHeaders = new ArrayList<>();
	/**
	 * OPAQUE: Opaque Data - bytes of opaque data. Total length of opaque data in
	 * bytes is blockette_length - 15 - length (opaque_data_header_string)
	 */
	private byte[] opaqueData;

	public B2000() {
		super(2000, "Variable Length Opaque Data Blockette");
	}

	@Override
	public byte[] toSeedBytes()throws SeedException{
		SeedByteArrayBuilder builder = new SeedByteArrayBuilder(12).appendU16((short) 2000);
		this.getNextBlocketteByteNumber();
		builder.appendU16(totalBlocketteLengthInBytes).appendU16(offsetToOpaqueData);
		builder.appendFloat(this.recordNumber).appendU8(this.dataWordOrder).appendU8(opaqueDataFlags)
				.appendU8((byte) opaqueDataHeaders.size());

		for (OpaqueDataHeader h : opaqueDataHeaders) {

		}

		builder.append(this.opaqueData);
		return builder.toBytes();
	}

	public BlocketteBuilder<B2000> builder() {
		return new Builder();
	}

	public static class Builder extends BlocketteBuilder<B2000> {

		public Builder() {
			super(2000);
		}

		public static Builder newInstance() {
			return new Builder();
		}

		public B2000 build() throws SeedException { 
			if (bytes == null || bytes.length == 0) {
				throw new IllegalArgumentException("object null|empty");
			}
			int type = ByteUtil.readUnsignedShort(bytes, 0, 2);
			if (2000 != type) {
				throw new SeedException("Invalid blockette type {}", type);
			}
			ByteUtil.readUnsignedShort(bytes, 2, 2);
			B2000 b = new B2000();
			b.setNextBlocketteByteNumber(ByteUtil.readUnsignedShort(bytes, 2, 2));
			return b;
		}
	}

}
