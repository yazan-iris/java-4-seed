package edu.iris.seed;

import java.io.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.iris.seed.io.SeedFileUtils;

public class ReadFileTest {

	@Test
	void read() throws Exception {
		Assertions.assertThrows(RuntimeException.class, () -> {
			SeedFileUtils
			.toSeedVolume(new File(getClass().getClassLoader().getResource("SBDdatalessfromORFEUS.dseed").getFile()));
		  });
	}
}
