package edu.iris.dmc.seed.control.dictionary;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import edu.iris.dmc.seed.Blockette;
import edu.iris.dmc.seed.BlocketteFactory;

public class B040sTest {

	@Test
	public void te() throws Exception {
		String text = "0570051046.4000E+040000200000+3.9062E-05+3.9062E-05";
		Blockette b057 = BlocketteFactory.create(text.getBytes());
		assertEquals(text, b057.toSeedString());
	}
}
