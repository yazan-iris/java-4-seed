package edu.iris.dmc.seed.control.station;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.BlocketteFactory;

public class B052Test {

	@Test
	public void te() throws Exception {
		String text = "0520149  BDF0000004~001002+28.209718-177.381430+0004.6000.0000.0+00.00001122.0000E+010.0000E+000000CG~2013,315,00:00:00.0000~2017,045,00:00:00.0000~N";
		Blockette b052 = BlocketteFactory.create(text.getBytes());
		assertEquals(text, b052.toSeedString());
	}
}
