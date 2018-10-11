package edu.iris.dmc.seed;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import edu.iris.dmc.seed.builder.BlocketteBuilder;
import edu.iris.dmc.seed.control.dictionary.B033;
import edu.iris.dmc.seed.control.dictionary.B034;

public class DictionaryHeaderTest {

	@Test
	public void t() throws Exception {
		B034 b034 = BlocketteBuilder
				.build034(new String("0340049178COUNTS~Digital Count in Digital counts~").getBytes());

		Dictionary dictionary = new Dictionary();
		dictionary.put(b034);
		assertEquals(1, dictionary.size());

		dictionary.put(b034);
		assertEquals(1, dictionary.size());

		dictionary.put(b034);
		assertEquals(1, dictionary.size());

		dictionary.put(b034);
		assertEquals(1, dictionary.size());

		B033 b033 = new B033();
		b033.setDescription("My testing blockette");
		b033 = (B033) dictionary.put(b033);
		assertEquals(2, dictionary.size());

	}
}
