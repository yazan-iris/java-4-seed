package edu.iris.dmc.seed.control.station;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.BlocketteFactory;

public class B054Test {

	@Test
	public void shortBlockette() throws Exception {
		String text = "0540024D0200200300000000";

		Blockette b054 = BlocketteFactory.create(text.getBytes());
		assertEquals(text, b054.toSeedString());
	}
	
	@Test
	public void longBlockette() throws Exception {
		String text = "0540888D030030030036+3.05176E-05+0.00000E+00+1.52588E-04+0.00000E+00+4.57764E-04+0.00000E+00+1.06812E-03+0.00000E+00+2.13623E-03+0.00000E+00+3.84521E-03+0.00000E+00+6.40869E-03+0.00000E+00+1.00708E-02+0.00000E+00+1.49536E-02+0.00000E+00+2.10571E-02+0.00000E+00+2.82593E-02+0.00000E+00+3.63159E-02+0.00000E+00+4.48608E-02+0.00000E+00+5.34058E-02+0.00000E+00+6.13403E-02+0.00000E+00+6.79321E-02+0.00000E+00+7.26318E-02+0.00000E+00+7.50732E-02+0.00000E+00+7.50732E-02+0.00000E+00+7.26318E-02+0.00000E+00+6.79321E-02+0.00000E+00+6.13403E-02+0.00000E+00+5.34058E-02+0.00000E+00+4.48608E-02+0.00000E+00+3.63159E-02+0.00000E+00+2.82593E-02+0.00000E+00+2.10571E-02+0.00000E+00+1.49536E-02+0.00000E+00+1.00708E-02+0.00000E+00+6.40869E-03+0.00000E+00+3.84521E-03+0.00000E+00+2.13623E-03+0.00000E+00+1.06812E-03+0.00000E+00+4.57764E-04+0.00000E+00+1.52588E-04+0.00000E+00+3.05176E-05+0.00000E+000000";

		Blockette b054 = BlocketteFactory.create(text.getBytes());
		assertEquals(text, b054.toSeedString());
	}
}
