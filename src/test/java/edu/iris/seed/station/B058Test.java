package edu.iris.seed.station;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;

public class B058Test {

	String text = "058003500+1.00700E+09+2.00000E-0200";

	@Test
	public void fromString() throws Exception {

		Blockette b058 = B058.Builder.newInstance().build(text.getBytes());
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
		b.setStageNumber(0);
		assertEquals(text, b.toSeedString());
	}
}
