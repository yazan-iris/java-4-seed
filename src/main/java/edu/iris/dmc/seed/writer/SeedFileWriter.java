package edu.iris.dmc.seed.writer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

import edu.iris.dmc.io.FileFormat;
import edu.iris.dmc.io.FileWriter;
import edu.iris.dmc.io.Formatter;
import edu.iris.dmc.io.SeedFormatter;
import edu.iris.dmc.seed.Record;
import edu.iris.dmc.seed.Volume;
import edu.iris.dmc.seed.io.SeedBufferedOutputStream;

public class SeedFileWriter implements FileWriter {

	private Logger LOGGER = Logger.getLogger(SeedFileWriter.class.getName());
	private Formatter formatter = new SeedFormatter();

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
	public void write(Volume volume, Formatter formatter) throws IOException {
		for (Record record : volume.getRecords()) {
			this.stream.write(record);
		}
	}





	public Formatter getFormatter() {
		return formatter;
	}

	public void setFormatter(Formatter formatter) {
		this.formatter = formatter;
	}

}
