package edu.iris.dmc.seed.control.volume;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import edu.iris.dmc.seed.builder.BlocketteBuilder;
import edu.iris.dmc.seed.control.index.B011;

public class B011Test {
	@Test
	public void b011() throws Exception {
		String oldString = "0110054004AAK  000003ANMO 000007ANTO 000010BJI  000012";
		B011 b = BlocketteBuilder.build011(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}
}
