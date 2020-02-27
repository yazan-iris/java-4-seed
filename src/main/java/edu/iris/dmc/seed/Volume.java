package edu.iris.dmc.seed;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.iris.dmc.seed.control.dictionary.AbstractDictionaryBlockette;
import edu.iris.dmc.seed.control.dictionary.DictionaryBlockette;
import edu.iris.dmc.seed.control.index.B010;
import edu.iris.dmc.seed.control.index.B011;
import edu.iris.dmc.seed.control.index.B012;
import edu.iris.dmc.seed.control.index.IndexBlockette;
import edu.iris.dmc.seed.control.station.B050;
import edu.iris.dmc.seed.control.station.B051;
import edu.iris.dmc.seed.control.station.B052;
import edu.iris.dmc.seed.control.station.B059;
import edu.iris.dmc.seed.control.station.OverFlowBlockette;
import edu.iris.dmc.seed.control.station.ResponseBlockette;
import edu.iris.dmc.seed.control.station.SeedResponseStage;
import edu.iris.dmc.seed.control.station.StationBlockette;
import edu.iris.dmc.seed.headers.Control;

/**
 * We need to examine this class and find some better representation.
 * 
 * @author Suleiman
 *
 */
public class Volume {

	private int id = 1000;

	private Blockette b005;
	private Blockette b008;
	private B010 b010;
	private B011 b011;
	private B012 b012;
	private B050 b050;
	private B052 b052;

	private final DictionaryIndex dictionary = new DictionaryIndex();
	private final Control control = new Control();

	private Map<Integer, Blockette> ids = new HashMap<>();

	private void index(Blockette blockette) {
		this.ids.put(blockette.getId(), blockette);
	}

	private Map<Integer, Record> volumeRecords = new TreeMap<>();
	private Map<Integer, Record> dictionaryRecords = new TreeMap<>();
	private Map<Integer, Record> stationRecords = new TreeMap<>();

	public void build() throws SeedException {
		// this.records = new TreeMap<>();// make sure start clean
		this.volumeRecords = new TreeMap<>();
		int sequence = 1;
		int recordSize = 0;
		if (this.b010 == null) {
			this.b010 = new B010();
		}

		recordSize = BigInteger.valueOf(2).pow(b010.getNthPower()).intValue();
		Record record = RecordFactory.create(recordSize, sequence, 'V', false);
		this.volumeRecords.put(sequence, record);
		record = addVolume(record, b010);
		// If you choose to build we create b011
		// This is actually where the b011 is created
		B011 b011 = new B011();
		// find the number of records this blockette requires and update the sequence

		for (B050 b050 : this.control.getB050s()) {

			// place as a holder for now
			b011.add(b050, sequence);
		}
		record = addVolume(record, b011);

		sequence = record.getSequence() + 1;
		record = RecordFactory.create(recordSize, sequence, 'A', false);
		this.dictionaryRecords.put(sequence, record);
		for (Blockette b : this.dictionary.getAll()) {
			record = addDictionary(record, b);
		}
		
		// Update the b011 for the sequence of the first station code epoch found in xml,  
		// b011 ignores epochs so first epoch of unique station codes must be used in B011 to make it work with Rdseed
        int ind = 1;
        String  prevStaCode ="";
        String prevStaConcat ="";
		for (B050 b050 : this.control.getB050s()) {
			record = addStation(record, b050);
			if(b050.getStationCode().equals(prevStaCode) == false ) {
				if(prevStaConcat.toLowerCase().contains(b050.getStationCode().toLowerCase()) == false) {
			        b011.update(b050, record.getSequence()); 
				}else {}
			}
		    prevStaCode = b050.getStationCode();
		    prevStaConcat = prevStaConcat.concat(b050.getStationCode()); 
		 
			for (B051 b051 : b050.getB051s()) {
				record = addStation(record, b051);
			}
			for (B052 b052 : b050.getB052s()) {
				record = addStation(record, b052);
				for (B059 b059 : b052.getB059s()) {
					record = addStation(record, b059);
				}

				for (SeedResponseStage responseStage : b052.getResponseStages()) {
					for (ResponseBlockette responseBlockette : responseStage.getBlockettes()) {
						if (responseBlockette instanceof OverFlowBlockette) {
							/**
							 * This blockette is the only blockette that might overflow the maximum allowed
							 * value of 9,999 characters. If there are more coefficients than fit in one
							 * record, list as many as will fit in the first occurrence of this blockette
							 * (the counts of Number of numerators and Number of denominators would then be
							 * set to the number included, not the total number). In the next record, put
							 * the remaining number. Be sure to write and read these blockettes in sequence,
							 * and be sure that the first few fields of both records are identical. Reading
							 * (and writing) programs have to be able to work with both blockettes as one
							 * after reading (or before writing). In July 2007, the FDSN adopted a
							 * convention that requires the coefficients to be listed in forward time order.
							 * As a reference, minimum-p
							 */
							OverFlowBlockette ob = (OverFlowBlockette) responseBlockette;

							if (ob.isOverFlown()) {
								for (Blockette b : ob.split()) {
									record = addStation(record, b);
								}
							} else {
								record = addStation(record, responseBlockette);
							}
						} else {
							record = addStation(record, responseBlockette);
						}
					}
				}
			}
		}
		this.volumeRecords = new TreeMap<>();
		record = RecordFactory.create(recordSize, 1, 'V', false);
		this.volumeRecords.put(1, record);
		record = addVolume(record, b010);
		addVolume(record, b011);
	}

