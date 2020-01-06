package edu.iris.dmc.seed.director;

import java.io.InputStream;

import edu.iris.dmc.seed.blockette.util.BlocketteItrator;
import edu.iris.dmc.seed.io.RecordInputStream;

public class BlocketteDirector {

	public BlocketteItrator process(InputStream inputStream) {
		RecordInputStream seedRecordInputStream = new RecordInputStream(inputStream);
		return new BlocketteItrator(seedRecordInputStream);
	}

}
