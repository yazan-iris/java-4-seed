package edu.iris.dmc.seed.io;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import edu.iris.dmc.seed.Record;
import edu.iris.dmc.seed.RecordFactory;
import edu.iris.dmc.seed.SeedException;

public class RecordInputStream extends BufferedInputStream {

	public static Pattern headerPattern = Pattern.compile("^\\d{6}[VASTDRQM][\\s\\*]");

	private Logger LOGGER = Logger.getLogger("SeedInputStream");

	private int recordLength = -1;
	int seq = 0;

	public RecordInputStream(InputStream in) {
		this(in, 1024);
	}

	public RecordInputStream(InputStream in, int size) {
		super(in, size);
	}

	public Record next() throws IOException, SeedException {
		byte[] bytes = new byte[getRecordLength()];

		int bytesRead = read(bytes);
		if (bytesRead < bytes.length) {
			LOGGER.info("Expected 8 but read only " + bytesRead + " bytes");
			// throw new SeedException("Reading record: Expected "+bytes.length+" but
			// received "+bytesRead);
			if (bytesRead < 0) {
				return null;
			}else {
				throw new SeedException("Reading record: Expected "+bytes.length+" but received "+bytesRead);
			}
		}

		return RecordFactory.create(bytes);
	}

	public int getRecordLength() throws IOException {
		if (recordLength > 0) {
			return recordLength;
		}

		mark(8);
		byte[] bytes = new byte[8];
		int bytesRead = read(bytes);
		if (bytesRead < bytes.length) {
			throw new IOException("Couldn't read enough bytes to determine length");
		}
		reset();
		if (isBeginingOfNewRecord(bytes)) {
			recordLength = 256;

			for (;;) {
				mark(recordLength + 8);
				bytes = new byte[recordLength + 8];
				read(bytes);
				if (isBeginingOfNewRecord(Arrays.copyOfRange(bytes, bytes.length - 8, bytes.length))) {
					reset();
					return recordLength;
				}
				recordLength = recordLength * 2;
				reset();
			}
		} else {
			throw new IOException("Error calculation record length!");
		}
	}

	private boolean isBeginingOfNewRecord(byte[] bytes) {
		if (headerPattern.matcher(new String(bytes)).find()) {
			return true;
		}
		return false;
	}
}
