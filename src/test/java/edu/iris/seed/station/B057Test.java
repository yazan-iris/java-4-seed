package edu.iris.seed.station;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;
import edu.iris.seed.SeedBlockette;

public class B057Test {

	@Test
	public void te() throws Exception {
		String text = "0570051046.4000E+040000200000+3.9062E-05+3.9062E-05";
		B057 b = B057.Builder.newInstance().fromBytes(text.getBytes()).build();
		assertEquals(text, b.toSeedString());
	}
}
