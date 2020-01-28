package edu.iris.seed;

import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.iris.seed.io.SeedFileUtils;

public class MiniSeedTest {

	@Test
	void read(@TempDir Path tempDir) throws Exception {

		SeedVolume v = SeedFileUtils
				.toSeedVolume(new File(getClass().getClassLoader().getResource("mseed/ADK.IU.2020.001").getFile()));

		for (Record<? extends Blockette> r : v.records()) {
			System.out.println(r.getHeader().toString());
		}
	}
}
