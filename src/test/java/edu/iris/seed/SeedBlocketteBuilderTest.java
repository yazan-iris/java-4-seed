package edu.iris.seed;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import edu.iris.seed.abbreviation.B032;

public class SeedBlocketteBuilderTest {

	@Test
	public void run() throws Exception {

		B032 b = B032.Builder.newInstance().author("Author").catalog("Catalog").date(2012, 3, 3, 3, 3, 3)
				.publisher("test").build();
		
		assertEquals("032005200,Author~Catalog2012,003,03:03:03.0003~test~",b.toSeedString());
	}
}
