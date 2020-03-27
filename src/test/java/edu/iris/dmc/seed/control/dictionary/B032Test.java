package edu.iris.dmc.seed.control.dictionary;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.iris.dmc.seed.builder.BlocketteBuilder;
import edu.iris.dmc.seed.control.index.B012;

public class B032Test {
	@Test
	public void b032() throws Exception {
		String oldString = "03201121970,001,00:00:00.0000Timothy John Ronan~Volume 47, Issue 89, 16 April 1971~Geophysical Research Letters~";
		B032 b = BlocketteBuilder.build032(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}
	
	@Test
	public void b032overflow() throws Exception {
		String oldString = "03201121970,001,00:00:00.0000Timothy John Ronan~Volume 47, Issue 89xzcvzxcvzczxczxvcsdfkjndghdfgjkldfcnfggjkldffgdfgdgdgffgdlgsfklgskl, 16 April 1971~Geophysical Research Letters~";
		B032 b = BlocketteBuilder.build032(oldString.getBytes());
		String newString = b.toSeedString();
		assertNotEquals(oldString, newString);
	}
}
