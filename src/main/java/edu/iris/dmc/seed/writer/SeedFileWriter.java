package edu.iris.dmc.seed.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import edu.iris.dmc.io.FileFormat;
import edu.iris.dmc.io.FileWriter;
import edu.iris.dmc.seed.SeedFormatter;
import edu.iris.dmc.seed.Record;
import edu.iris.dmc.seed.BlocketteFormatter;
import edu.iris.dmc.seed.Volume;
import edu.iris.dmc.seed.io.SeedBufferedOutputStream;

public class SeedFileWriter implements FileWriter {

	private Logger LOGGER = Logger.getLogger(SeedFileWriter.class.getName());
	private SeedFormatter formatter = new BlocketteFormatter();

	private SeedBufferedOutputStream stream;

	public SeedFileWriter(File file, int recordSize) throws IOException {
		this.stream = new SeedBufferedOutputStream(new FileOutputStream(file), recordSize);
	}

	public boolean isFileTypeSupported(FileFormat.TYPE type) {
		if (type != FileFormat.TYPE.SEED) {
			return false;
		}
		return true;
	}

	@Override
	public void write(Volume volume) throws IOException {
		write(volume, this.formatter);
	}

	@Override
	public void write(Volume volume, SeedFormatter formatter) throws IOException {
		for (Record record : volume.getRecords()) {
			this.stream.write(record);
		}
	}

	public SeedFormatter getFormatter() {
		return formatter;
	}

	public void setFormatter(SeedFormatter formatter) {
		this.formatter = formatter;
	}

	public void close() throws IOException {
		if (this.stream != null) {
			this.stream.close();
		}
	}
}
