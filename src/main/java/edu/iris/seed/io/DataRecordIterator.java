package edu.iris.seed.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import edu.iris.seed.SeedException;
import edu.iris.seed.SeedInputStream;
import edu.iris.seed.record.DataRecord;

public class DataRecordIterator implements Iterator<DataRecord> {

	private SeedInputStream seedInputStream;

	private DataRecord cachedRecord;
	private boolean finished;

	public DataRecordIterator(InputStream inputStream) {
		seedInputStream = new SeedInputStream(inputStream);
	}

	@Override
	public boolean hasNext() {
		if (cachedRecord != null) {
			return true;
		} else if (finished) {
			return false;
		} else {
			while (true) {
				final DataRecord r = next();
				if (r == null) {
					finished = true;
					return false;
				} else {
					cachedRecord = r;
					return true;
				}
			}
		}
	}

	@Override
	public DataRecord next() {
		try {
			byte[] bytes = seedInputStream.read();
			if(bytes==null) {
				return null;
			}
			return DataRecord.Builder.newInstance().fromBytes(bytes).build();
		} catch (IOException | SeedException e) {
			throw new RuntimeException(e);
		}
	}

}
