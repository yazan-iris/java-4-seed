package edu.iris.seed;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import edu.iris.seed.record.DataRecord;

public class SeedDataOutputStream {
	// OutputStream o;
	BufferedOutputStream o;
	// int sequence;
	int count;
	int index;
	int recordLength;
	int sequence;
	private DataRecord record;
	// private Type typeIdentifier;

	public SeedDataOutputStream(OutputStream o, int length, int sequence, DataRecord record) throws SeedException {
		if (length < 256 || length > 32768) {
			throw new SeedException("Invalid record length {}, expected a length between 256 and 32768", length);
		}

		if ((length & (length - 1)) != 0) {
			throw new SeedException("Invalid record length {}, should be a power of 2", length);
		}
		this.o = new BufferedOutputStream(o);
		this.record = record;
		// this.o = o;
		this.recordLength = length;
		this.sequence = sequence;
		// this.typeIdentifier = typeIdentifier;
		// this.header = (SeedDataHeader) header;
	}

	public SeedDataOutputStream writeData(List<? extends DataBlockette> list) throws IOException {
		if (list == null || list.isEmpty()) {
			return this;
		}
		for (DataBlockette b : list) {
			write(b);
		}
		return this;
	}

	public SeedDataOutputStream write(DataBlockette b) throws IOException {
		if (b != null) {
			try {
				write(b.toSeedBytes());
				o.flush();
			} catch (SeedException e) {
				throw new IOException(e);
			}
		} else {
			// do something
		}

		return this;
	}

	private SeedDataOutputStream write(byte[] bytes) throws IOException {
		int len = bytes.length;
		try {
			if (index == 0) {

				o.write(nextSequene(false));

				index = 48;
			}
			if (index > recordLength - 7) {
				fill();
				o.write(nextSequene(false));
				index = 48;
			}
			for (int i = 0; i < len; i++, index++) {
				if (index >= recordLength) {
					o.write(nextSequene(true));
					index = 48;
				}
				o.write(bytes[i]);
			}
			o.flush();
			return this;
		} catch (SeedException e) {
			throw new IOException(e);
		}
	}

	byte[] nextSequene(boolean continuation) throws SeedException {
		List<DataBlockette> blockettes = this.record.blockettes();

		SeedDataHeader header = (SeedDataHeader) this.record.getHeader();
		SeedDataHeader.Builder builder = SeedDataHeader.Builder.newInstance(sequence++, header.getRecordType(), ' ');
		builder.network(header.getNetwork()).station(header.getStation()).channel(header.getChannel())
				.location(header.getLocation()).byteOrder(header.getByteOrder()).activityFlag(header.getActivityFlag())
				.ioClockFlag(header.getIoClockFlag()).dataQualityFlag(header.getDataQualityFlag())
				.sampleRateFactor(header.getSampleRateFactor()).timeCorrection(header.getTimeCorrection())
				.numberOfFollowingBlockettes(blockettes == null ? 0 : blockettes.size());
		builder.firstDataBlockette(48);

		int beginingOfData = 48;
		for (Blockette b : blockettes) {
			BlocketteDefinition def = SeedContext.get().get(b.getType());
			beginingOfData += def.minumumLength;
		}
		builder.beginingOfData(beginingOfData);
		byte[] data = this.record.getData();
		
		if(data.length+beginingOfData<this.recordLength) {
			
		}

		builder.start(header.getStart());
		builder.numberOfSamples(header.getNumberOfSamples());

		return builder.build().toSeedBytes();
	}

	/**
	 * Will fill record with empty space and return this record ending sequence
	 * 
	 * @return
	 * @throws IOException
	 */
	public int flush() throws IOException {
		fill();
		o.flush();
		return sequence;
	}

	private void fill() throws IOException {
		for (; index < recordLength; index++) {
			o.write((byte) 32);
		}
		index = 0;
	}
}
