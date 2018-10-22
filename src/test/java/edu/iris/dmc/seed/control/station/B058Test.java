package edu.iris.dmc.seed.control.station;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.BlocketteFactory;

public class B058Test {

	String text = "058003500+1.00700E+09+2.00000E-0200";

	@Test
	public void fromString() throws Exception {

		Blockette b058 = BlocketteFactory.create(text.getBytes());
		assertEquals(text, b058.toSeedString());
	}

	@Test
	public void fromObject() throws Exception {
		B058 b = new B058();
		b.setFrequency(2E-2);
		// b.setHistory(history);
		b.setSensitivity(1.007E9);
		// b.setSignalInputUnit(signalInputUnit);
		// b.setSignalOutputUnit(signalOutputUnit);
		b.setStageSequence(0);
		assertEquals(text, b.toSeedString());
	}
}
