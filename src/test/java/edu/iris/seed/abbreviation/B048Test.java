package edu.iris.seed.abbreviation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;
import edu.iris.seed.ControlBlockette;

public class B048Test {
	@Test
	public void fromString() throws Exception {
		String text = "048  56   5GLBELA2009016HXXXX~ 3.11333E+05 0.00000E+00 0";
		ControlBlockette b = B048.Builder.newInstance().fromBytes(new String(text).getBytes()).build();
		assertEquals("04800560005GLBELA2009016HXXXX~+3.11333E+05+0.00000E+0000", b.toSeedString());

	}
}
