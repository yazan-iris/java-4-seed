package edu.iris.dmc.seed.control.dictionary;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.BlocketteFactory;

public class B049Test {
	@Test
	public void b049() throws Exception {
		String text = "0620129P00021020MB 0.00000E+00 0.00000E+00 0.00000E+00 5.00000E+01 0.00000E+00002-8.39688E+01 0.00000E+00 2.56250E-03 0.00000E+00";
		Blockette b049 = BlocketteFactory.create(text.getBytes());
		assertEquals(
				"0620129P00021020MB+0.00000E+00+0.00000E+00+0.00000E+00+5.00000E+01+0.00000E+00002-8.39688E+01+0.00000E+00+2.56250E-03+0.00000E+00",
				b049.toSeedString());
	}
}
