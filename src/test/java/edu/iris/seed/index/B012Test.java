package edu.iris.seed.index;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;

public class B012Test {
	@Test
	public void b012() throws Exception {
		String oldString = "012006300011992,001,00:00:00.0000~1992,002,00:00:00.0000~000014";
		Blockette b = B012.Builder.newInstance().build(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}
}
