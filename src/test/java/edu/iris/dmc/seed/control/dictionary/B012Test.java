package edu.iris.dmc.seed.control.dictionary;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.iris.dmc.seed.builder.BlocketteBuilder;
import edu.iris.dmc.seed.control.index.B012;

public class B012Test {
	@Test
	public void b012() throws Exception {
		String oldString = "012006300011992,001,00:00:00.0000~1992,002,00:00:00.0000~000014";
		B012 b = BlocketteBuilder.build012(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}
}
