package edu.iris.dmc.seed;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.iris.dmc.seed.control.station.B057;

public class B057Test {

	@Test
	public void te() throws Exception {
		String text = "057005104+6.400E+040000200000+3.9062E-05+3.9062E-05";

		Blockette b057 = BlocketteFactory.create(text.getBytes());
		assertEquals(text, b057.toSeedString());

	}
}
