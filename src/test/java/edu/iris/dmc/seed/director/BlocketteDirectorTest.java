package edu.iris.dmc.seed.director;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.Test;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.blockette.util.BlocketteItrator;
import edu.iris.dmc.seed.builder.BlocketteBuilder;

public class BlocketteDirectorTest {

	@Test
	public void t1() throws Exception{
		File source = null, target = null;

		source = new File(BlocketteDirectorTest.class.getClassLoader().getResource("dataless.CI.DJJB.061013").getFile());

		BlocketteDirector director = new BlocketteDirector();
		try (InputStream inputStream = new FileInputStream(source)) {
			BlocketteItrator iterator = director.process(inputStream);
			
			while (iterator.hasNext()) {
				Blockette blockette = iterator.next();
				int type = blockette.getType();
				//System.out.println(blockette.toSeedString());
			}
		}

	}
}
