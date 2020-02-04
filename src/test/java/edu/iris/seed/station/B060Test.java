package edu.iris.seed.station;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class B060Test {

	@Test
	public void b060() throws Exception {
		String text = "060  37 2 1 2   6   7 2 3   3   4   8";
		B060 b = B060.Builder.newInstance().fromBytes(text.getBytes()).build();
		
		assertEquals(2,b.getStages().size());
		assertEquals("0600037020102000600070203000300040008", b.toSeedString());
		
		B060 b2 = B060.Builder.newInstance().fromBytes(text.getBytes()).build();
		b.append(b2);
		assertEquals(4,b.getStages().size());
		assertEquals("06000650401020006000702030003000400080102000600070203000300040008", b.toSeedString());
	}

}
