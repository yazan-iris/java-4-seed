package edu.iris.seed.abbreviation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;
import edu.iris.seed.ControlBlockette;

public class B034Test {
	@Test
	public void fromString() throws Exception {
		String text = "0340049178COUNTS~Digital Count in Digital counts~";
		ControlBlockette b = B034.Builder.newInstance().fromBytes(new String(text).getBytes()).build();

		assertEquals(text, b.toSeedString());

	}
}
