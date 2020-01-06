package edu.iris.dmc.seed;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;

import org.junit.jupiter.api.Test;

import edu.iris.dmc.seed.control.index.B010;
import edu.iris.dmc.seed.control.index.B011;
import edu.iris.dmc.seed.control.station.B050;
import edu.iris.dmc.seed.director.BlocketteDirectorTest;

public class VolumeBuildTest {

	@Test
	public void b011() throws Exception {
		Volume v = new Volume();

		B010 b010 = new B010();
		b010.setNthPower(12);
		v.add(b010);
		assertNotNull(v.getB010());
		assertEquals(12, v.getB010().getNthPower());

		for (int i = 3; i < 500; i++) {
			v.add(randomStation());
		}
		v.build();

		assertNotNull(v.getRecords());
		assertEquals(500, v.getRecords().size());

		for (Record r : v.getRecords()) {
			System.out.println(new String(r.getBytes()));
			break;
		}
	}

	@Test
	public void build() throws Exception {
		File source = new File(BlocketteDirectorTest.class.getClassLoader().getResource("test.xml.seed").getFile());
		Volume v = TestUtil.load(source);
		B011 b = v.getB011();
		assertEquals("0110032002CONZ 000003BELA 000009",b.toSeedString());
		v.build();
		b = v.getB011();
		assertEquals("0110032002CONZ 000003BELA 000009",b.toSeedString());
	}

	public B050 randomStation() throws SeedException {

		String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		StringBuilder sb = new StringBuilder(5);
		for (int i = 0; i < 5; i++) {
			int index = (int) (chars.length() * Math.random());
			sb.append(chars.charAt(index));
		}
		String text = "0500154" + sb.toString()
				+ "+28.209718-177.381430+0004.60000000Midway Islands Infrasonic Array, Site I58H1, USA~0003210102013,315,00:00:00.0000~2999,365,23:59:59.0000~NIM";
		return (B050) BlocketteFactory.create(text.getBytes());
	}
}
