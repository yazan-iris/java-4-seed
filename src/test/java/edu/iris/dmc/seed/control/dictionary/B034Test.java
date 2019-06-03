package edu.iris.dmc.seed.control.dictionary;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.dmc.seed.DictionaryIndex;
import edu.iris.dmc.seed.builder.BlocketteBuilder;


public class B034Test {
	@Test
	public void fromString() throws Exception {
		B034 b034 = BlocketteBuilder
				.build034(new String("0340049178COUNTS~Digital Count in Digital counts~").getBytes());

		DictionaryIndex dictionary = new DictionaryIndex();
		dictionary.put(b034);
		assertEquals(1, dictionary.size());

		dictionary.put(b034);
		assertEquals(1, dictionary.size());

		dictionary.put(b034);
		assertEquals(1, dictionary.size());

		dictionary.put(b034);
		assertEquals(1, dictionary.size());



	}
}
