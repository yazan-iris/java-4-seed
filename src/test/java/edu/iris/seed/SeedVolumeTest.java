package edu.iris.seed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import edu.iris.seed.io.SeedFileUtils;

public class SeedVolumeTest {

	@Test
	void writeItemsToFile1(@TempDir Path tempDir) throws Exception {

		int numberOfBlockettes = 621;
		int numberOfRecords = 3;

		SeedVolume v = SeedFileUtils.toSeedVolume(new File(
				getClass().getClassLoader().getResource("AU.MILA.dataless.fromHughGlanville.20181018").getFile()));

		assertEquals(numberOfBlockettes, v.getAll().size());
		assertEquals(numberOfRecords, v.getNumberOfRecords());
		Path path = tempDir.resolve("test1.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 256);

		SeedVolume v1 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v1.getAll().size());
		assertEquals(numberOfRecords, v1.getNumberOfRecords());

		path = tempDir.resolve("test2.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 512);
		SeedVolume v2 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v2.getAll().size());
		assertEquals(numberOfRecords, v2.getNumberOfRecords());

		path = tempDir.resolve("test3.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 1024);
		SeedVolume v3 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v3.getAll().size());
		assertEquals(numberOfRecords, v3.getNumberOfRecords());

		path = tempDir.resolve("test4.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 2048);
		SeedVolume v4 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(621, v4.getAll().size());
		assertEquals(3, v4.getNumberOfRecords());

		path = tempDir.resolve("test5.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 4096);
		SeedVolume v5 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(621, v5.getAll().size());
		assertEquals(numberOfRecords, v5.getNumberOfRecords());

		path = tempDir.resolve("test6.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 8192);
		SeedVolume v6 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v6.getAll().size());
		assertEquals(numberOfRecords, v6.getNumberOfRecords());

		path = tempDir.resolve("test7.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 16384);
		SeedVolume v7 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v7.getAll().size());
		assertEquals(numberOfRecords, v7.getNumberOfRecords());

		path = tempDir.resolve("test8.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 32768);
		SeedVolume v8 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v8.getAll().size());
		assertEquals(numberOfRecords, v8.getNumberOfRecords());
	}

	@Test
	void writeItemsToFile2(@TempDir Path tempDir) throws Exception {

		int numberOfBlockettes = 4958;
		int numberOfRecords = 35;
		SeedVolume v = SeedFileUtils.toSeedVolume(
				new File(getClass().getClassLoader().getResource("ZR_2013_fromSEISUK.dataless.seed").getFile()));

		assertEquals(numberOfBlockettes, v.getAll().size());
		assertEquals(numberOfRecords, v.getNumberOfRecords());
		Path path = tempDir.resolve("test1.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 256);

		SeedVolume v1 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v1.getAll().size());
		assertEquals(numberOfRecords, v1.getNumberOfRecords());

		path = tempDir.resolve("test2.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 512);
		SeedVolume v2 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v2.getAll().size());
		assertEquals(numberOfRecords, v2.getNumberOfRecords());

		path = tempDir.resolve("test3.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 1024);
		SeedVolume v3 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v3.getAll().size());
		assertEquals(numberOfRecords, v3.getNumberOfRecords());

		path = tempDir.resolve("test4.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 2048);
		SeedVolume v4 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v4.getAll().size());
		assertEquals(numberOfRecords, v4.getNumberOfRecords());

		path = tempDir.resolve("test5.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 4096);
		SeedVolume v5 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v5.getAll().size());
		assertEquals(numberOfRecords, v5.getNumberOfRecords());

		path = tempDir.resolve("test6.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 8192);
		SeedVolume v6 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v6.getAll().size());
		assertEquals(numberOfRecords, v6.getNumberOfRecords());

		path = tempDir.resolve("test7.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 16384);
		SeedVolume v7 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v7.getAll().size());
		assertEquals(numberOfRecords, v7.getNumberOfRecords());

		path = tempDir.resolve("test8.seed");
		SeedFileUtils.writeSeed(path.toFile(), v, 32768);
		SeedVolume v8 = SeedFileUtils.toSeedVolume(path.toFile());
		assertEquals(numberOfBlockettes, v8.getAll().size());
		assertEquals(numberOfRecords, v8.getNumberOfRecords());
	}
}
