package edu.iris.seed.abbreviation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;

public class B043Test {
	@Test
	public void fromString() throws Exception {
		String text = "043 408   1RSSENSOR201301082239159~A  1  3 5.71572E+08 1.00000E+00  2 0.00000E+00 0.00000E+00 0.00000E-00 0.00000E-00 0.00000E+00 0.00000E+00 0.00000E-00 0.00000E-00  5-7.40159E-02-7.40159E-02 0.00000E-00 0.00000E-00-7.40159E-02 7.40159E-02 0.00000E-00 0.00000E-00-1.00531E+03 0.00000E+00 0.00000E-00 0.00000E-00-5.02655E+02 0.00000E+00 0.00000E-00 0.00000E-00-1.13097E+03 0.00000E+00 0.00000E-00 0.00000E-00";
		String expected = "04304080001RSSENSOR201301082239159~A001003+5.71572E+08+1.00000E+00002+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00+0.00000E+00005-7.40159E-02-7.40159E-02+0.00000E+00+0.00000E+00-7.40159E-02+7.40159E-02+0.00000E+00+0.00000E+00-1.00531E+03+0.00000E+00+0.00000E+00+0.00000E+00-5.02655E+02+0.00000E+00+0.00000E+00+0.00000E+00-1.13097E+03+0.00000E+00+0.00000E+00+0.00000E+00";
		Blockette b = B043.Builder.newInstance().build(43, new String(text).getBytes());
		assertEquals(expected, b.toSeedString());

		B043 fromExpected = (B043) B043.Builder.newInstance().build(43, expected.getBytes());
		assertEquals(expected, fromExpected.toSeedString());
	}
}
