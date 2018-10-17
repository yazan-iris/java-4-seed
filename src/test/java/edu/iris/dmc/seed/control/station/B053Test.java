package edu.iris.dmc.seed.control.station;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.BlocketteFactory;

public class B053Test {

	@Test
	public void te() throws Exception {
		String text = "0530382A01001002+3.14096E+02+1.00000E+00003+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-1.70000E-01+0.00000E+00+0.00000E+00+0.00000E+00004+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-3.14000E+02+0.00000E+00+0.00000E+00+0.00000E+00-1.88000E-01+0.00000E+00+0.00000E+00+0.00000E+00-4.40000E-02+0.00000E+00+0.00000E+00+0.00000E+00";

		Blockette b053 = BlocketteFactory.create(text.getBytes());
		assertEquals(text, b053.toSeedString());
	}
}
