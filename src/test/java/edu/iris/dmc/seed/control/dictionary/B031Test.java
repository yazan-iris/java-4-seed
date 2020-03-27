package edu.iris.dmc.seed.control.dictionary;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.iris.dmc.seed.builder.BlocketteBuilder;
import edu.iris.dmc.seed.control.index.B012;

public class B031Test {
	@Test
	public void b0O31() throws Exception {
		String oldString = "03100590001C SCI_1 -0.003495@100, -0.1263@10, -0.1077@1~000";
		B031 b = BlocketteBuilder.build031(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}

	@Test
	public void b0O31overflow() throws Exception {
		String oldString = "03100650001C SCI_1 -0.003495@100, -0.1263@10, -0.1077@1  afsdfsdfdsfdsfdsfdsfsdbfbweerlkfgsdlldsfskhjdflsfbhjsfjlsdfjhsdhfsdlfhgsdfksdgfhjdsf    ~000";
		B031 b = BlocketteBuilder.build031(oldString.getBytes());
		String newString = b.toSeedString();
		assertNotEquals(oldString, newString);
	}
}
