package edu.iris.seed.record;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import edu.iris.seed.SeedException;
import edu.iris.seed.abbreviation.B034;

public class AbbreviationRecordTest {

	@Test
	public void add() throws Exception {

		AbbreviationRecord ar = AbbreviationRecord.Builder.newInstance().sequence(1).continuation(false).build();

		String name = "test";
		String description = "description";
		B034 b1 = B034.Builder.newInstance().name(name).description(description).build();
		assertEquals(0, b1.getLookupKey());
		assertEquals(name, b1.getName());
		assertEquals(description, b1.getDescription());
		ar.add(b1);

		B034 b2 = B034.Builder.newInstance().name(name).description(description).lookupKey(1).build();
		Assertions.assertThrows(SeedException.class, () -> {
			ar.add(b2);
		});

		B034 b3 = B034.Builder.newInstance().name(name).description(description).lookupKey(10000).build();
		Assertions.assertThrows(SeedException.class, () -> {
			ar.add(b3);
		});
		B034 b4 = B034.Builder.newInstance().name(name).description(description).lookupKey(0).build();
		b4 = (B034) ar.add(b4);
		assertEquals(1, b4.getLookupKey());
	}
}
