package edu.iris.dmc.seed;

import java.util.logging.Logger;

import edu.iris.dmc.seed.record.DictionaryRecord;
import edu.iris.dmc.seed.record.EmptyRecord;
import edu.iris.dmc.seed.record.StationRecord;
import edu.iris.dmc.seed.record.VolumeRecord;

public class RecordFactory {
	private static final Logger LOGGER = Logger.getLogger("RecordFactory");

	private RecordFactory() {
	}

	public static Record create(byte[] bytes) throws SeedException {
		char type = (char) bytes[6];
		int sequence = Integer.parseInt(new String(bytes, 0, 6));
		boolean continuation = ((char) bytes[7]) == '*';
		Record record = create(bytes.length, sequence, type, continuation);
		record.setBytes(bytes);
		return record;
	}

	/*
	 * public static Record create(int sequence, char type, boolean continuation)
	 * throws SeedException { return create(0, sequence, type, continuation); }
	 */

	public static Record create(int size, int sequence, char type, boolean continuation) throws SeedException {
		Record record = null;
		if (type == 'V') {
			record = new VolumeRecord(size, sequence, continuation);
		} else if (type == 'A') {
			record = new StationRecord(size, sequence, continuation);
		} else if (type == 'S') {
			record = new DictionaryRecord(size, sequence, continuation);
		} else if (type == 'T' || type == 'D' || type == 'R' || type == 'M' || type == 'Q') {
			LOGGER.info("Creating record of type " + type + " sequence: " + sequence);
			throw new SeedException("Unsupported Record type " + type);
		} else if (type == ' ') {
			record = new EmptyRecord(sequence);
		} else {
			throw new SeedException("Invalid record type [" + type + "]");
		}
		return record;
	}

	public static final int trimRight(byte[] bytes, int start) {
		if (bytes == null) {
			return start;
		}

		int end = bytes.length - 1;
		while (true) {
			if ((bytes[end] == 32 || bytes[end] == 0) && end > start) {
				end--;
			} else {
				return end;
			}
		}
	}
}
