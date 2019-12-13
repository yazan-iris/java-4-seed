package edu.iris.dmc.seed.io;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

import edu.iris.dmc.seed.Record;
import edu.iris.dmc.seed.SeedException;
import edu.iris.dmc.seed.Volume;

public class SeedBufferedOutputStream implements Closeable {

	private OutputStream outputStream;

	public SeedBufferedOutputStream(OutputStream outputStream, int size) {
		if (size <= 0) {
			throw new IllegalArgumentException("Buffer size <= 0");
		}
		this.outputStream = outputStream;
	}

	public void write(Volume volume) throws IOException {
		for (Record record : volume.getRecords()) {
			write(record);
		}
	}

	public void write(Record record) throws IOException {
		try {
			for (byte b : record.getBytes()) {
				if (b == 0) {
					outputStream.write(' ');
				} else {
					outputStream.write(b);
				}
			}
		} catch (SeedException e) {
			throw new IOException(e);
		}
		outputStream.flush();
	}

	public void close() throws IOException {
		if (this.outputStream != null) {
			this.outputStream.close();
		}
	}
}
