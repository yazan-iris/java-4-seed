package edu.iris.seed.record;

import java.util.logging.Logger;

import edu.iris.seed.Blockette;
import edu.iris.seed.Record;
import edu.iris.seed.SeedDataHeader;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedHeader.Type;

public class RecordFactory {
	private static final Logger LOGGER = Logger.getLogger("RecordFactory");

	private RecordFactory() {
	}

	public static Record<? extends Blockette> create(byte[] bytes,boolean relax) throws SeedException {
		// make sure we have at least 7 bytes
		if (bytes.length < 7) {
			throw new SeedException("byte array is too short, expected at least 7 but received {}", bytes.length);
		}
		char type = (char) bytes[6];
		switch (type) {
		case 'V':
			return IdentifierRecord.Builder.newInstance().fromBytes(bytes).build(false);
		case 'A':
			return AbbreviationRecord.Builder.newInstance().fromBytes(bytes).build(relax);
		case 'S':
			return StationRecord.Builder.newInstance().fromBytes(bytes).build(relax);
		case 'T':
			return TimeSpanRecord.Builder.newInstance().build(bytes);
		case 'D':
		case 'R':
		case 'M':
		case 'Q':
			return DataRecord.Builder.newInstance().fromBytes(bytes).build(false);
		case ' ':
			return new EmptyRecord();
		default:
			throw new SeedException("Invalid record type {}", type);
		}
	}

	public static Record<? extends Blockette> create(int sequence, char type, boolean continuation)
			throws SeedException {
		Record<? extends Blockette> record = null;
		if (type == 'V') {
			record = new IdentifierRecord(sequence, continuation);
		} else if (type == 'A') {
			record = new StationRecord(sequence, continuation);
		} else if (type == 'S') {
			record = new AbbreviationRecord(sequence, continuation);
		} else if (type == 'T' || type == 'D' || type == 'R' || type == 'Q' || type == 'M') {
			LOGGER.info("Creating record of type " + type + " sequence: " + sequence);
			throw new SeedException("Unsupported Record type " + type);
		} else if (type == 'D' || type == 'R' || type == 'M' || type == 'Q') {
			record = new DataRecord(SeedDataHeader.Builder.newInstance(sequence, Type.from(type), (byte)0).build(false));
		} else if (type == ' ') {
			record = new EmptyRecord(sequence);
		} else {
			throw new SeedException("Invalid record type [" + type + "]");
		}
		return record;
	}

}
