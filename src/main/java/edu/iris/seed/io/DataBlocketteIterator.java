package edu.iris.seed.io;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iris.seed.Blockette;
import edu.iris.seed.BlocketteBuilder;
import edu.iris.seed.BlocketteDefinition;
import edu.iris.seed.DataBlockette;
import edu.iris.seed.SeedBlockette;
import edu.iris.seed.SeedContext;
import edu.iris.seed.SeedDataHeader;
import edu.iris.seed.SeedException;
import edu.iris.seed.data.ByteUtil;
import edu.iris.seed.data.DataSection;

public class DataBlocketteIterator implements Iterator<DataBlockette> {
	private static final Logger logger = LoggerFactory.getLogger(DataBlocketteIterator.class);
	private DataBlockette cachedBlockette;
	private boolean finished;

	private int index;
	private int beginingOfData;
	private byte[] bytes;

	public DataBlocketteIterator(byte[] bytes) throws SeedException {
		this(0, bytes);
	}

	public DataBlocketteIterator(int index, byte[] bytes) throws SeedException {
		if (bytes.length < 7) {
			throw new SeedException("byte array is too short, expected at least 7 but received {}", bytes.length);
		}
		this.index = index;
		this.bytes = bytes;

		SeedDataHeader header = SeedDataHeader.Builder.newInstance().bytes(bytes).build();
		beginingOfData = header.getBeginingOfData();
		cachedBlockette = header;
		index = cachedBlockette.getNextBlocketteByteNumber();
	}

	@Override
	public boolean hasNext() {
		if (cachedBlockette != null) {
			return true;
		} else if (finished) {
			return false;
		} else {
			try {
				while (true) {
					final DataBlockette blockette = read();
					if (blockette == null) {
						finished = true;
						index = 0;
						return false;
					} else {
						cachedBlockette = blockette;
						index = blockette.getNextBlocketteByteNumber();
						return true;
					}
				}
			} catch (final SeedException ioe) {
				throw new IllegalStateException(ioe);
			}
		}
	}

	@Override
	public DataBlockette next() {
		return nextBlockette();
	}

	public DataBlockette nextBlockette() {
		if (!hasNext()) {
			throw new NoSuchElementException("No more blockettes");
		}
		final DataBlockette currentBlockette = cachedBlockette;
		cachedBlockette = null;
		return currentBlockette;
	}

	private DataSection readData() throws SeedException {
		if (this.beginingOfData > this.bytes.length) {
			throw new SeedException(
					"Byte array is too short, expected begining of dat at {} but byte array length is {}",
					this.beginingOfData, bytes.length);
		}
		
		byte[] data = new byte[this.bytes.length - this.beginingOfData];
		System.arraycopy(this.bytes, this.beginingOfData, data, 0, data.length);
		DataSection dataSection = new DataSection();
		dataSection.setData(data);
		finished = true;
		return dataSection;
	}

	private DataBlockette read() throws SeedException {
		if (this.bytes == null) {
			return null;
		}
		if (index == 0) {
			DataSection dataSection = readData();
			return dataSection;
		}

		int recordSize = bytes.length;
		if (bytes == null || index >= recordSize - 1) {
			return null;
		}
		int type = -1;
		int length = -1;

		if (index + 2 > recordSize) {
			return null;
		}
		type = ByteUtil.readUnsignedShort(bytes, index, 2);
		if (!SeedBlockette.controlMap.containsKey(type)) {
			throw new SeedException("Error looking up blockette of type {} at index {}, invalid blockette type.", type,
					index);
		}
		//logger.info("processing {} {}", type, index);
		BlocketteDefinition def = SeedContext.get().get(type);

		length = def.getMinumumLength();
		if (length + index <= bytes.length) {
			byte[] b = new byte[length];
			System.arraycopy(bytes, index, b, 0, length);
			

			BlocketteBuilder<? extends DataBlockette> builder = SeedBlockette.datBlocketteBuilder(type);
			Blockette blockette = builder.fromBytes(b).build();
			//BlocketteBuilder<DataBlockette> builder = SeedBlockette.builder(type);
			DataBlockette db = builder.fromBytes(b).build();
			index = db.getNextBlocketteByteNumber();
			return db;
		} else {
			throw new SeedException("Expected {} bytes but reached end of array", length);
		}
	}

	public byte[] skip(int length) throws SeedException {
		if (this.bytes == null) {
			throw new SeedException("Expected a valid record but was null");
		}
		int available = bytes.length - index;
		int l = length;
		if (length > available) {
			l = available;
		}
		byte[] newBytes = new byte[l];
		System.arraycopy(bytes, index, newBytes, 0, l);
		index += length;
		return newBytes;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Remove unsupported on BlocketteIterator");
	}
}
