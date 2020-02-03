package edu.iris.seed;

import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.iris.seed.io.SeedIOUtils;

public class InvalidSeedTest {

	@Test
	public void invalidBlocketteForStage0() throws Exception {

		try (InputStream inputStream = InvalidSeedTest.class.getClassLoader()
				.getResourceAsStream("metadata-SBD-SBD-19840725-99990101.seedmetadata");) {
			Assertions.assertThrows(SeedException.class, () -> {
				SeedIOUtils.toSeedVolume(inputStream);
			});
		}
	}

}