	private Record addVolume(Record record, Blockette b) throws SeedException {
		int recordLength = record.size();
		int sequence = record.getSequence();
		if (b instanceof B050) {
			record = RecordFactory.create(recordLength, record.getSequence() + 1, 'S', false);
			this.volumeRecords.put(record.getSequence(), record);
		}
		byte[] bytes = b.toSeedString().getBytes(StandardCharsets.US_ASCII);
		while (true) {
			bytes = record.add(bytes);
			if (bytes == null || bytes.length == 0) {
				break;
			} else {
				sequence++;
				record = RecordFactory.create(recordLength, sequence, record.getType(), true);
				this.volumeRecords.put(sequence, record);
			}
		}
		return record;
	}

	private Record addDictionary(Record record, Blockette b) throws SeedException {
		int recordLength = record.size();
		int sequence = record.getSequence();
		if (b instanceof B050) {
			record = RecordFactory.create(recordLength, record.getSequence() + 1, 'S', false);
			this.dictionaryRecords.put(record.getSequence(), record);
		}
		byte[] bytes = b.toSeedString().getBytes(StandardCharsets.US_ASCII);
		while (true) {
			bytes = record.add(bytes);
			if (bytes == null || bytes.length == 0) {
				break;
			} else {
				sequence++;
				record = RecordFactory.create(recordLength, sequence, record.getType(), true);
				this.dictionaryRecords.put(sequence, record);
			}
		}
		return record;
	}

	private Record addStation(Record record, Blockette b) throws SeedException {
		int recordLength = record.size();
		int sequence = record.getSequence();
		if (b instanceof B050) {
			record = RecordFactory.create(recordLength, record.getSequence() + 1, 'S', false);
			this.stationRecords.put(record.getSequence(), record);
		}
		byte[] bytes = b.toSeedString().getBytes(StandardCharsets.US_ASCII);
		while (true) {
			bytes = record.add(bytes);
			if (bytes == null || bytes.length == 0) {
				break;
			} else {
				sequence++;
				record = RecordFactory.create(recordLength, sequence, record.getType(), true);
				this.stationRecords.put(sequence, record);
			}
		}
		return record;
	}

	public Blockette add(Blockette blockette) throws SeedException {

		if (blockette == null) {
			throw new IllegalArgumentException("Cannot add a null blockette to this volume");
		}
		if (!this.isValidType(blockette)) {
			throw new IllegalArgumentException("Invalid blockette type [" + blockette.getType() + "]");
		}
		blockette.setId(id++);
		blockette.setVolume(this);

		if (blockette.getSize() > blockette.getLength() && !(blockette instanceof OverFlowBlockette)) {
			throw new SeedException("Blockette " + blockette.getType() + " is too long, expected maximum 9999 but was "
					+ blockette.getLength());
		}

		index(blockette);
		if (blockette instanceof IndexBlockette) {
			if (5 == blockette.getType()) {
				this.b005 = blockette;
			} else if (8 == blockette.getType()) {
				this.b008 = blockette;
			} else if (10 == blockette.getType()) {
				this.b010 = (B010) blockette;
			} else if (11 == blockette.getType()) {
				this.b011 = (B011) blockette;
			} else if (12 == blockette.getType()) {
				this.b012 = (B012) blockette;
			}
			return blockette;
		} else if (blockette instanceof StationBlockette) {
			if (50 == blockette.getType()) {
				this.b050 = (B050) blockette;
				this.control.put(b050);
			} else if (51 == blockette.getType()) {
				this.b050.add((B051) blockette);
			} else if (52 == blockette.getType()) {
				if (this.b050 == null) {
					throw new SeedException("Trying to add b052 when no b050 is present.");
				}
				this.b052 = (B052) blockette;
				b050.add(this.b052);
			} else if (59 == blockette.getType()) {
				if (this.b052 == null) {
					throw new SeedException("Trying to add b059 when no b052 is present.");
				}
				this.b052.add((B059) blockette);
			}
			return blockette;
		} else if (blockette instanceof DictionaryBlockette) {
			return this.dictionary.put((AbstractDictionaryBlockette) blockette);
		} else if (blockette instanceof ResponseBlockette) {
			if (this.b052 == null) {
				throw new SeedException("Expected b052 but was null.");
			}
			this.b052.add((ResponseBlockette) blockette);
			return blockette;
		} else {
			throw new SeedException("Could n't add blockette " + blockette.getType());
		}
	}

