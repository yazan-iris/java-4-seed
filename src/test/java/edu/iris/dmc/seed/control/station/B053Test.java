package edu.iris.dmc.seed.control.station;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import edu.iris.dmc.seed.BlocketteFactory;

public class B053Test {

	@Test
	public void te() throws Exception {
		String text = "0530382A01001002+3.14096E+02+1.00000E+00003+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-1.70000E-01+0.00000E+00+0.00000E+00+0.00000E+00004+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00-3.14000E+02+0.00000E+00+0.00000E+00+0.00000E+00-1.88000E-01+0.00000E+00+0.00000E+00+0.00000E+00-4.40000E-02+0.00000E+00+2.00000E-04+2.00000E-04";
		B053 b053 = (B053) BlocketteFactory.create(text.getBytes());

		assertNotNull(b053.getPoles());
		assertEquals(4, b053.getPoles().size());
		Pole pole = b053.getPoles().get(3);
		assertNotNull(pole.getImaginary());
		assertEquals(0, pole.getImaginary().getValue(),0.0000000001);
		assertEquals(0.00020E+00, pole.getImaginary().getError(),0.0000000001);
		assertEquals(text, b053.toSeedString());
	}
}
