package edu.iris.seed.index;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;

public class B005Test {
	@Test
	public void b005() throws Exception {
		String oldString = "0050036 2.1121992,001,00:00:00.0000~";
		Blockette b = B005.Builder.newInstance().build(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}
}
