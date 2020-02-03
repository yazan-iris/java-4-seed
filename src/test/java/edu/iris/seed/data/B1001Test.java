package edu.iris.seed.data;

import org.junit.jupiter.api.Test;

public class B1001Test {

	@Test
	public void b100() throws Exception {

		B1001 b = B1001.Builder.newInstance().frameCount(3).microSeconds(2).timingQuality(2).build();
		b = B1001.Builder.newInstance().fromBytes(b.toSeedBytes()).build();

	}
}
