package edu.iris.dmc.seed.control.dictionary;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class B033Test {
	@Test
	public void fromString() throws Exception {
		B033 b = new B033();
		b.setDescription("My testing blockette");
		assertEquals("0330031000My testing blockette~", b.toSeedString());
	}
}
