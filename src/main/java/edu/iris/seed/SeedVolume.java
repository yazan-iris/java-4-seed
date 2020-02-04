package edu.iris.seed;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iris.seed.Identifier.B010;
import edu.iris.seed.Identifier.B011;
import edu.iris.seed.Identifier.IdentifierBlockette;
import edu.iris.seed.abbreviation.AbbreviationBlockette;
import edu.iris.seed.data.B1000;
import edu.iris.seed.data.DataSection;
import edu.iris.seed.io.output.NullOutputStream;
import edu.iris.seed.record.AbbreviationRecord;
import edu.iris.seed.record.DataRecord;
import edu.iris.seed.record.IdentifierRecord;
import edu.iris.seed.record.StationRecord;
import edu.iris.seed.station.B050;
import edu.iris.seed.station.StationBlockette;

public class SeedVolume {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private IdentifierRecord identifierRecord = new IdentifierRecord();
	private AbbreviationRecord abbreviationRecord = new AbbreviationRecord();

	private List<StationRecord> stationRecords = new ArrayList<>();

	private List<DataRecord> dataRecords = new ArrayList<>();

	private StationRecord stationRecord;
	private DataRecord dataRecord;

	public AbbreviationRecord getAbbreviationRecord() {
		return this.abbreviationRecord;
	}

	public IdentifierRecord getIdentifierRecord() {
		return identifierRecord;
	}

	public List<StationRecord> getStationRecords() {
		return this.stationRecords;
	}
	
	public List<DataRecord> getDataRecords() {
		return this.dataRecords;
	}

	public List<? extends Blockette> blockettes() {
		List<Blockette> list = new ArrayList<>();

		list.addAll(this.identifierRecord.blockettes());
		list.addAll(this.abbreviationRecord.blockettes());

		for (Record<StationBlockette> r : stationRecords) {
			list.addAll(r.blockettes());
		}
		for (Record<DataBlockette> r : dataRecords) {
			list.addAll(r.blockettes());
		}
		return list;
	}

	public List<Record<? extends Blockette>> records() {
		List<Record<? extends Blockette>> list = new ArrayList<>();

		if (identifierRecord != null) {
			list.add(this.identifierRecord);
		}

		if (abbreviationRecord != null) {
			list.add(this.abbreviationRecord);
		}

		if (stationRecords != null && !stationRecords.isEmpty()) {
			list.addAll(stationRecords);
		}
		if (dataRecords != null) {
			list.addAll(dataRecords);
		}
		return list;
	}

	public Blockette add(Blockette b) throws SeedException {
		int type = b.getType();

		if (b instanceof SeedDataHeader) {
			SeedDataHeader header = (SeedDataHeader) b;
			dataRecord = DataRecord.Builder.newInstance().header(header).build();
			dataRecords.add(dataRecord);
		} else if (b instanceof DataSection) {
			((DataRecord) dataRecord).setData(((DataSection) b).getData());
		} else {
			if (type < 30) {
				b = identifierRecord.add((IdentifierBlockette) b);
			} else if (type < 50) {
				b = abbreviationRecord.add((AbbreviationBlockette) b);
			} else if (type < 70) {
				if (type == 50) {
					stationRecord = new StationRecord();
					stationRecords.add(stationRecord);
				}
				b = stationRecord.add((StationBlockette) b);
			} else {

				dataRecord.add((DataBlockette) b);
			}
		}
		return b;
	}

	public boolean addAll(Collection<? extends Blockette> c) throws SeedException {
		for (Blockette b : c) {
			add(b);
		}
		return true;
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public int size() {
		int size = 0;
		for (Record<? extends Blockette> r : this.stationRecords) {
			size += r.size();
		}
		for (Record<? extends Blockette> r : this.dataRecords) {
			size += r.size();
		}
		return size + this.identifierRecord.size() + this.abbreviationRecord.size();
	}

	public void clear() {
		this.identifierRecord.clear();
		this.abbreviationRecord.clear();
		for (Record<? extends Blockette> r : stationRecords) {
			r.clear();
		}

		for (Record<? extends Blockette> r : dataRecords) {
			r.clear();
		}
	}

	public void writeTo(OutputStream outputStream, int recordLength) throws SeedException, IOException {
		buildB011();
		int sequence = 1;
		// At this point, b011 sequence values are only a place holder
		// we need to update the value after we are done writing station blockettes

		sequence = this.identifierRecord.writeTo(outputStream, recordLength, sequence) + 1;
		sequence = this.abbreviationRecord.writeTo(outputStream, recordLength, sequence) + 1;
		for (Record<? extends Blockette> r : this.stationRecords) {
			sequence = r.writeTo(outputStream, recordLength, sequence) + 1;
		}

		for (DataRecord r : this.dataRecords) {
			SeedDataHeader header = (SeedDataHeader) r.getHeader();
			ByteOrder byteOrder = header.getByteOrder();

			B1000 b1000 = (B1000) r.get(1000);
			
			b1000.getByteOrder();
			
			
			b1000.getEncodingFormat();
			
		}
		/*
		 * if (this.indexRecord != null) { sequence =
		 * this.indexRecord.writeTo(outputStream, recordLength, sequence) + 1; }
		 * 
		 * if (this.abbreviationRecord != null) { sequence =
		 * this.abbreviationRecord.writeTo(outputStream, recordLength, sequence) + 1; }
		 * 
		 * for (StationRecord r : stationRecords) { sequence = r.writeTo(outputStream,
		 * recordLength, sequence) + 1; }
		 */
	}

	private void buildB011() throws SeedException, IOException {

		B010 b010 = (B010) identifierRecord.get(10);
		if (b010 == null) {
			b010 = new B010();
			b010.setVolumeTime(BTime.now());
			b010.setOrganization("IRIC DMC");
			b010.setVersion("02.4");
			b010.setLabel("Converted from XML");
			identifierRecord.add(b010);
		}
		int recordLength = (int) Math.pow(2, b010.getNthPower());
		B011 b011 = (B011) identifierRecord.get(11);

		int sequence = 1;

		for (Record<? extends Blockette> r : stationRecords) {
			Blockette b = r.blockettes().get(0);
			if (b.getType() != 50) {
				throw new SeedException("Expected blockette B050 but received B({})", b.getType());
			}
			b011.add((B050) b, sequence);
			sequence = r.writeTo(new NullOutputStream(), recordLength, sequence);
		}

		sequence = 1;
		sequence = identifierRecord.writeTo(new NullOutputStream(), recordLength, sequence);
		sequence = this.abbreviationRecord.writeTo(new NullOutputStream(), recordLength, sequence);
		b011.updateSequences(sequence);
	}

}
