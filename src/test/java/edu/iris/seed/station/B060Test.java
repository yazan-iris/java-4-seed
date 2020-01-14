package edu.iris.seed.station;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;
import edu.iris.seed.SeedBlockette;

public class B060Test {

	@Test
	public void b061() throws Exception {
		String text = "060  37 2 1 2   6   7 2 3   3   4   8";
		Blockette b060 = SeedBlockette.Builder.newInstance().build(text.getBytes());
		assertEquals("0600037020102000600070203000300040008", b060.toSeedString());
	}


}
