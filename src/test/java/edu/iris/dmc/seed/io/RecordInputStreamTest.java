package edu.iris.dmc.seed.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

import edu.iris.dmc.seed.blockette.util.BlocketteItrator;
import edu.iris.dmc.seed.builder.BlocketteBuilder;
import edu.iris.dmc.seed.director.BlocketteDirector;

public class RecordInputStreamTest {

	@Test
	public void d() throws Exception {
		// RecordInputStream stream = new RecordInputStream();
		try (InputStream stream = new FileInputStream(new File("/Users/Suleiman/RESP/AU.BN2S.fromHughGlanville.dataless.20180925"))) {
			BlocketteDirector director = new BlocketteDirector(new BlocketteBuilder());
			BlocketteItrator iterator = director.process(stream);
			
			while(iterator.hasNext()) {
				System.out.println(iterator.next().toSeedString());
			}
		}
	}
}
