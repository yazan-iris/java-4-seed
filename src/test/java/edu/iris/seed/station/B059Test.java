package edu.iris.seed.station;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;
import edu.iris.seed.SeedBlockette;

public class B059Test {

	@Test
	public void b059() throws Exception {
		String oldString = "05900632012,284,20:00:00.0000~2013,317,01:06:40.0000~0003000000";
		B059 b = B059.Builder.newInstance().fromBytes(oldString.getBytes()).build();
		String newString = b.toSeedString();
		assertEquals(oldString, newString);

		oldString = "05900632012,284,20:00:00.0000~1987,023,00:00:00.0000~0003000000";
		b = B059.Builder.newInstance().fromBytes(oldString.getBytes()).build();
		newString = b.toSeedString();
		assertEquals(oldString, newString);
	}
	
	@Test
	public void b059NoStartTime() throws Exception {
		String oldString = "0590041~2013,317,01:06:40.0000~0003000000";
		B059 b = B059.Builder.newInstance().fromBytes(oldString.getBytes()).build();
		String newString = b.toSeedString();
		assertEquals(oldString, newString);

		oldString = "0590041~2013,317,01:06:40.0000~0003000000";
		b = B059.Builder.newInstance().fromBytes(oldString.getBytes()).build();
		newString = b.toSeedString();
		assertEquals(oldString, newString);
	}
}
