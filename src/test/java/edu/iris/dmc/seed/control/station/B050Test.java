package edu.iris.dmc.seed.control.station;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.BlocketteFactory;

public class B050Test {

	@Test
	public void te() throws Exception {
		String text = "0500154I58H1+28.209718-177.381430+0004.60001000Midway Islands Infrasonic Array, Site I58H1, USA~0013210102013,315,00:00:00.0000~2999,365,23:59:59.0000~NIM";
		Blockette b050 = BlocketteFactory.create(text.getBytes());
		assertEquals(text, b050.toSeedString());
	}
}
