package edu.iris.seed.index;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Identifier.B011;
import edu.iris.seed.Identifier.B011.Row;

public class B011Test {
	@Test
	public void b012() throws Exception {
		B011 b011 = new B011();
		b011.add("ANMO", 1);
		b011.add("ANMO", 2);
		b011.add("ANTO", 3);
		assertEquals("0110032002ANMO 000001ANTO 000003", b011.toSeedString());
		assertEquals(2, b011.getRows().size());
		assertEquals(2, b011.getNumberOfStations());

		Row r1 = b011.getRows().get(0);
		assertEquals("ANMO", r1.getCode());
		assertEquals(1, r1.getSequence());

		Row r2 = b011.getRows().get(1);
		assertEquals("ANTO", r2.getCode());
		assertEquals(3, r2.getSequence());

		b011.updateSequences(3);

		assertEquals("0110032002ANMO 000004ANTO 000006", b011.toSeedString());
		assertEquals(2, b011.getRows().size());
		assertEquals(2, b011.getNumberOfStations());

		r1 = b011.getRows().get(0);
		assertEquals("ANMO", r1.getCode());
		assertEquals(4, r1.getSequence());

		r2 = b011.getRows().get(1);
		assertEquals("ANTO", r2.getCode());
		assertEquals(6, r2.getSequence());
	}
}
