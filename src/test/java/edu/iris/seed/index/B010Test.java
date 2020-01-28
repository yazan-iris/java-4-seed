package edu.iris.seed.index;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;
import edu.iris.seed.Identifier.B010;

public class B010Test {

	@Test
	public void b010() throws Exception {
		String oldString = "010012502.1121992,001,00:00:00.0000~1992,002,00:00:00.0000~1993,029,00:00:00.0000~IRIS _ DMC~Data for 1992,001,00:00:00.0000~";
		B010 b = B010.Builder.newInstance().fromBytes(oldString.getBytes()).build();
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}
}
