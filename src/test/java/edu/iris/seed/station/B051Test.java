package edu.iris.seed.station;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;

public class B051Test {

	@Test
	public void b051() throws Exception {
		String oldString = "05100632012,275,06:52:37.0000~2014,317,01:06:40.0000~0001000000";
		Blockette b = B051.Builder.newInstance().build(oldString.getBytes());

		String newString = b.toSeedString();
		assertEquals(oldString, newString);

		oldString = "05100632012,275,06:52:37.0000~2014,317,00:00:00.0000~0001000000";
		b = B051.Builder.newInstance().build(oldString.getBytes());
		newString = b.toSeedString();
		assertEquals(oldString, newString);
	}
}
