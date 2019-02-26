package edu.iris.dmc.seed.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

import edu.iris.dmc.seed.blockette.util.BlocketteItrator;
import edu.iris.dmc.seed.builder.BlocketteBuilder;
import edu.iris.dmc.seed.director.BlocketteDirector;
import edu.iris.dmc.seed.director.BlocketteDirectorTest;

public class RecordInputStreamTest {

	@Test
	public void d() throws Exception {
		
		
		File source = null;

		source = new File(BlocketteDirectorTest.class.getClassLoader().getResource("AU.MILA.dataless.fromHughGlanville.20181018").getFile());

		BlocketteDirector director = new BlocketteDirector(new BlocketteBuilder());
		try (InputStream stream = new FileInputStream(source)) {
			BlocketteItrator iterator = director.process(stream);
			while(iterator.hasNext()) {
			System.out.println(iterator.next().toSeedString());
			}
		}
	}
}
