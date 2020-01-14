package edu.iris.seed.abbreviation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;

public class B048Test {
	@Test
	public void fromString() throws Exception {
		String text = "048  56   5GLBELA2009016HXXXX~ 3.11333E+05 0.00000E+00 0";
		Blockette b = B048.Builder.newInstance().build(48, new String(text).getBytes());
		assertEquals("04800560005GLBELA2009016HXXXX~+3.11333E+05+0.00000E+0000", b.toSeedString());

	}
}
