package edu.iris.seed;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.iris.seed.abbreviation.AbbreviationBlockette;
import edu.iris.seed.data.DataBlockette;
import edu.iris.seed.index.B010;
import edu.iris.seed.index.B011;
import edu.iris.seed.index.IndexBlockette;
import edu.iris.seed.io.output.NullOutputStream;
import edu.iris.seed.record.AbbreviationRecord;
import edu.iris.seed.record.StationRecord;
import edu.iris.seed.record.VolumeRecord;
import edu.iris.seed.station.B050;
import edu.iris.seed.station.B051;
import edu.iris.seed.station.StationBlockette;

public class SeedVolume {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private final VolumeRecord indexRecord = new VolumeRecord();
	private final Record<AbbreviationBlockette> abbreviationRecord = new AbbreviationRecord();

	private final List<StationRecord> stationRecords = new ArrayList<>();

	private final Set<DataBlockette> dataRecords = new TreeSet<>();

	private StationRecord currentWorkingStationRecord;

	public Blockette add(Blockette blockette) throws SeedException {
		if (blockette == null) {
			throw new NullPointerException();
		}
		int type = blockette.getType();
		if (logger.isDebugEnabled()) {
			// logger.debug("Adding {}", blockette.toSeedString());
		}
		if (blockette instanceof IndexBlockette) {
			blockette = indexRecord.add((IndexBlockette) blockette);
		} else if (blockette instanceof AbbreviationBlockette) {
			blockette = abbreviationRecord.add((AbbreviationBlockette) blockette);
		} else if (blockette instanceof StationBlockette) {
			if (type == 50) {
				currentWorkingStationRecord = new StationRecord((B050) blockette);
				stationRecords.add(currentWorkingStationRecord);
			} else {
				if (currentWorkingStationRecord == null) {
					throw new SeedException(
							"B050 is required to be present when adding blockette of type {}, but none found!",
							blockette.getType());
				}
				blockette = currentWorkingStationRecord.add((StationBlockette) blockette);
			}
		} else {
			throw new SeedException("Unsupported blockette type {}", blockette.toSeedString());
		}
		return blockette;
	}

	public int getNumberOfRecords() {
		return 2 + this.stationRecords.size() + this.dataRecords.size();
	}

	public Record<IndexBlockette> getIndexRecord() {
		return indexRecord;
	}

	public Record<AbbreviationBlockette> getAbbreviationRecord() {
		return abbreviationRecord;
	}

	public List<StationRecord> getStationRecords() {
		return stationRecords;
	}

	public boolean isEmpty() {
		return getAll().isEmpty();
	}

	public List<Blockette> getAll() {
		List<Blockette> list = new ArrayList<>();
		list.addAll(this.indexRecord.getAll());
		list.addAll(this.abbreviationRecord.getAll());
		for (Record<? extends Blockette> r : this.stationRecords) {
			list.addAll(r.getAll());
		}
		return list;
	}

	public void writeTo(OutputStream outputStream, int recordLength) throws SeedException, IOException {
		buildB011();
		int sequence = 1;
		// At this point, b011 sequence values are only a place holder
		// we need to update the value after we are done writing station blockettes
		if (this.indexRecord != null) {
			sequence = this.indexRecord.writeTo(outputStream, recordLength, sequence) + 1;
		}

		if (this.abbreviationRecord != null) {
			sequence = this.abbreviationRecord.writeTo(outputStream, recordLength, sequence) + 1;
		}

		for (StationRecord r : stationRecords) {
			sequence = r.writeTo(outputStream, recordLength, sequence) + 1;
		}
	}

	private void buildB011() throws SeedException, IOException {

		B010 b010 = (B010) indexRecord.get(10);
		if (b010 == null) {
			b010 = new B010();
			b010.setVolumeTime(BTime.now());
			b010.setOrganization("IRIC DMC");
			b010.setVersion("02.4");
			b010.setLabel("Converted from XML");
			indexRecord.add(b010);
		}
		int recordLength = (int) Math.pow(2, b010.getNthPower());
		B011 b011 = (B011) indexRecord.get(11);

		int sequence = 0;
		for (StationRecord s : stationRecords) {
			B050 b050 = s.getB050();
			b011.add(b050, sequence);
			sequence = s.writeTo(new NullOutputStream(), recordLength, sequence);
		}
		sequence = 1;
		sequence = indexRecord.writeTo(new NullOutputStream(), recordLength, sequence);
		sequence = abbreviationRecord.writeTo(new NullOutputStream(), recordLength, sequence);
		b011.updateSequences(sequence);
	}
}
