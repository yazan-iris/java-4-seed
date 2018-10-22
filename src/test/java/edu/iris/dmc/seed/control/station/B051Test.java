package edu.iris.dmc.seed.control.station;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import edu.iris.dmc.seed.builder.BlocketteBuilder;

public class B051Test {

	@Test
	public void b051() throws Exception {
		String oldString = "05100632012,275,06:52:37.0000~2014,317,01:06:40.0000~0001000000";
		B051 b = BlocketteBuilder.build051(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);

		oldString = "05100632012,275,06:52:37.0000~2014,317,00:00:00.0000~0001000000";
		b = BlocketteBuilder.build051(oldString.getBytes());
		newString = b.toSeedString();
		assertEquals(oldString, newString);
	}
}
