package edu.iris.seed;

import java.io.InputStream;

import org.junit.jupiter.api.Test;

import edu.iris.seed.Blockette;
import edu.iris.seed.SeedRecordInputStream;
import edu.iris.seed.io.SeedBlocketteIterator;

public class SeedBlocketteIteratorTest {

	@Test
	public void getAll() throws Exception {
		try (InputStream stream = SeedBlocketteIteratorTest.class.getClassLoader()
				.getResourceAsStream("AU.MILA.dataless.fromHughGlanville.20181018");
				SeedBlocketteIterator it = new SeedBlocketteIterator(new SeedRecordInputStream(stream));) {

			while (it.hasNext()) {
				Blockette b = it.next();
				System.out.println(b.toSeedString());
			}
		}

	}
	
	@Test
	public void getAll1() throws Exception {
		try (InputStream stream = SeedBlocketteIteratorTest.class.getClassLoader()
				.getResourceAsStream("CI_OAT.dataless");
				SeedBlocketteIterator it = new SeedBlocketteIterator(new SeedRecordInputStream(stream));) {

			while (it.hasNext()) {
				Blockette b = it.next();
				System.out.println(b.toSeedString());
			}
		}

	}
}