	public int getNumberOfStations() {
		return this.control.getB050s().size();
	}

	public Blockette get(int id) {
		return this.ids.get(id);
	}

	public Record getRecord(int sequence) {
		if (this.volumeRecords.size() > sequence) {
			return this.volumeRecords.get(sequence);
		}
		if (this.dictionaryRecords.size() > sequence) {
			return this.dictionaryRecords.get(sequence);
		}
		if (this.stationRecords.size() > sequence) {
			return this.stationRecords.get(sequence);
		}
		throw new IndexOutOfBoundsException();
	}

	public List<Record> getRecords() {
		List<Record> list = new ArrayList<>(volumeRecords.values());
		list.addAll(dictionaryRecords.values());
		list.addAll(stationRecords.values());
		return list;
	}

	/**
	 * a list of all blockettes in this volume
	 * 
	 * @return a list of all blockettes ordered as they were inserted
	 */
	public List<Blockette> getAll() {
		List<Blockette> list = new ArrayList<>();
		if (b005 != null) {
			list.add(this.b005);
		}

		if (b008 != null) {
			list.add(this.b008);
		}

		if (b010 != null) {
			list.add(this.b010);
		}

		if (b011 != null) {
			list.add(b011);
		}

		if (b012 != null) {
			list.add(this.b012);
		}

		list.addAll(this.dictionary.getAll());
		list.addAll(this.control.getAll());
		return list;
	}

	public List<Blockette> find(String network, String station, String channel, String location) {
		List<Blockette> list = new ArrayList<>();
		for (B050 b : this.control.getB050s()) {
			if (network != null && !b.getNetworkCode().trim().equals(network)) {
				continue;
			}
			if (station != null && !b.getStationCode().trim().equals(station)) {
				continue;
			}

			if (channel != null) {
				for (B052 b052 : b.getB052s()) {
					if (b052.getChannelCode().trim().equals(channel)) {
						if (location == null) {
							list.add(b052);
						} else {
							if (b052.getLocationCode().equals(location)) {
								list.add(b052);
							}
						}
					}
				}
			}
		}
		return list;
	}

	public B010 getB010() {
		return this.b010;
	}

	public B011 getB011() {
		return this.b011;
	}

	public B012 getB012() {
		return this.b012;
	}

	public Blockette getById(int id) {
		return this.ids.get(id);
	}

	public DictionaryIndex getDictionary() {
		return this.dictionary;
	}

	public Blockette getDictionaryBlockette(int type, int lookupCode) {
		return this.dictionary.get(type, lookupCode);
	}

	public Blockette getResponseDictionaryBlockette(int lookupCode) {
		return this.dictionary.getResponse(lookupCode);
	}

	public List<Blockette> getIndexBlockettes() {
		Arrays.asList(this.b005, this.b008, this.b010, this.b012);
		List<Blockette> l = new ArrayList<>();
		if (b005 != null) {
			l.add(b005);
		}
		if (b008 != null) {
			l.add(b008);
		}
		if (b010 != null) {
			l.add(b010);
		}
		if (b011 != null) {
			l.add(b011);
		}
		if (b012 != null) {
			l.add(b012);
		}
		return l;
	}

	public List<Blockette> getDictionaryBlockettes() {
		return this.dictionary.getAll();
	}

	public List<B050> getB050s() {
		return this.control.getB050s();
	}

	public List<Blockette> getControlBlockettes() {
		return this.control.getAll();
	}

	public boolean isEmpty() {
		return this.dictionary.isEmpty() && this.control.isEmpty() && b005 == null && b008 == null && b010 == null
				&& b011 != null && b012 == null;
	}

	private boolean isValidType(int type) {
		return SEED.TYPES.contains(type);
	}

	private boolean isValidType(Blockette blockette) {
		return isValidType(blockette.getType());
	}

	public int size() {
		return this.dictionary.size() + this.control.size();
	}

	class Buffer {
		int size;
		int remainder;

		Buffer(int size) {
			this.size = size;
			this.remainder = size - 8;
		}

		int add(int length) {
			if (this.remainder == 0) {
				this.remainder = size - 8;
			}
			if (length > remainder) {
				int re = length - remainder;
				remainder = 0;
				return re;
			} else {
				remainder -= length;
				return 0;
			}
		}
	}
}
