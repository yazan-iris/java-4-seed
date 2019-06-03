package edu.iris.dmc.seed.control.dictionary;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.iris.dmc.seed.builder.BlocketteBuilder;
import edu.iris.dmc.seed.control.index.B008;
import edu.iris.dmc.seed.control.index.B010;

public class B008Test {

	@Test
	public void b008() throws Exception {
		String oldString = "008011702.112 ANMO  BHZ1992,001,00:00:00.0000~1992,002,00:00:00.0000~1992,002,00:00:00.0000~1993,029,00:00:00.0000~IU";
		// 008011702.112 ANMO
		// BHZ1992,001,00:00:00.0000~1992,002,00:00:00.0000~1992,002,00:00:00.0000~1993,029,00:00:00.0000~IU
		B008 b = BlocketteBuilder.build008(oldString.getBytes());
		String newString = b.toSeedString();
		assertEquals(oldString, newString);
	}
}
