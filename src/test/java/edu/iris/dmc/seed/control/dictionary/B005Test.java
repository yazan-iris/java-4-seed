package edu.iris.dmc.seed.control.dictionary;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.iris.dmc.seed.builder.BlocketteBuilder;
import edu.iris.dmc.seed.control.index.B005;

public class B005Test {
	@Test
	public void b005() throws Exception {
		String oldString = "0050036 2.1121992,001,00:00:00.0000~";
		B005 b = BlocketteBuilder.build005(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}
}
