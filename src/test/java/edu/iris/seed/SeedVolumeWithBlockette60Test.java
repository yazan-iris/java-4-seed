package edu.iris.seed;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.iris.seed.io.SeedFileUtils;

public class SeedVolumeWithBlockette60Test {

	@Test
	void writeItemsToFile(@TempDir Path tempDir) throws Exception {

		int numberOfBlockettes = 32;
		int numberOfRecords = 3;

		SeedVolume v = SeedFileUtils.toSeedVolume(
				new File(getClass().getClassLoader().getResource("AI.dataless_arc.BELA.fromORFEUS").getFile()));

		int size = 0;
		for(Blockette b:v.blockettes()) {
			System.out.println("+++"+b.toString());
		}
		assertEquals(2,v.getIdentifierRecord().size());
		assertEquals(20,v.getAbbreviationRecord().size());
		assertEquals(numberOfBlockettes, v.size());
		Path path = tempDir.resolve("test1.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 256);

		SeedVolume v1 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v1.size());

		path = tempDir.resolve("test2.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 512);
		SeedVolume v2 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v2.size());

		path = tempDir.resolve("test3.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 1024);
		SeedVolume v3 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v3.size());

		path = tempDir.resolve("test4.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 2048);
		SeedVolume v4 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v4.size());

		path = tempDir.resolve("test5.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 4096);
		SeedVolume v5 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v5.size());

		path = tempDir.resolve("test6.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 8192);
		SeedVolume v6 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v6.size());

		path = tempDir.resolve("test7.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 16384);
		SeedVolume v7 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v7.size());

		path = tempDir.resolve("test8.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 32768);
		SeedVolume v8 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v8.size());
	}

}
