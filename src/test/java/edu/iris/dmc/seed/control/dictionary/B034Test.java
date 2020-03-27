package edu.iris.dmc.seed.control.dictionary;


import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.dmc.seed.DictionaryIndex;
import edu.iris.dmc.seed.builder.BlocketteBuilder;


public class B034Test {
	@Test
	public void fromString() throws Exception {
		String oldString = "0340049178COUNTS~Digital Count in Digital counts~";
		B034 b034 = BlocketteBuilder.build034(oldString.getBytes());
		String newString = b034.toSeedString();
		assertEquals(oldString, newString);
	
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
	
	@Test
	public void b34overflow() throws Exception {
		String oldString = "0340049178COUNTSsdhfsdhjfhsdgdflkghfdkhghdflghuildfhguifdhluxf~Digital Count in Digital countssdgjkfsdgjgsdhfghsdgfhksdhkgjfdshgfhgdsfghksdhgfsdghkfgkdsgfjkdsghj~";
		B034 b034 = BlocketteBuilder.build034(oldString.getBytes());
		String newString = b034.toSeedString();
		System.out.println(oldString);
		System.out.println(newString);

		assertNotEquals(oldString, newString);

	}
}
