package edu.iris.seed.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import edu.iris.seed.Blockette;
import edu.iris.seed.Record;
import edu.iris.seed.SeedException;
import edu.iris.seed.SeedVolume;
import edu.iris.seed.io.output.StringBuilderOutputStream;
import edu.iris.seed.record.Header.Type;

public class SeedFileUtils {

	
	public static String volumeRecordToString(final File file) throws SeedException, IOException {
		try (SeedRecordIterator it = recordIterator(file)) {
			StringBuilderOutputStream outputStream = new StringBuilderOutputStream();
			while (it.hasNext()) {
				Record<? extends Blockette> record = it.next();
				if (record.getHeader().getType() == Type.V) {
					record.writeTo(outputStream, 4096, 1);
					record.getBytes();
				}
			}
			return outputStream.toString();
		}
	}

	public static String abbreviationRecordToString(final File file) throws SeedException, IOException {
		try (SeedRecordIterator it = recordIterator(file)) {
			StringBuilderOutputStream outputStream = new StringBuilderOutputStream();
			while (it.hasNext()) {
				Record<? extends Blockette> record = it.next();
				if (record.getHeader().getType() == Type.A) {
					record.writeTo(outputStream, 4096, 1);
					record.getBytes();
				}
			}
			return outputStream.toString();
		}
	}

	public static String readSeedFileToString(final Path path) throws SeedException, IOException {
		return readSeedFileToString(path.toFile());
	}

	public static String readSeedFileToString(final File file) throws SeedException, IOException {
		validateFile(file);
		try (InputStream inputStream = new FileInputStream(file)) {
			return readSeedFileToString(inputStream);
		}
	}

	public static String readSeedFileToString(final InputStream inputStream) throws SeedException, IOException {
		return readSeedFileToString(inputStream, 4096);
	}

	public static String readSeedFileToString(final InputStream inputStream, int recordLength)
			throws SeedException, IOException {
		SeedVolume v = SeedIOUtils.toSeedVolume(inputStream);
		StringBuilderOutputStream outputStream = new StringBuilderOutputStream();
		v.writeTo(outputStream, recordLength);
		return outputStream.toString();
	}

	public static SeedVolume toSeedVolume(final Path path) throws SeedException, IOException {
		return toSeedVolume(path.toFile());
	}

	public static SeedVolume toSeedVolume(final File file) throws SeedException, IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (file.canRead() == false) {
				throw new IOException("File '" + file + "' cannot be read");
			}
		} else {
			throw new FileNotFoundException("File '" + file + "' does not exist");
		}

		try (InputStream inputStream = new FileInputStream(file)) {
			return SeedIOUtils.toSeedVolume(inputStream);
		}
	}

	public static void validateFile(final File file) throws IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (file.canRead() == false) {
				throw new IOException("File '" + file + "' cannot be read");
			}
		} else {
			throw new FileNotFoundException("File '" + file + "' does not exist");
		}
	}

	public static SeedRecordIterator recordIterator(final File file) throws SeedException, IOException {
		validateFile(file);
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);
			return SeedIOUtils.recordIterator(inputStream);
		} catch (final IOException | RuntimeException ex) {
			if (inputStream != null) {
				inputStream.close();
			}
			throw ex;
		}
	}

	public static SeedBlocketteIterator blocketteIterator(final File file) throws SeedException, IOException {
		validateFile(file);
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);
			return SeedIOUtils.blocketteIterator(inputStream);
		} catch (final IOException | RuntimeException ex) {
			if (inputStream != null) {
				inputStream.close();
			}
			throw ex;
		}
	}

	public static List<Record<? extends Blockette>> toSeedRecords(final InputStream inputStream)
			throws SeedException, IOException {

		List<Record<? extends Blockette>> records = new ArrayList<>();
		try (SeedRecordIterator it = SeedIOUtils.recordIterator(inputStream);) {
			while (it.hasNext()) {
				records.add(it.next());
			}
			return records;
		}
	}

	public static void writeSeed(final File file, SeedVolume volume) throws SeedException, IOException {
		writeSeed(file, volume, 4096);
	}

	public static void writeSeed(final File file, SeedVolume volume, int recordLength)
			throws SeedException, IOException {
		if (file.exists()) {
			if (file.isDirectory()) {
				throw new IOException("File '" + file + "' exists but is a directory");
			}
			if (file.canWrite() == false) {
				throw new IOException("File '" + file + "' cannot be written to");
			}
		} else {
			final File parent = file.getParentFile();
			if (parent != null) {
				if (!parent.mkdirs() && !parent.isDirectory()) {
					throw new IOException("Directory '" + parent + "' could not be created");
				}
			}
		}

		try (OutputStream outputStream = new FileOutputStream(file)) {
			volume.writeTo(outputStream, recordLength);
		}

	}
}
