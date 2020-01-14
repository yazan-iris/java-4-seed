package edu.iris.seed.station;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;

public class B062Test {

	@Test
	public void b062() throws Exception {
		String text = "0620129P00021020MB 0.00000E+00 0.00000E+00 0.00000E+00 5.00000E+01 0.00000E+00002-8.39688E+01 0.00000E+00 2.56250E-03 0.00000E+00";
		Blockette b062 = B062.Builder.newInstance().build(text.getBytes());
		assertEquals(
				"0620129P00021020MB+0.00000E+00+0.00000E+00+0.00000E+00+5.00000E+01+0.00000E+00002-8.39688E+01+0.00000E+00+2.56250E-03+0.00000E+00",
				b062.toSeedString());
	}

}
