package edu.iris.seed;

import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.iris.seed.io.SeedFileUtils;
import edu.iris.seed.station.B061;

public class ReadFileTest {

	@Test
	void read() throws Exception {
		Assertions.assertThrows(RuntimeException.class, () -> {
			SeedFileUtils
			.toSeedVolume(new File(getClass().getClassLoader().getResource("SBDdatalessfromORFEUS.dseed").getFile()));
		  });
	}
}
