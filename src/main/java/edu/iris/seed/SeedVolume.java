package edu.iris.seed;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
import edu.iris.seed.station.StationBlockette;

public class SeedVolume {

	private final VolumeRecord indexRecord = new VolumeRecord();
	private final Record<AbbreviationBlockette> abbreviationRecord = new AbbreviationRecord();

	private final List<StationRecord> stationRecords = new ArrayList<>();

	private final Set<DataBlockette> dataRecords = new TreeSet<>();

	private int numberOfRecords;

	private B050 b050;

	public AbbreviationBlockette add(AbbreviationBlockette abbreviation) throws SeedException {
		abbreviationRecord.add(abbreviation);
		return abbreviation;
	}

	public void add(IndexBlockette index) throws SeedException {
		indexRecord.add(index);
	}

	public void add(B050 blockette) throws SeedException {
		StationRecord record = new StationRecord();
		record.add(blockette);
		stationRecords.add(record);
		b050 = blockette;
		// this.indexRecord.getB011().add(b050, 0);
		numberOfRecords++;
	}

	int cnt=0;
	public void add(StationBlockette blockette) throws SeedException {
		int type = blockette.getType();
		if (type == 50) {
			add((B050) blockette);
		} else {
			if (b050 == null) {
				throw new SeedException(
						"B050 is required to be present when adding blockette of type {}, but none found!",
						blockette.getType());
			}
			/*if (type == 60) {
				// resolve
				B060 b060 = (B060) blockette;
				List<Stage> stages = b060.getStages();
				if (stages != null && !stages.isEmpty()) {
					for (Stage stage : stages) {
						stage.getSequence();
						List<Integer> list = stage.getResponses();
						for (int i : list) {
							AbbreviationBlockette b = this.abbreviationRecord.get(i);
							if (b.getType() < 40 || b.getType() > 49) {
								throw new SeedException("Expected a blockette of type [41-49] but was {}", b.getType());
							}
							b050.add((ResponseBlockette) b);
							numberOfBlockettes++;
						}
					}
				}
			} else {*/
				b050.add(blockette);
			//}
		}
	}

	public void add(Blockette blockette) throws SeedException {
		if (blockette == null) {
			throw new NullPointerException();
		}
		if (blockette instanceof IndexBlockette) {
			add((IndexBlockette) blockette);
		} else if (blockette instanceof AbbreviationBlockette) {
			add((AbbreviationBlockette) blockette);
		} else if (blockette instanceof B050) {
			add((B050) blockette);
		} else if (blockette instanceof StationBlockette) {
			add((StationBlockette) blockette);
		} else {
			throw new SeedException("Unsupported blockette type {}", blockette.toSeedString());
		}

	}

	public int getNumberOfBlockettes() {
		return getAll().size();
	}

	public int getNumberOfRecords() {
		return numberOfRecords + 2;
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
		return this.getAll().isEmpty();
	}

	public List<Blockette> getAll() {
		List<Blockette> list = new ArrayList<>();
		list.addAll(this.indexRecord.getAll());
		list.addAll(this.abbreviationRecord.getAll());
		for (StationRecord r : this.stationRecords) {
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
