package edu.iris.seed.abbreviation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;

public class B034Test {
	@Test
	public void fromString() throws Exception {
		String text = "0340049178COUNTS~Digital Count in Digital counts~";
		Blockette b = B034.Builder.newInstance().build(34, new String(text).getBytes());

		assertEquals(text, b.toSeedString());

	}
}
