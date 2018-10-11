package edu.iris.dmc.seed.director;

import java.io.IOException;
import java.io.InputStream;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.blockette.util.BlocketteItrator;
import edu.iris.dmc.seed.builder.Builder;
import edu.iris.dmc.seed.io.RecordInputStream;

public class BlocketteDirector {

	private Builder<Blockette> builder;

	public BlocketteDirector(Builder<Blockette> blocketteBuilder) {
		this.builder = blocketteBuilder;
	}

	public BlocketteItrator process(InputStream inputStream) throws IOException {
		//SeedRecordInputStream seedRecordInputStream = new SeedRecordInputStream(inputStream);
		RecordInputStream seedRecordInputStream = new RecordInputStream(inputStream);
		return new BlocketteItrator(seedRecordInputStream, builder);
	}

}
