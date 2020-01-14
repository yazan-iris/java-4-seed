package edu.iris.seed.index;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;

public class B008Test {

	@Test
	public void b008() throws Exception {
		String oldString = "008011702.112 ANMO  BHZ1992,001,00:00:00.0000~1992,002,00:00:00.0000~1992,002,00:00:00.0000~1993,029,00:00:00.0000~IU";
		// 008011702.112 ANMO
		// BHZ1992,001,00:00:00.0000~1992,002,00:00:00.0000~1992,002,00:00:00.0000~1993,029,00:00:00.0000~IU
		Blockette b = B008.Builder.newInstance().build(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}
}
