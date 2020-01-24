package edu.iris.seed.station;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;
import edu.iris.seed.SeedBlockette;

public class B058Test {

	String text = "058003500+1.00700E+09+2.00000E-0200";

	@Test
	public void fromString() throws Exception {
		Blockette b = SeedBlockette.builder(58).fromBytes(text.getBytes()).build();
		assertEquals(text, b.toSeedString());
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
