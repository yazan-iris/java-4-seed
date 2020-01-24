package edu.iris.seed;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A class for reading a Seed inputStream. It will determine the logical record
 * length and return a byte array of the same size. The byte array contains the
 * raw bytes unchanged.
 * 
 * @author Suleiman
 *
 */
public class SeedInputStream implements AutoCloseable {

	public static Pattern headerPattern = Pattern.compile("^\\d{6}[VASTDRQM][\\s\\*]");

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private int recordLength = -1;
	int seq = 0;

	private BufferedInputStream bufferedInputStream;

	public SeedInputStream(InputStream in) {
		this(in, 1024);
	}

	public SeedInputStream(InputStream in, int size) {
		// super(in, size);
		bufferedInputStream = new BufferedInputStream(in, size);
	}

	public byte[] read() throws IOException, SeedException {
		int length = getRecordLength();
		if (length == -1) {
			throw new SeedException("Invalid record length {}", length);
		}
		byte[] bytes = new byte[length];

		int bytesRead = bufferedInputStream.read(bytes);
		if (bytesRead < bytes.length) {
			if (bytesRead <= 0) {
				return null;
			} else {
				// are we at end of file?
				boolean isNL = isNL(bytes);
				if (isNL && bufferedInputStream.read(bytes) == -1) {
					return null;
				} else {
					throw new SeedException("Reading record: Expected {} but received {}:{}", bytes.length, bytesRead,
							new String(bytes));
				}
			}
		}
		return bytes;
	}

	private boolean isNL(byte[] byteArray) {
		if (byteArray.length == 0) {
			return true;
		}
		for (int index = 0; index < byteArray.length; index++) {
			if (Character.isLetterOrDigit(byteArray[index])) {
				return false;
			}
		}
		return true;
	}

	public int getRecordLength() throws IOException {
		if (recordLength > 0) {
			return recordLength;
		}

		bufferedInputStream.mark(8);
		byte[] bytes = new byte[8];
		int bytesRead = bufferedInputStream.read(bytes);
		if (bytesRead == -1) {
			return -1;
		}
		if (bytesRead < bytes.length) {
			throw new IOException("Couldn't read enough bytes to determine length");
		}
		bufferedInputStream.reset();
		if (isBeginingOfNewRecord(bytes)) {
			recordLength = 256;

			for (;;) {
				bufferedInputStream.mark(recordLength + 8);
				bytes = new byte[recordLength + 8];
				bufferedInputStream.read(bytes);
				if (isBeginingOfNewRecord(Arrays.copyOfRange(bytes, bytes.length - 8, bytes.length))) {
					bufferedInputStream.reset();
					return recordLength;
				}
				recordLength = recordLength * 2;
				bufferedInputStream.reset();
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

	@Override
	public void close() throws IOException {
		if (bufferedInputStream != null) {
			try {
				bufferedInputStream.close();
			} catch (IOException e) {
				logger.warn(e.getMessage());
			}
		}
	}

}
