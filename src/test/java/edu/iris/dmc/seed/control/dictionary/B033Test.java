package edu.iris.dmc.seed.control.dictionary;

import org.junit.jupiter.api.Test;

import edu.iris.dmc.seed.builder.BlocketteBuilder;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class B033Test {
	@Test
	public void fromString() throws Exception {
		B033 b = new B033();
		b.setDescription("My testing blockette");
		assertEquals("0330031000My testing blockette~", b.toSeedString());
	}
	
	@Test
	public void b0O33() throws Exception {
		String oldString = "0330038001INTERNATIONAL-MISCELLANEOUS~";
		B033 b = BlocketteBuilder.build033(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}
	
	
	
	@Test
	public void b33overflow() throws Exception {
		String oldString = "0330038001INTERNATIONAL-MISCELLANEOUSsadfjksdfghjksdjkhfgshdjfkjshfshfhshfkdhsjfhskjfssdghjkfdhjsf~";
		B033 b = BlocketteBuilder.build033(oldString.getBytes());
		String newString = b.toSeedString();
		assertNotEquals(oldString, newString);

	}
}
