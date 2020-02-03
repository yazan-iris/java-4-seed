package edu.iris.seed.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;

import edu.iris.seed.SeedException;
import edu.iris.seed.SeedInputStream;
import edu.iris.seed.SeedVolume;
import edu.iris.seed.io.output.StringBuilderOutputStream;
import edu.iris.seed.record.AbbreviationRecord;
import edu.iris.timeseries.Timeseries;

public class SeedFileUtils {

	public static String volumeToString(final File file, int recordLength) throws SeedException, IOException {
		checkFile(file);
		try (InputStream inputStream = new FileInputStream(file);) {
			SeedVolume v = SeedIOUtils.toSeedVolume(inputStream);
			StringBuilderOutputStream outputStream = new StringBuilderOutputStream();
			v.writeTo(outputStream, recordLength);
			return outputStream.toString();
		}
	}

	public static String abbreviationRecordToString(final File file, int recordLength)
			throws SeedException, IOException {
		checkFile(file);
		
		try (InputStream inputStream = new FileInputStream(file);) {
			AbbreviationRecord v = SeedIOUtils.toAbbreviationRecord(inputStream);
			StringBuilderOutputStream outputStream = new StringBuilderOutputStream();
			v.writeTo(outputStream, recordLength, 1);
			return outputStream.toString();
		}
	}

	public static String readSeedFileToString(final Path path) throws SeedException, IOException {
		return readSeedFileToString(path.toFile());
	}

	public static String readSeedFileToString(final File file) throws SeedException, IOException {
		checkFile(file);
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

	public static List<Timeseries> toTimeseries(final File file) throws SeedException, IOException {
		return toTimeseries(file, false);
	}

	public static List<Timeseries> toTimeseries(final File file, boolean reduce) throws SeedException, IOException {
		checkFile(file);
		try (InputStream inputStream = new FileInputStream(file)) {
			return SeedIOUtils.toTimeseries(inputStream, reduce);
		}
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

	public static SeedInputStream toSeedInputStream(File file) throws SeedException, IOException {
		checkFile(file);
		return SeedIOUtils.toSeedInputStream(new FileInputStream(file));
	}

	public static SeedBlocketteIterator toBlocketteIterator(File file) throws SeedException, IOException {
		checkFile(file);
		return SeedIOUtils.toBlocketteIterator(new FileInputStream(file));
	}

	public static void checkFile(final File file) throws IOException {
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

	public static SeedBlocketteIterator blocketteIterator(final File file) throws SeedException, IOException {
		checkFile(file);
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);
			return SeedIOUtils.toBlocketteIterator(inputStream);
		} catch (final IOException | RuntimeException ex) {
			if (inputStream != null) {
				inputStream.close();
			}
			throw ex;
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
