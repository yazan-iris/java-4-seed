package edu.iris.seed.abbreviation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class B033Test {
	@Test
	public void fromString() throws Exception {
		B033 b = new B033();
		b.setDescription("My testing blockette");
		assertEquals("0330031000My testing blockette~", b.toSeedString());
	}
}
