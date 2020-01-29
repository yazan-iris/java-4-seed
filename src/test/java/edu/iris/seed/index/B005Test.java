package edu.iris.seed.index;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Identifier.B005;

public class B005Test {
	@Test
	public void b005() throws Exception {
		String oldString = "0050036 2.1121992,001,00:00:00.0000~";
		B005 b = B005.Builder.newInstance().fromBytes(oldString.getBytes()).build();
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}
}
